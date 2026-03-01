package com.api.hexagonal_architecture.adapter.in.web;

import java.math.BigDecimal;

import com.api.hexagonal_architecture.domain.model.Product;

public record ProductResponse(
        Long id,
        String name,
        String description,
        BigDecimal price,
        RecipeResponse recipe,
        BigDecimal costPerUnit,
        BigDecimal markup) {

    public static ProductResponse from(Product product) {
        RecipeResponse recipeResponse = product.getRecipe() != null
                ? RecipeResponse.from(product.getRecipe())
                : null;
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                recipeResponse,
                product.getCost().orElse(null),
                product.getMarkup().orElse(null)
        );
    }
}
