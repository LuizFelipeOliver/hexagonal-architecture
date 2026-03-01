package com.api.hexagonal_architecture.domain.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class Recipe {

    private Long id;
    private String name;
    private BigDecimal yield;
    private List<RecipeItem> items;

    private Recipe(Long id, String name, BigDecimal yield, List<RecipeItem> items) {
        validateName(name);
        validateYield(yield);
        this.id = id;
        this.name = name;
        this.yield = yield;
        this.items = items != null ? new ArrayList<>(items) : new ArrayList<>();
    }

    public static Recipe create(String name, BigDecimal yield, List<RecipeItem> items) {
        return new Recipe(null, name, yield, items);
    }

    public static Recipe reconstruct(Long id, String name, BigDecimal yield, List<RecipeItem> items) {
        return new Recipe(id, name, yield, items);
    }

    public void update(String name, BigDecimal yield, List<RecipeItem> items) {
        validateName(name);
        validateYield(yield);
        this.name = name;
        this.yield = yield;
        this.items = items != null ? new ArrayList<>(items) : new ArrayList<>();
    }

    public BigDecimal calculateCost() {
        return items.stream()
                .map(RecipeItem::calculateCost)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal calculateCostPerUnit() {
        return calculateCost().divide(yield, 4, RoundingMode.HALF_UP);
    }

    private void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("[Recipe]: Name cannot be null or blank");
        }
        if (name.length() <= 2) {
            throw new IllegalArgumentException("[Recipe]: Name must have at least 3 characters");
        }
    }

    private void validateYield(BigDecimal yield) {
        if (yield == null) {
            throw new IllegalArgumentException("[Recipe]: Yield is required");
        }
        if (yield.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("[Recipe]: Yield must be greater than zero");
        }
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getYield() {
        return yield;
    }

    public List<RecipeItem> getItems() {
        return new ArrayList<>(items);
    }
}
