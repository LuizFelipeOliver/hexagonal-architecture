package com.api.hexagonal_architecture.infrastructure.persistence.repository;

import java.util.List;
import java.util.Optional;

import com.api.hexagonal_architecture.domain.model.RawMaterial;
import com.api.hexagonal_architecture.domain.port.out.RawMaterialRepositoryPort;
import com.api.hexagonal_architecture.infrastructure.persistence.mapper.RawMaterialPersistenceMapper;

public class RawMaterialRepositoryAdapter implements RawMaterialRepositoryPort {

    private final RawMaterialJpaRepository jpaRepository;
    private final RawMaterialPersistenceMapper mapper;

    public RawMaterialRepositoryAdapter(RawMaterialJpaRepository jpaRepository,
                                        RawMaterialPersistenceMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public RawMaterial save(RawMaterial rawMaterial) {
        var entity = mapper.toEntity(rawMaterial);
        var saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<RawMaterial> findById(Long id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<RawMaterial> findAll() {
        return jpaRepository.findAll().stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }
}
