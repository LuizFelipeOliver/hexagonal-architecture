package com.api.hexagonal_architecture.infrastructure.persistence.mapper;

import com.api.hexagonal_architecture.domain.model.Product;
import com.api.hexagonal_architecture.infrastructure.persistence.entity.ProductEntity;

public class ProductPersistenceMapper {
    public ProductEntity toEntity(Product product) {
        return new ProductEntity(product.getId(), product.getName(), product.getPrice());
    }

    public Product toDomain(ProductEntity entity) {
        return Product.reconstruct(entity.getId(), entity.getName(), entity.getPrice());
    }
}
