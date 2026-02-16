package com.api.hexagonal_architecture.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.api.hexagonal_architecture.application.service.ProductService;
import com.api.hexagonal_architecture.domain.port.in.ProductServicePort;
import com.api.hexagonal_architecture.domain.port.out.ProductRepositoryPort;
import com.api.hexagonal_architecture.infrastructure.persistence.mapper.ProductPersistenceMapper;
import com.api.hexagonal_architecture.infrastructure.persistence.repository.ProductJpaRepository;
import com.api.hexagonal_architecture.infrastructure.persistence.repository.ProductRepositoryAdapter;

@Configuration
public class BeanConfig {
    @Bean
    public ProductPersistenceMapper productPersistenceMapper() {
        return new ProductPersistenceMapper();
    }

    @Bean
    public ProductRepositoryPort productRepositoryPort(ProductJpaRepository jpaRepository,
        ProductPersistenceMapper mapper) {
        return new ProductRepositoryAdapter(jpaRepository, mapper);
    }

    @Bean
    public ProductServicePort productServicePort(ProductRepositoryPort repositoryPort) {
        return new ProductService(repositoryPort);
    }
}
