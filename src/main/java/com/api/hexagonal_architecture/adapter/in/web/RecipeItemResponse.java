package com.api.hexagonal_architecture.adapter.in.web;

import java.math.BigDecimal;

import com.api.hexagonal_architecture.domain.model.RecipeItem;

public record RecipeItemResponse(Long rawMaterialId, String rawMaterialName, BigDecimal quantity, BigDecimal cost) {
    public static RecipeItemResponse from(RecipeItem item) {
        return new RecipeItemResponse(
                item.getRawMaterial().getId(),
                item.getRawMaterial().getName(),
                item.getQuantity(),
                item.calculateCost()
        );
    }
}
