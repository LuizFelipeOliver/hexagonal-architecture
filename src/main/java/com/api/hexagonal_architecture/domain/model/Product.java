package com.api.hexagonal_architecture.domain.model;

import java.math.BigDecimal;

public class Product {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;

    public Product(Long id, String name, BigDecimal price, String description) {
        validateName(name);
        validatePrice(price);
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
    }

    public static Product create(String name, BigDecimal price, String description) {
        return new Product(null, name, price, description);
    }

    public static Product reconstruct(Long id, String name, BigDecimal price, String description) {
        return new Product(id, name, price, description);
    }

    public void update(String name, BigDecimal price, String description) {
        validateName(name);
        validatePrice(price);
        this.name = name;
        this.price = price;
        this.description = description;
    }

    private void validatePrice(BigDecimal price) {
        if (price == null) {
            throw new IllegalArgumentException("Price is required");
        }
        if (price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Price must be greater than zero");
        }
    }

    private void validateName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("[Name]: Cannot be null");
        }

        if (name.isBlank()) {
            throw new IllegalArgumentException("[Name]: Cannot be blank");
        }

        if (name.length() <= 2) {
            throw new IllegalArgumentException("[Name]: Must have at least 3 characters");
        }
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }
}
