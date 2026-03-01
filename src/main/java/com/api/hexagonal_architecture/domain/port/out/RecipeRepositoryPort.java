package com.api.hexagonal_architecture.domain.port.out;

import java.util.List;
import java.util.Optional;

import com.api.hexagonal_architecture.domain.model.Recipe;

public interface RecipeRepositoryPort {
    Recipe save(Recipe recipe);

    Optional<Recipe> findById(Long id);

    List<Recipe> findAll();

    void deleteById(Long id);
}
