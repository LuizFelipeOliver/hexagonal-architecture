package com.api.hexagonal_architecture.core.domain;

import java.math.BigDecimal;

public class Product {
    private Long id;
    private String name;
    private BigDecimal price;

    public Product() {
        // default constructor
    }

    public Product(Long id, String name, BigDecimal price) {
        validatePrice(price);
        validateName(name);

        this.id = id;
        this.name = name;
        this.price = price;
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
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Product name is required");
        }
    }

    public void changePrice(BigDecimal newPrice) {
        validatePrice(newPrice);
        this.price = newPrice;
    }


    public void changeName(String newName) {
        validateName(newName);
        this.name = newName;
    }


    public Long getId() { return id; }

    public String getName() { return name; }

    public BigDecimal getPrice() { return price; }
}

