package com.api.hexagonal_architecture.adapter.input.controller;

import java.math.BigDecimal;

public record ProductRequest(String name, BigDecimal price) {
}
