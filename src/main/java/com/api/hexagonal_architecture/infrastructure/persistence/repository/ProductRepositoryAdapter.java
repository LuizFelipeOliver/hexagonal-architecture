package com.api.hexagonal_architecture.infrastructure.persistence.repository;

import java.util.List;
import java.util.Optional;

import com.api.hexagonal_architecture.domain.model.Product;
import com.api.hexagonal_architecture.domain.port.out.ProductRepositoryPort;
import com.api.hexagonal_architecture.infrastructure.persistence.mapper.ProductPersistenceMapper;

public class ProductRepositoryAdapter implements ProductRepositoryPort {
    private final ProductJpaRepository jpaRepository;
    private final ProductPersistenceMapper mapper;

    public ProductRepositoryAdapter(ProductJpaRepository jpaRepository, ProductPersistenceMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Product save(Product product) {
        var entity = mapper.toEntity(product);
        var saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<Product> findById(Long id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Product> findAll() {
        return jpaRepository.findAll().stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public List<Product> findByName(String name) {
        return jpaRepository.findByNameContainingIgnoreCase(name)
                .stream()
                .map(mapper::toDomain)
                .toList();
    }
}
