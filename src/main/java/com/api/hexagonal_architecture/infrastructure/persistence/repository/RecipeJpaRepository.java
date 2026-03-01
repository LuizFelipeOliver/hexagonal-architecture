package com.api.hexagonal_architecture.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.hexagonal_architecture.infrastructure.persistence.entity.RecipeEntity;

public interface RecipeJpaRepository extends JpaRepository<RecipeEntity, Long> {
}
