package com.api.hexagonal_architecture.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.api.hexagonal_architecture.application.service.ProductService;
import com.api.hexagonal_architecture.application.service.RawMaterialService;
import com.api.hexagonal_architecture.application.service.RecipeService;
import com.api.hexagonal_architecture.domain.port.in.ProductServicePort;
import com.api.hexagonal_architecture.domain.port.in.RawMaterialServicePort;
import com.api.hexagonal_architecture.domain.port.in.RecipeServicePort;
import com.api.hexagonal_architecture.domain.port.out.ProductRepositoryPort;
import com.api.hexagonal_architecture.domain.port.out.RawMaterialRepositoryPort;
import com.api.hexagonal_architecture.domain.port.out.RecipeRepositoryPort;
import com.api.hexagonal_architecture.infrastructure.persistence.mapper.ProductPersistenceMapper;
import com.api.hexagonal_architecture.infrastructure.persistence.mapper.RawMaterialPersistenceMapper;
import com.api.hexagonal_architecture.infrastructure.persistence.mapper.RecipePersistenceMapper;
import com.api.hexagonal_architecture.infrastructure.persistence.repository.ProductJpaRepository;
import com.api.hexagonal_architecture.infrastructure.persistence.repository.ProductRepositoryAdapter;
import com.api.hexagonal_architecture.infrastructure.persistence.repository.RawMaterialJpaRepository;
import com.api.hexagonal_architecture.infrastructure.persistence.repository.RawMaterialRepositoryAdapter;
import com.api.hexagonal_architecture.infrastructure.persistence.repository.RecipeJpaRepository;
import com.api.hexagonal_architecture.infrastructure.persistence.repository.RecipeRepositoryAdapter;

@Configuration
public class BeanConfig {

    @Bean
    public RawMaterialPersistenceMapper rawMaterialPersistenceMapper() {
        return new RawMaterialPersistenceMapper();
    }

    @Bean
    public RecipePersistenceMapper recipePersistenceMapper(RawMaterialPersistenceMapper rawMaterialMapper) {
        return new RecipePersistenceMapper(rawMaterialMapper);
    }

    @Bean
    public ProductPersistenceMapper productPersistenceMapper(RecipePersistenceMapper recipeMapper) {
        return new ProductPersistenceMapper(recipeMapper);
    }

    @Bean
    public RawMaterialRepositoryPort rawMaterialRepositoryPort(RawMaterialJpaRepository jpaRepository,
                                                                RawMaterialPersistenceMapper mapper) {
        return new RawMaterialRepositoryAdapter(jpaRepository, mapper);
    }

    @Bean
    public RecipeRepositoryPort recipeRepositoryPort(RecipeJpaRepository jpaRepository,
                                                      RecipePersistenceMapper mapper) {
        return new RecipeRepositoryAdapter(jpaRepository, mapper);
    }

    @Bean
    public ProductRepositoryPort productRepositoryPort(ProductJpaRepository jpaRepository,
                                                        ProductPersistenceMapper mapper) {
        return new ProductRepositoryAdapter(jpaRepository, mapper);
    }

    @Bean
    public RawMaterialServicePort rawMaterialServicePort(RawMaterialRepositoryPort repositoryPort) {
        return new RawMaterialService(repositoryPort);
    }

    @Bean
    public RecipeServicePort recipeServicePort(RecipeRepositoryPort recipeRepositoryPort,
                                               ProductRepositoryPort productRepositoryPort) {
        return new RecipeService(recipeRepositoryPort, productRepositoryPort);
    }

    @Bean
    public ProductServicePort productServicePort(ProductRepositoryPort productRepositoryPort,
                                                  RecipeRepositoryPort recipeRepositoryPort) {
        return new ProductService(productRepositoryPort, recipeRepositoryPort);
    }
}
