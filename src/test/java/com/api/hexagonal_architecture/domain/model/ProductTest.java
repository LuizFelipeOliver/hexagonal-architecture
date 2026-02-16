package com.api.hexagonal_architecture.domain.model;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    @Test
    void constructorWithValidDataShouldCreateProduct() {
        Product product = new Product(1L, "Notebook", new BigDecimal("2500.00"));

        assertEquals(1L, product.getId());
        assertEquals("Notebook", product.getName());
        assertEquals(new BigDecimal("2500.00"), product.getPrice());
    }

    @Test
    void createFactoryMethodShouldCreateProductWithoutId() {
        Product product = Product.create("Notebook", new BigDecimal("2500.00"));

        assertNull(product.getId());
        assertEquals("Notebook", product.getName());
        assertEquals(new BigDecimal("2500.00"), product.getPrice());
    }

    @Test
    void reconstructShouldCreateProductWithId() {
        Product product = Product.reconstruct(5L, "Mouse", new BigDecimal("50.00"));

        assertEquals(5L, product.getId());
        assertEquals("Mouse", product.getName());
        assertEquals(new BigDecimal("50.00"), product.getPrice());
    }

    @Test
    void constructorWithNullPriceShouldThrowException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new Product(1L, "Notebook", null));

        assertEquals("Price is required", exception.getMessage());
    }

    @Test
    void constructorWithZeroPriceShouldThrowException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new Product(1L, "Notebook", BigDecimal.ZERO));

        assertEquals("Price must be greater than zero", exception.getMessage());
    }

    @Test
    void constructorWithNegativePriceShouldThrowException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new Product(1L, "Notebook", new BigDecimal("-10.00")));

        assertEquals("Price must be greater than zero", exception.getMessage());
    }

    @Test
    void constructorWithNullNameShouldThrowException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new Product(1L, null, new BigDecimal("100.00")));

        assertEquals("Product name is required", exception.getMessage());
    }

    @Test
    void constructorWithBlankNameShouldThrowException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new Product(1L, "  ", new BigDecimal("100.00")));

        assertEquals("Product name is required", exception.getMessage());
    }

    @Test
    void updateShouldChangeNameAndPrice() {
        Product product = new Product(1L, "Notebook", new BigDecimal("100.00"));

        product.update("Notebook Pro", new BigDecimal("200.00"));

        assertEquals("Notebook Pro", product.getName());
        assertEquals(new BigDecimal("200.00"), product.getPrice());
    }

    @Test
    void updateWithInvalidDataShouldThrowException() {
        Product product = new Product(1L, "Notebook", new BigDecimal("100.00"));

        assertThrows(IllegalArgumentException.class,
                () -> product.update(null, new BigDecimal("200.00")));

        assertThrows(IllegalArgumentException.class,
                () -> product.update("Valid", null));

        assertThrows(IllegalArgumentException.class,
                () -> product.update("Valid", BigDecimal.ZERO));
    }
}
