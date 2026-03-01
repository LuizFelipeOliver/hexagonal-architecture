package com.api.hexagonal_architecture.infrastructure.persistence.mapper;

import com.api.hexagonal_architecture.domain.model.RawMaterial;
import com.api.hexagonal_architecture.infrastructure.persistence.entity.RawMaterialEntity;

public class RawMaterialPersistenceMapper {

    public RawMaterialEntity toEntity(RawMaterial rawMaterial) {
        return new RawMaterialEntity(
                rawMaterial.getId(),
                rawMaterial.getName(),
                rawMaterial.getUnit(),
                rawMaterial.getUnitCost()
        );
    }

    public RawMaterial toDomain(RawMaterialEntity entity) {
        return RawMaterial.reconstruct(
                entity.getId(),
                entity.getName(),
                entity.getUnit(),
                entity.getUnitCost()
        );
    }
}
