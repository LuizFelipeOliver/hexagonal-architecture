package com.api.hexagonal_architecture.port.input;

import java.util.List;
import java.util.Optional;

import com.api.hexagonal_architecture.core.domain.Product;

public interface ProductServicePort {
    Product cretateProduct(Product product);

    Optional<Product> getProduct(Long id);

    List<Product> listProduct();

    Product updateProduct(Product product);

    void deleteProduct(Long id);
}
