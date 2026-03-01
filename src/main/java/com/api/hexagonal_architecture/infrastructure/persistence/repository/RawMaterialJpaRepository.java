package com.api.hexagonal_architecture.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.hexagonal_architecture.infrastructure.persistence.entity.RawMaterialEntity;

public interface RawMaterialJpaRepository extends JpaRepository<RawMaterialEntity, Long> {
}
