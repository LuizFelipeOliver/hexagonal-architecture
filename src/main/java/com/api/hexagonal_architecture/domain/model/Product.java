package com.api.hexagonal_architecture.domain.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

public class Product {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Recipe recipe;

    private Product(Long id, String name, String description, BigDecimal price, Recipe recipe) {
        validateName(name);
        if (price != null) {
            validatePrice(price);
        }
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.recipe = recipe;
    }

    public static Product create(String name, String description, BigDecimal price) {
        return new Product(null, name, description, price, null);
    }

    public static Product createFromRecipe(String name, Recipe recipe) {
        return new Product(null, name, null, null, recipe);
    }

    public static Product reconstruct(Long id, String name, String description,
                                      BigDecimal price, Recipe recipe) {
        return new Product(id, name, description, price, recipe);
    }

    public void update(String name, String description, BigDecimal price) {
        validateName(name);
        if (price != null) {
            validatePrice(price);
        }
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public void linkRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public void unlinkRecipe() {
        this.recipe = null;
    }

    public Optional<BigDecimal> getCost() {
        if (recipe == null) {
            return Optional.empty();
        }
        return Optional.of(recipe.calculateCostPerUnit());
    }

    public Optional<BigDecimal> getMarkup() {
        if (price == null || recipe == null) {
            return Optional.empty();
        }
        BigDecimal cost = recipe.calculateCostPerUnit();
        return Optional.of(
            price.subtract(cost)
                 .divide(cost, 4, RoundingMode.HALF_UP)
                 .multiply(BigDecimal.valueOf(100))
        );
    }

    private void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("[Name]: Cannot be null or blank");
        }
        if (name.length() <= 2) {
            throw new IllegalArgumentException("[Name]: Must have at least 3 characters");
        }
    }

    private void validatePrice(BigDecimal price) {
        if (price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Price must be greater than zero");
        }
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Recipe getRecipe() {
        return recipe;
    }
}
