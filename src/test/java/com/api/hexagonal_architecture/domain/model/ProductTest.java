package com.api.hexagonal_architecture.domain.model;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    @Test
    void constructorWithValidDataShouldCreateProduct() {
        Product product = new Product(1L, "Notebook", new BigDecimal("2500.00"), "A powerful notebook");

        assertEquals(1L, product.getId());
        assertEquals("Notebook", product.getName());
        assertEquals(new BigDecimal("2500.00"), product.getPrice());
        assertEquals("A powerful notebook", product.getDescription());
    }

    @Test
    void createFactoryMethodShouldCreateProductWithoutId() {
        Product product = Product.create("Notebook", new BigDecimal("2500.00"), "A powerful notebook");

        assertNull(product.getId());
        assertEquals("Notebook", product.getName());
        assertEquals(new BigDecimal("2500.00"), product.getPrice());
        assertEquals("A powerful notebook", product.getDescription());
    }

    @Test
    void reconstructShouldCreateProductWithId() {
        Product product = Product.reconstruct(5L, "Mouse", new BigDecimal("50.00"), "Wireless mouse");

        assertEquals(5L, product.getId());
        assertEquals("Mouse", product.getName());
        assertEquals(new BigDecimal("50.00"), product.getPrice());
        assertEquals("Wireless mouse", product.getDescription());
    }

    @Test
    void constructorWithNullPriceShouldThrowException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new Product(1L, "Notebook", null, "desc"));

        assertEquals("Price is required", exception.getMessage());
    }

    @Test
    void constructorWithZeroPriceShouldThrowException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new Product(1L, "Notebook", BigDecimal.ZERO, "desc"));

        assertEquals("Price must be greater than zero", exception.getMessage());
    }

    @Test
    void constructorWithNegativePriceShouldThrowException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new Product(1L, "Notebook", new BigDecimal("-10.00"), "desc"));

        assertEquals("Price must be greater than zero", exception.getMessage());
    }

    @Test
    void constructorWithNullNameShouldThrowException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new Product(1L, null, new BigDecimal("100.00"), "desc"));

        assertEquals("[Name]: Cannot be null", exception.getMessage());
    }

    @Test
    void constructorWithBlankNameShouldThrowException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new Product(1L, "  ", new BigDecimal("100.00"), "desc"));

        assertEquals("[Name]: Cannot be blank", exception.getMessage());
    }

    @Test
    void constructorWithShortNameShouldThrowException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new Product(1L, "AB", new BigDecimal("100.00"), "desc"));

        assertEquals("[Name]: Must have at least 3 characters", exception.getMessage());
    }

    @Test
    void updateShouldChangeNamePriceAndDescription() {
        Product product = new Product(1L, "Notebook", new BigDecimal("100.00"), "Old desc");

        product.update("Notebook Pro", new BigDecimal("200.00"), "New desc");

        assertEquals("Notebook Pro", product.getName());
        assertEquals(new BigDecimal("200.00"), product.getPrice());
        assertEquals("New desc", product.getDescription());
    }

    @Test
    void updateWithInvalidDataShouldThrowException() {
        Product product = new Product(1L, "Notebook", new BigDecimal("100.00"), "desc");

        assertThrows(IllegalArgumentException.class,
                () -> product.update(null, new BigDecimal("200.00"), "desc"));

        assertThrows(IllegalArgumentException.class,
                () -> product.update("Valid", null, "desc"));

        assertThrows(IllegalArgumentException.class,
                () -> product.update("Valid", BigDecimal.ZERO, "desc"));
    }
}
