package com.api.hexagonal_architecture.adapter.output.persistence.repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.api.hexagonal_architecture.adapter.output.persistence.mapper.ProductMapper;
import com.api.hexagonal_architecture.core.domain.Product;
import com.api.hexagonal_architecture.port.output.ProductRepositoryPort;

@Repository
public class ProductRepositoryImpl implements ProductRepositoryPort {
    private final ProductJpaRepository productJpaRepository;
    private final ProductMapper productMapper;

    public ProductRepositoryImpl(ProductJpaRepository productJpaRepository, ProductMapper productMapper) {
        this.productJpaRepository = productJpaRepository;
        this.productMapper = productMapper;
    }

    @Override
    public Product save(Product product) {
        var productEntity = productMapper.toEntity(product);
        var savedEntity = productJpaRepository.save(productEntity);
        return productMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Product> findById(Long id) {
        return productJpaRepository.findById(id)
            .map(productMapper::toDomain);
    }

    @Override
    public List<Product> findAll() {
        return productJpaRepository.findAll().stream()
            .map(productMapper::toDomain)
            .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        productJpaRepository.deleteById(id);
    }
}
