package com.api.hexagonal_architecture.domain.model;

import java.math.BigDecimal;

public class RecipeItem {
    private final RawMaterial rawMaterial;
    private final BigDecimal quantity;

    public RecipeItem(RawMaterial rawMaterial, BigDecimal quantity) {
        if (rawMaterial == null) {
            throw new IllegalArgumentException("[RecipeItem]: RawMaterial is required");
        }
        if (quantity == null || quantity.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("[RecipeItem]: Quantity must be greater than zero");
        }
        this.rawMaterial = rawMaterial;
        this.quantity = quantity;
    }

    public BigDecimal calculateCost() {
        return rawMaterial.getUnitCost().multiply(quantity);
    }

    public RawMaterial getRawMaterial() {
        return rawMaterial;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }
}
