package com.api.hexagonal_architecture.adapter.output.persistence.mapper;

import com.api.hexagonal_architecture.adapter.output.persistence.entity.ProductEntity;
import com.api.hexagonal_architecture.core.domain.Product;

public class ProductMapper {
    public ProductEntity toEntity(Product product) {
        return new ProductEntity(product.getId(), product.getName(), product.getPrice());
    }

    public Product toDomain(ProductEntity productEntity) {
        return new Product(productEntity.getId(), productEntity.getName(), productEntity.getPrice());
    }
}
