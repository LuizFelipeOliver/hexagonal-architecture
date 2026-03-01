package com.api.hexagonal_architecture.application.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import com.api.hexagonal_architecture.domain.exception.RecipeNotFoundException;
import com.api.hexagonal_architecture.domain.model.Product;
import com.api.hexagonal_architecture.domain.model.Recipe;
import com.api.hexagonal_architecture.domain.model.RecipeItem;
import com.api.hexagonal_architecture.domain.port.in.RecipeServicePort;
import com.api.hexagonal_architecture.domain.port.out.ProductRepositoryPort;
import com.api.hexagonal_architecture.domain.port.out.RecipeRepositoryPort;

public class RecipeService implements RecipeServicePort {

    private final RecipeRepositoryPort recipeRepository;
    private final ProductRepositoryPort productRepository;

    public RecipeService(RecipeRepositoryPort recipeRepository, ProductRepositoryPort productRepository) {
        this.recipeRepository = recipeRepository;
        this.productRepository = productRepository;
    }

    @Override
    public Recipe create(String name, BigDecimal yield, List<RecipeItem> items) {
        Recipe recipe = Recipe.create(name, yield, items);
        Recipe saved = recipeRepository.save(recipe);

        Product product = Product.createFromRecipe(saved.getName(), saved);
        productRepository.save(product);

        return saved;
    }

    @Override
    public Optional<Recipe> findById(Long id) {
        return recipeRepository.findById(id);
    }

    @Override
    public List<Recipe> findAll() {
        return recipeRepository.findAll();
    }

    @Override
    public Recipe update(Long id, String name, BigDecimal yield, List<RecipeItem> items) {
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new RecipeNotFoundException(id));
        recipe.update(name, yield, items);
        return recipeRepository.save(recipe);
    }

    @Override
    public void delete(Long id) {
        recipeRepository.findById(id)
                .orElseThrow(() -> new RecipeNotFoundException(id));
        recipeRepository.deleteById(id);
    }
}
