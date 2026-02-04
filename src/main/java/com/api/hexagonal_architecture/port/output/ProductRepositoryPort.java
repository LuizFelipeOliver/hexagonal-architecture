package com.api.hexagonal_architecture.port.output;

import java.util.List;
import java.util.Optional;

import com.api.hexagonal_architecture.core.domain.Product;

public interface ProductRepositoryPort {
  Product save(Product product);

  Optional<Product> findById(Long id);

  List<Product> findAll();

  void deleteById(Long id);
}
