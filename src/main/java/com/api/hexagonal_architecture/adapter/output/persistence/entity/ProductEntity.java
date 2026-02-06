package com.api.hexagonal_architecture.adapter.output.persistence.entity;

import java.math.BigDecimal;

import com.api.hexagonal_architecture.core.domain.Product;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class ProductEntity extends Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private BigDecimal price;

    public ProductEntity() {
        // default constructor for JPA
    }

    public ProductEntity(Long id, String name, BigDecimal price) {
        validatePrice(price);
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    private void validatePrice(BigDecimal price) {
        if (price == null) {
            throw new IllegalArgumentException("Price is required");
        }

        if (price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Price must be greater than zero");
        }
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "ProductEntity{"
            + "id=" + id
            + ", name='" + name + '\''
            + ", price=" + price
            + '}';
    }
}
