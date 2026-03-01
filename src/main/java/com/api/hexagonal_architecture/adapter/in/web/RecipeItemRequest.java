package com.api.hexagonal_architecture.adapter.in.web;

import java.math.BigDecimal;

public record RecipeItemRequest(Long rawMaterialId, BigDecimal quantity) {
}
