package com.api.hexagonal_architecture.domain.model;

import java.math.BigDecimal;

public class RawMaterial {
    public enum Unit { KG, L, UN, G }

    private Long id;
    private String name;
    private Unit unit;
    private BigDecimal unitCost;

    private RawMaterial(Long id, String name, Unit unit, BigDecimal unitCost) {
        validateName(name);
        validateUnitCost(unitCost);
        this.id = id;
        this.name = name;
        this.unit = unit;
        this.unitCost = unitCost;
    }

    public static RawMaterial create(String name, Unit unit, BigDecimal unitCost) {
        return new RawMaterial(null, name, unit, unitCost);
    }

    public static RawMaterial reconstruct(Long id, String name, Unit unit, BigDecimal unitCost) {
        return new RawMaterial(id, name, unit, unitCost);
    }

    public void update(String name, Unit unit, BigDecimal unitCost) {
        validateName(name);
        validateUnitCost(unitCost);
        this.name = name;
        this.unit = unit;
        this.unitCost = unitCost;
    }

    private void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("[RawMaterial]: Cannot be null or blank");
        }
        if (name.length() <= 2) {
            throw new IllegalArgumentException("[RawMaterial]: Must have at least 3 characters");
        }
    }

    private void validateUnitCost(BigDecimal unitCost) {
        if (unitCost == null) {
            throw new IllegalArgumentException("[RawMaterial]: UnitCost is required");
        }
        if (unitCost.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("[RawMaterial]: UnitCost must be greater than zero");
        }
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Unit getUnit() {
        return unit;
    }

    public BigDecimal getUnitCost() {
        return unitCost;
    }
}
