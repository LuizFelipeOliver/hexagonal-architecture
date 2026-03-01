package com.api.hexagonal_architecture.adapter.in.web;

import java.math.BigDecimal;
import java.util.List;

public record RecipeRequest(String name, BigDecimal yield, List<RecipeItemRequest> items) {
}
