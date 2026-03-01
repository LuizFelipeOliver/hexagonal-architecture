package com.api.hexagonal_architecture.domain.port.out;

import java.util.List;
import java.util.Optional;

import com.api.hexagonal_architecture.domain.model.RawMaterial;

public interface RawMaterialRepositoryPort {
    RawMaterial save(RawMaterial rawMaterial);

    Optional<RawMaterial> findById(Long id);

    List<RawMaterial> findAll();

    void deleteById(Long id);
}
