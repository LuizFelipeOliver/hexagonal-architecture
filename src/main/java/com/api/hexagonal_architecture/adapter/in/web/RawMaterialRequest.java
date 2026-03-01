package com.api.hexagonal_architecture.adapter.in.web;

import java.math.BigDecimal;

import com.api.hexagonal_architecture.domain.model.RawMaterial.Unit;

public record RawMaterialRequest(String name, Unit unit, BigDecimal unitCost) {
}
