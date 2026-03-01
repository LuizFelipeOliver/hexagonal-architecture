package com.api.hexagonal_architecture.infrastructure.persistence.mapper;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import com.api.hexagonal_architecture.domain.model.Product;
import com.api.hexagonal_architecture.infrastructure.persistence.entity.ProductEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ProductPersistenceMapperTest {

    private final RawMaterialPersistenceMapper rawMaterialMapper = new RawMaterialPersistenceMapper();
    private final RecipePersistenceMapper recipeMapper = new RecipePersistenceMapper(rawMaterialMapper);
    private final ProductPersistenceMapper mapper = new ProductPersistenceMapper(recipeMapper);

    @Test
    void toEntityShouldConvertProductToProductEntity() {
        Product product = Product.reconstruct(1L, "Notebook", "A powerful notebook",
                new BigDecimal("2500.00"), null);

        ProductEntity entity = mapper.toEntity(product);

        assertEquals(1L, entity.getId());
        assertEquals("Notebook", entity.getName());
        assertEquals(new BigDecimal("2500.00"), entity.getPrice());
        assertEquals("A powerful notebook", entity.getDescription());
        assertNull(entity.getRecipe());
    }

    @Test
    void toDomainShouldConvertProductEntityToProduct() {
        ProductEntity entity = new ProductEntity(1L, "Notebook", new BigDecimal("2500.00"),
                "A powerful notebook", null);

        Product product = mapper.toDomain(entity);

        assertEquals(1L, product.getId());
        assertEquals("Notebook", product.getName());
        assertEquals(new BigDecimal("2500.00"), product.getPrice());
        assertEquals("A powerful notebook", product.getDescription());
        assertNull(product.getRecipe());
    }
}
