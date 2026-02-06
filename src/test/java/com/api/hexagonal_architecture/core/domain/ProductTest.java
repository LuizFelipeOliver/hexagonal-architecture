package com.api.hexagonal_architecture.core.domain;

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
    void createPriceWithValidPriceShouldUpdatePrice() {
        Product product = new Product(1L, "Notebook", new BigDecimal("100.00"));

        product.createPrice(new BigDecimal("200.00"));

        assertEquals(new BigDecimal("200.00"), product.getPrice());
    }

    @Test
    void createPriceWithInvalidPriceShouldThrowException() {
        Product product = new Product(1L, "Notebook", new BigDecimal("100.00"));

        assertThrows(IllegalArgumentException.class,
                () -> product.createPrice(null));

        assertThrows(IllegalArgumentException.class,
                () -> product.createPrice(BigDecimal.ZERO));

        assertThrows(IllegalArgumentException.class,
                () -> product.createPrice(new BigDecimal("-5.00")));
    }
}
