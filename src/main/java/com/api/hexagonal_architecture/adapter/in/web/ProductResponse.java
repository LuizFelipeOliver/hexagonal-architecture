package com.api.hexagonal_architecture.adapter.in.web;

import java.math.BigDecimal;

import com.api.hexagonal_architecture.domain.model.Product;

public record ProductResponse(Long id, String name, BigDecimal price, String description) {
    public static ProductResponse from(Product product) {
        return new ProductResponse(product.getId(), product.getName(), product.getPrice(), product.getDescription());
    }
}
