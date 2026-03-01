package com.api.hexagonal_architecture.application.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import com.api.hexagonal_architecture.domain.exception.ProductNotFoundException;
import com.api.hexagonal_architecture.domain.exception.RecipeNotFoundException;
import com.api.hexagonal_architecture.domain.model.Product;
import com.api.hexagonal_architecture.domain.model.Recipe;
import com.api.hexagonal_architecture.domain.port.in.ProductServicePort;
import com.api.hexagonal_architecture.domain.port.out.ProductRepositoryPort;
import com.api.hexagonal_architecture.domain.port.out.RecipeRepositoryPort;

public class ProductService implements ProductServicePort {

    private final ProductRepositoryPort productRepository;
    private final RecipeRepositoryPort recipeRepository;

    public ProductService(ProductRepositoryPort productRepository, RecipeRepositoryPort recipeRepository) {
        this.productRepository = productRepository;
        this.recipeRepository = recipeRepository;
    }

    @Override
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Optional<Product> findProductById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public List<Product> listProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product updateProduct(Long id, String name, String description, BigDecimal price) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        product.update(name, description, price);
        return productRepository.save(product);
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        productRepository.deleteById(id);
    }

    @Override
    public List<Product> findProductByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public Product linkRecipe(Long productId, Long recipeId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new RecipeNotFoundException(recipeId));
        product.linkRecipe(recipe);
        return productRepository.save(product);
    }

    @Override
    public Product unlinkRecipe(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));
        product.unlinkRecipe();
        return productRepository.save(product);
    }
}
