package com.api.hexagonal_architecture.adapter.in.web;

import java.math.BigDecimal;
import java.util.List;

import com.api.hexagonal_architecture.domain.model.Recipe;

public record RecipeResponse(
        Long id,
        String name,
        BigDecimal yield,
        List<RecipeItemResponse> items,
        BigDecimal totalCost,
        BigDecimal costPerUnit) {

    public static RecipeResponse from(Recipe recipe) {
        return new RecipeResponse(
                recipe.getId(),
                recipe.getName(),
                recipe.getYield(),
                recipe.getItems().stream().map(RecipeItemResponse::from).toList(),
                recipe.calculateCost(),
                recipe.calculateCostPerUnit()
        );
    }
}
