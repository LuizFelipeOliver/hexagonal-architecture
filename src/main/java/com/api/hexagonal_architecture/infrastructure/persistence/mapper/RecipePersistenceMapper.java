package com.api.hexagonal_architecture.infrastructure.persistence.mapper;

import java.util.List;

import com.api.hexagonal_architecture.domain.model.Recipe;
import com.api.hexagonal_architecture.domain.model.RecipeItem;
import com.api.hexagonal_architecture.infrastructure.persistence.entity.RecipeEntity;
import com.api.hexagonal_architecture.infrastructure.persistence.entity.RecipeItemEntity;

public class RecipePersistenceMapper {

    private final RawMaterialPersistenceMapper rawMaterialMapper;

    public RecipePersistenceMapper(RawMaterialPersistenceMapper rawMaterialMapper) {
        this.rawMaterialMapper = rawMaterialMapper;
    }

    public RecipeEntity toEntity(Recipe recipe) {
        List<RecipeItemEntity> itemEntities = recipe.getItems().stream()
                .map(this::toItemEntity)
                .toList();
        return new RecipeEntity(recipe.getId(), recipe.getName(), recipe.getYield(), itemEntities);
    }

    public Recipe toDomain(RecipeEntity entity) {
        List<RecipeItem> items = entity.getItems().stream()
                .map(this::toItemDomain)
                .toList();
        return Recipe.reconstruct(entity.getId(), entity.getName(), entity.getYield(), items);
    }

    private RecipeItemEntity toItemEntity(RecipeItem item) {
        return new RecipeItemEntity(
                rawMaterialMapper.toEntity(item.getRawMaterial()),
                item.getQuantity()
        );
    }

    private RecipeItem toItemDomain(RecipeItemEntity entity) {
        return new RecipeItem(
                rawMaterialMapper.toDomain(entity.getRawMaterial()),
                entity.getQuantity()
        );
    }
}
