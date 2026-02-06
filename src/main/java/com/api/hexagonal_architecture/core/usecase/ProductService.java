package com.api.hexagonal_architecture.core.usecase;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.api.hexagonal_architecture.core.domain.Product;
import com.api.hexagonal_architecture.port.output.ProductRepositoryPort;

@Service
public class ProductService {
    private final ProductRepositoryPort productRepository;

    public ProductService(ProductRepositoryPort productRepository) {
        this.productRepository = productRepository;
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public Optional<Product> findProductById(Long id) {
        return productRepository.findById(id);
    }

    public List<Product> listProduct() {
        return productRepository.findAll();
    }

    public Product updateProduct(Product product) {
        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
