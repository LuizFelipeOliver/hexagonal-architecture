package com.api.hexagonal_architecture.adapter.output.persistence.mapper;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import com.api.hexagonal_architecture.adapter.output.persistence.entity.ProductEntity;
import com.api.hexagonal_architecture.core.domain.Product;

import static org.junit.jupiter.api.Assertions.*;

class ProductMapperTest {

    private final ProductMapper productMapper = new ProductMapper();

    @Test
    void toEntityShouldConvertProductToProductEntity() {
        Product product = new Product(1L, "Notebook", new BigDecimal("2500.00"));

        ProductEntity entity = productMapper.toEntity(product);

        assertEquals(1L, entity.getId());
        assertEquals("Notebook", entity.getName());
        assertEquals(new BigDecimal("2500.00"), entity.getPrice());
    }

    @Test
    void toDomainShouldConvertProductEntityToProduct() {
        ProductEntity entity = new ProductEntity(1L, "Notebook", new BigDecimal("2500.00"));

        Product product = productMapper.toDomain(entity);

        assertEquals(1L, product.getId());
        assertEquals("Notebook", product.getName());
        assertEquals(new BigDecimal("2500.00"), product.getPrice());
    }
}
