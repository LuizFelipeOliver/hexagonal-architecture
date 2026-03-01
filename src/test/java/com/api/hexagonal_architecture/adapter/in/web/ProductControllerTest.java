package com.api.hexagonal_architecture.adapter.in.web;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.api.hexagonal_architecture.domain.model.Product;
import com.api.hexagonal_architecture.domain.port.in.ProductServicePort;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductServicePort productService;

    @Test
    void postProductShouldReturn201() throws Exception {
        Product saved = Product.reconstruct(1L, "Notebook", "A powerful notebook",
                new BigDecimal("2500.00"), null);
        when(productService.createProduct(any(Product.class))).thenReturn(saved);

        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"name": "Notebook", "description": "A powerful notebook", "price": 2500.00}
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Notebook"))
                .andExpect(jsonPath("$.price").value(2500.00))
                .andExpect(jsonPath("$.description").value("A powerful notebook"));
    }

    @Test
    void getProductByIdShouldReturn200WhenExists() throws Exception {
        Product product = Product.reconstruct(1L, "Notebook", "desc", new BigDecimal("2500.00"), null);
        when(productService.findProductById(1L)).thenReturn(Optional.of(product));

        mockMvc.perform(get("/api/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Notebook"))
                .andExpect(jsonPath("$.price").value(2500.00))
                .andExpect(jsonPath("$.description").value("desc"));
    }

    @Test
    void getProductByIdShouldReturn404WhenNotExists() throws Exception {
        when(productService.findProductById(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/products/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getProductsShouldReturnListAndStatus200() throws Exception {
        List<Product> products = List.of(
                Product.reconstruct(1L, "Notebook", "desc1", new BigDecimal("2500.00"), null),
                Product.reconstruct(2L, "Mouse", "desc2", new BigDecimal("50.00"), null));
        when(productService.listProducts()).thenReturn(products);

        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Notebook"))
                .andExpect(jsonPath("$[1].name").value("Mouse"));
    }

    @Test
    void putProductShouldReturn200() throws Exception {
        Product updated = Product.reconstruct(1L, "Notebook Pro", "Updated desc",
                new BigDecimal("3000.00"), null);
        when(productService.updateProduct(eq(1L), eq("Notebook Pro"), eq("Updated desc"), any(BigDecimal.class)))
                .thenReturn(updated);

        mockMvc.perform(put("/api/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"name": "Notebook Pro", "description": "Updated desc", "price": 3000.00}
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Notebook Pro"))
                .andExpect(jsonPath("$.price").value(3000.00))
                .andExpect(jsonPath("$.description").value("Updated desc"));
    }

    @Test
    void deleteProductShouldReturn204() throws Exception {
        doNothing().when(productService).deleteProduct(1L);

        mockMvc.perform(delete("/api/products/1"))
                .andExpect(status().isNoContent());

        verify(productService).deleteProduct(1L);
    }

    @Test
    void searchProductsByNameShouldReturn200() throws Exception {
        List<Product> products = List.of(
                Product.reconstruct(1L, "Notebook", "desc", new BigDecimal("2500.00"), null));
        when(productService.findProductByName("Notebook")).thenReturn(products);

        mockMvc.perform(get("/api/products/search").param("name", "Notebook"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value("Notebook"));
    }
}
