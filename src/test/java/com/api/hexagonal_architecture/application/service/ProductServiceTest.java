package com.api.hexagonal_architecture.application.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.api.hexagonal_architecture.domain.exception.ProductNotFoundException;
import com.api.hexagonal_architecture.domain.model.Product;
import com.api.hexagonal_architecture.domain.port.out.ProductRepositoryPort;
import com.api.hexagonal_architecture.domain.port.out.RecipeRepositoryPort;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepositoryPort productRepository;

    @Mock
    private RecipeRepositoryPort recipeRepository;

    @Test
    void createProductShouldDelegateToRepositorySave() {
        Product product = Product.create("Notebook", "A powerful notebook", new BigDecimal("2500.00"));
        Product saved = Product.reconstruct(1L, "Notebook", "A powerful notebook",
                new BigDecimal("2500.00"), null);
        when(productRepository.save(product)).thenReturn(saved);

        ProductService productService = new ProductService(productRepository, recipeRepository);
        Product result = productService.createProduct(product);

        assertEquals(1L, result.getId());
        assertEquals("Notebook", result.getName());
        verify(productRepository).save(product);
    }

    @Test
    void findProductByIdShouldReturnProductWhenExists() {
        Product product = Product.reconstruct(1L, "Notebook", "desc", new BigDecimal("2500.00"), null);
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        ProductService productService = new ProductService(productRepository, recipeRepository);
        Optional<Product> result = productService.findProductById(1L);

        assertTrue(result.isPresent());
        assertEquals("Notebook", result.get().getName());
        verify(productRepository).findById(1L);
    }

    @Test
    void findProductByIdShouldReturnEmptyWhenNotExists() {
        when(productRepository.findById(99L)).thenReturn(Optional.empty());

        ProductService productService = new ProductService(productRepository, recipeRepository);
        Optional<Product> result = productService.findProductById(99L);

        assertTrue(result.isEmpty());
        verify(productRepository).findById(99L);
    }

    @Test
    void listProductsShouldReturnListOfProducts() {
        List<Product> products = List.of(
                Product.reconstruct(1L, "Notebook", "desc1", new BigDecimal("2500.00"), null),
                Product.reconstruct(2L, "Mouse", "desc2", new BigDecimal("50.00"), null));
        when(productRepository.findAll()).thenReturn(products);

        ProductService productService = new ProductService(productRepository, recipeRepository);
        List<Product> result = productService.listProducts();

        assertEquals(2, result.size());
        verify(productRepository).findAll();
    }

    @Test
    void updateProductShouldFindAndUpdateProduct() {
        Product existing = Product.reconstruct(1L, "Notebook", "Old desc", new BigDecimal("2500.00"), null);
        when(productRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(productRepository.save(any(Product.class))).thenAnswer(inv -> inv.getArgument(0));

        ProductService productService = new ProductService(productRepository, recipeRepository);
        Product result = productService.updateProduct(1L, "Notebook Pro", "New desc", new BigDecimal("3000.00"));

        assertEquals("Notebook Pro", result.getName());
        assertEquals(new BigDecimal("3000.00"), result.getPrice());
        assertEquals("New desc", result.getDescription());
        verify(productRepository).findById(1L);
        verify(productRepository).save(any(Product.class));
    }

    @Test
    void updateProductShouldThrowWhenNotFound() {
        when(productRepository.findById(99L)).thenReturn(Optional.empty());

        ProductService productService = new ProductService(productRepository, recipeRepository);
        assertThrows(ProductNotFoundException.class,
                () -> productService.updateProduct(99L, "Name", "desc", new BigDecimal("10.00")));
    }

    @Test
    void deleteProductShouldDelegateToRepositoryDeleteById() {
        Product existing = Product.reconstruct(1L, "Notebook", "desc", new BigDecimal("2500.00"), null);
        when(productRepository.findById(1L)).thenReturn(Optional.of(existing));
        doNothing().when(productRepository).deleteById(1L);

        ProductService productService = new ProductService(productRepository, recipeRepository);
        productService.deleteProduct(1L);

        verify(productRepository).deleteById(1L);
    }

    @Test
    void deleteProductShouldThrowWhenNotFound() {
        when(productRepository.findById(99L)).thenReturn(Optional.empty());

        ProductService productService = new ProductService(productRepository, recipeRepository);
        assertThrows(ProductNotFoundException.class,
                () -> productService.deleteProduct(99L));
    }

    @Test
    void findProductByNameShouldDelegateToRepository() {
        List<Product> products = List.of(
                Product.reconstruct(1L, "Notebook", "desc", new BigDecimal("2500.00"), null));
        when(productRepository.findByName("Notebook")).thenReturn(products);

        ProductService productService = new ProductService(productRepository, recipeRepository);
        List<Product> result = productService.findProductByName("Notebook");

        assertEquals(1, result.size());
        assertEquals("Notebook", result.get(0).getName());
        verify(productRepository).findByName("Notebook");
    }
}
