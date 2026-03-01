package com.api.hexagonal_architecture.application.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import com.api.hexagonal_architecture.domain.exception.RawMaterialNotFoundException;
import com.api.hexagonal_architecture.domain.model.RawMaterial;
import com.api.hexagonal_architecture.domain.model.RawMaterial.Unit;
import com.api.hexagonal_architecture.domain.port.in.RawMaterialServicePort;
import com.api.hexagonal_architecture.domain.port.out.RawMaterialRepositoryPort;

public class RawMaterialService implements RawMaterialServicePort {

    private final RawMaterialRepositoryPort repository;

    public RawMaterialService(RawMaterialRepositoryPort repository) {
        this.repository = repository;
    }

    @Override
    public RawMaterial create(RawMaterial rawMaterial) {
        return repository.save(rawMaterial);
    }

    @Override
    public Optional<RawMaterial> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<RawMaterial> findAll() {
        return repository.findAll();
    }

    @Override
    public RawMaterial update(Long id, String name, Unit unit, BigDecimal unitCost) {
        RawMaterial rawMaterial = repository.findById(id)
                .orElseThrow(() -> new RawMaterialNotFoundException(id));
        rawMaterial.update(name, unit, unitCost);
        return repository.save(rawMaterial);
    }

    @Override
    public void delete(Long id) {
        repository.findById(id)
                .orElseThrow(() -> new RawMaterialNotFoundException(id));
        repository.deleteById(id);
    }
}
