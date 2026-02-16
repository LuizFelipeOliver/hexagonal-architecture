package com.api.hexagonal_architecture.domain.port.out;

import java.util.List;
import java.util.Optional;

import com.api.hexagonal_architecture.domain.model.Product;

public interface ProductRepositoryPort {
    Product save(Product product);

    Optional<Product> findById(Long id);

    List<Product> findAll();

    void deleteById(Long id);
}
