package com.api.hexagonal_architecture.adapter.in.web;

import java.math.BigDecimal;

public record ProductRequest(String name, BigDecimal price, String description) {
}
