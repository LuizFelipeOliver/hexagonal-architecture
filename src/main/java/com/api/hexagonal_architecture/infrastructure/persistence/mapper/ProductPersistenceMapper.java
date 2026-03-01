package com.api.hexagonal_architecture.infrastructure.persistence.mapper;

import com.api.hexagonal_architecture.domain.model.Product;
import com.api.hexagonal_architecture.domain.model.Recipe;
import com.api.hexagonal_architecture.infrastructure.persistence.entity.ProductEntity;
import com.api.hexagonal_architecture.infrastructure.persistence.entity.RecipeEntity;

public class ProductPersistenceMapper {

    private final RecipePersistenceMapper recipeMapper;

    public ProductPersistenceMapper(RecipePersistenceMapper recipeMapper) {
        this.recipeMapper = recipeMapper;
    }

    public ProductEntity toEntity(Product product) {
        RecipeEntity recipeEntity = product.getRecipe() != null
                ? recipeMapper.toEntity(product.getRecipe())
                : null;
        return new ProductEntity(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getDescription(),
                recipeEntity
        );
    }

    public Product toDomain(ProductEntity entity) {
        Recipe recipe = entity.getRecipe() != null
                ? recipeMapper.toDomain(entity.getRecipe())
                : null;
        return Product.reconstruct(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getPrice(),
                recipe
        );
    }
}
