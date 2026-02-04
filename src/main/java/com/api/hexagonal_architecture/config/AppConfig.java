package com.api.hexagonal_architecture.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.api.hexagonal_architecture.adapter.output.persistence.mapper.ProductMapper;
import com.api.hexagonal_architecture.adapter.output.persistence.repository.ProductJpaRepository;
import com.api.hexagonal_architecture.adapter.output.persistence.repository.ProductRepositoryImpl;
import com.api.hexagonal_architecture.core.usecase.ProductService;
import com.api.hexagonal_architecture.port.output.ProductRepositoryPort;

@Configuration
public class AppConfig {
  @Bean
  public ProductService productService(ProductRepositoryPort productRepositoryPort) {
    return new ProductService(productRepositoryPort);
  }

  @Bean
  public ProductRepositoryPort productRepositoryPort(ProductJpaRepository productRepository,
      ProductMapper productMapper) {
    return new ProductRepositoryImpl(productRepository, productMapper);
  }

  @Bean
  public ProductMapper productMapper() {
    return new ProductMapper();
  }
}
