package com.api.hexagonal_architecture.application.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.api.hexagonal_architecture.domain.exception.ProductNotFoundException;
import com.api.hexagonal_architecture.domain.model.Product;
import com.api.hexagonal_architecture.domain.port.out.ProductRepositoryPort;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepositoryPort productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    void createProductShouldDelegateToRepositorySave() {
        Product product = Product.create("Notebook", new BigDecimal("2500.00"), "A powerful notebook");
        Product saved = new Product(1L, "Notebook", new BigDecimal("2500.00"), "A powerful notebook");
        when(productRepository.save(product)).thenReturn(saved);

        Product result = productService.createProduct(product);

        assertEquals(1L, result.getId());
        assertEquals("Notebook", result.getName());
        verify(productRepository).save(product);
    }

    @Test
    void findProductByIdShouldReturnProductWhenExists() {
        Product product = new Product(1L, "Notebook", new BigDecimal("2500.00"), "desc");
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        Optional<Product> result = productService.findProductById(1L);

        assertTrue(result.isPresent());
        assertEquals("Notebook", result.get().getName());
        verify(productRepository).findById(1L);
    }

    @Test
    void findProductByIdShouldReturnEmptyWhenNotExists() {
        when(productRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<Product> result = productService.findProductById(99L);

        assertTrue(result.isEmpty());
        verify(productRepository).findById(99L);
    }

    @Test
    void listProductsShouldReturnListOfProducts() {
        List<Product> products = List.of(
                new Product(1L, "Notebook", new BigDecimal("2500.00"), "desc1"),
                new Product(2L, "Mouse", new BigDecimal("50.00"), "desc2"));
        when(productRepository.findAll()).thenReturn(products);

        List<Product> result = productService.listProducts();

        assertEquals(2, result.size());
        verify(productRepository).findAll();
    }

    @Test
    void updateProductShouldFindAndUpdateProduct() {
        Product existing = new Product(1L, "Notebook", new BigDecimal("2500.00"), "Old desc");
        when(productRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(productRepository.save(any(Product.class))).thenAnswer(inv -> inv.getArgument(0));

        Product result = productService.updateProduct(1L, "Notebook Pro", new BigDecimal("3000.00"), "New desc");

        assertEquals("Notebook Pro", result.getName());
        assertEquals(new BigDecimal("3000.00"), result.getPrice());
        assertEquals("New desc", result.getDescription());
        verify(productRepository).findById(1L);
        verify(productRepository).save(any(Product.class));
    }

    @Test
    void updateProductShouldThrowWhenNotFound() {
        when(productRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class,
                () -> productService.updateProduct(99L, "Name", new BigDecimal("10.00"), "desc"));
    }

    @Test
    void deleteProductShouldDelegateToRepositoryDeleteById() {
        Product existing = new Product(1L, "Notebook", new BigDecimal("2500.00"), "desc");
        when(productRepository.findById(1L)).thenReturn(Optional.of(existing));
        doNothing().when(productRepository).deleteById(1L);

        productService.deleteProduct(1L);

        verify(productRepository).deleteById(1L);
    }

    @Test
    void deleteProductShouldThrowWhenNotFound() {
        when(productRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class,
                () -> productService.deleteProduct(99L));
    }

    @Test
    void findProductByNameShouldDelegateToRepository() {
        List<Product> products = List.of(
                new Product(1L, "Notebook", new BigDecimal("2500.00"), "desc"));
        when(productRepository.findByName("Notebook")).thenReturn(products);

        List<Product> result = productService.findProductByName("Notebook");

        assertEquals(1, result.size());
        assertEquals("Notebook", result.get(0).getName());
        verify(productRepository).findByName("Notebook");
    }
}
