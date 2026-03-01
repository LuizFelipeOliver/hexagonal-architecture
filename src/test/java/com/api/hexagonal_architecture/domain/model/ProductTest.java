package com.api.hexagonal_architecture.domain.model;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ProductTest {

    @Test
    void createFactoryMethodShouldCreateProductWithoutId() {
        Product product = Product.create("Notebook", "A powerful notebook", new BigDecimal("2500.00"));

        assertNull(product.getId());
        assertEquals("Notebook", product.getName());
        assertEquals("A powerful notebook", product.getDescription());
        assertEquals(new BigDecimal("2500.00"), product.getPrice());
    }

    @Test
    void reconstructShouldCreateProductWithId() {
        Product product = Product.reconstruct(5L, "Mouse", "Wireless mouse", new BigDecimal("50.00"), null);

        assertEquals(5L, product.getId());
        assertEquals("Mouse", product.getName());
        assertEquals("Wireless mouse", product.getDescription());
        assertEquals(new BigDecimal("50.00"), product.getPrice());
    }

    @Test
    void createWithNullPriceShouldAllowNullablePrice() {
        Product product = Product.create("Notebook", "desc", null);

        assertNull(product.getPrice());
    }

    @Test
    void createWithZeroPriceShouldThrowException() {
        assertThrows(IllegalArgumentException.class,
                () -> Product.create("Notebook", "desc", BigDecimal.ZERO));
    }

    @Test
    void createWithNegativePriceShouldThrowException() {
        assertThrows(IllegalArgumentException.class,
                () -> Product.create("Notebook", "desc", new BigDecimal("-10.00")));
    }

    @Test
    void createWithNullNameShouldThrowException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> Product.create(null, "desc", new BigDecimal("100.00")));

        assertEquals("[Name]: Cannot be null or blank", exception.getMessage());
    }

    @Test
    void createWithBlankNameShouldThrowException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> Product.create("  ", "desc", new BigDecimal("100.00")));

        assertEquals("[Name]: Cannot be null or blank", exception.getMessage());
    }

    @Test
    void createWithShortNameShouldThrowException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> Product.create("AB", "desc", new BigDecimal("100.00")));

        assertEquals("[Name]: Must have at least 3 characters", exception.getMessage());
    }

    @Test
    void updateShouldChangeNameDescriptionAndPrice() {
        Product product = Product.create("Notebook", "Old desc", new BigDecimal("100.00"));

        product.update("Notebook Pro", "New desc", new BigDecimal("200.00"));

        assertEquals("Notebook Pro", product.getName());
        assertEquals("New desc", product.getDescription());
        assertEquals(new BigDecimal("200.00"), product.getPrice());
    }

    @Test
    void updateWithNullNameShouldThrowException() {
        Product product = Product.create("Notebook", "desc", new BigDecimal("100.00"));

        assertThrows(IllegalArgumentException.class,
                () -> product.update(null, "desc", new BigDecimal("200.00")));
    }
}
