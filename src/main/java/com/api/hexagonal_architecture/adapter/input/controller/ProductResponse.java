package com.api.hexagonal_architecture.adapter.input.controller;

import java.math.BigDecimal;

public record ProductResponse(Long id, String name, BigDecimal price) {
}
