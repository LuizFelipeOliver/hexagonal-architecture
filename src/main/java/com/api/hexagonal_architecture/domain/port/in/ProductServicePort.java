package com.api.hexagonal_architecture.domain.port.in;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import com.api.hexagonal_architecture.domain.model.Product;

public interface ProductServicePort {
    Product createProduct(Product product);

    Optional<Product> findProductById(Long id);

    List<Product> listProducts();

    Product updateProduct(Long id, String name, String description, BigDecimal price);

    void deleteProduct(Long id);

    List<Product> findProductByName(String name);

    Product linkRecipe(Long productId, Long recipeId);

    Product unlinkRecipe(Long productId);
}
