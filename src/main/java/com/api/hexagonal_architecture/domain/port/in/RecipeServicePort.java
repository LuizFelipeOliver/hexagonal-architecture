package com.api.hexagonal_architecture.domain.port.in;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import com.api.hexagonal_architecture.domain.model.Recipe;
import com.api.hexagonal_architecture.domain.model.RecipeItem;

public interface RecipeServicePort {
    Recipe create(String name, BigDecimal yield, List<RecipeItem> items);

    Optional<Recipe> findById(Long id);

    List<Recipe> findAll();

    Recipe update(Long id, String name, BigDecimal yield, List<RecipeItem> items);

    void delete(Long id);
}
