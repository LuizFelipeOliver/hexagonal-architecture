package com.api.hexagonal_architecture.adapter.in.web;

import java.math.BigDecimal;

import com.api.hexagonal_architecture.domain.model.RawMaterial;
import com.api.hexagonal_architecture.domain.model.RawMaterial.Unit;

public record RawMaterialResponse(Long id, String name, Unit unit, BigDecimal unitCost) {
    public static RawMaterialResponse from(RawMaterial rawMaterial) {
        return new RawMaterialResponse(
                rawMaterial.getId(),
                rawMaterial.getName(),
                rawMaterial.getUnit(),
                rawMaterial.getUnitCost()
        );
    }
}
