package com.api.hexagonal_architecture.application.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import com.api.hexagonal_architecture.domain.exception.ProductNotFoundException;
import com.api.hexagonal_architecture.domain.model.Product;
import com.api.hexagonal_architecture.domain.port.in.ProductServicePort;
import com.api.hexagonal_architecture.domain.port.out.ProductRepositoryPort;

public class ProductService implements ProductServicePort {
    private final ProductRepositoryPort productRepository;

    public ProductService(ProductRepositoryPort productRepository) {
        this.productRepository = productRepository;
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
    public Product updateProduct(Long id, String name, BigDecimal price, String description) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        product.update(name, price, description);
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
}
