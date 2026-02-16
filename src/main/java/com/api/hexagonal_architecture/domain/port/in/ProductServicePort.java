package com.api.hexagonal_architecture.domain.port.in;

import java.util.List;
import java.util.Optional;

import com.api.hexagonal_architecture.domain.model.Product;

public interface ProductServicePort {
    Product createProduct(Product product);

    Optional<Product> findProductById(Long id);

    List<Product> listProducts();

    Product updateProduct(Long id, String name, java.math.BigDecimal price);

    void deleteProduct(Long id);
}
