package com.api.hexagonal_architecture.domain.port.in;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import com.api.hexagonal_architecture.domain.model.RawMaterial;
import com.api.hexagonal_architecture.domain.model.RawMaterial.Unit;

public interface RawMaterialServicePort {
    RawMaterial create(RawMaterial rawMaterial);

    Optional<RawMaterial> findById(Long id);

    List<RawMaterial> findAll();

    RawMaterial update(Long id, String name, Unit unit, BigDecimal unitCost);

    void delete(Long id);
}
