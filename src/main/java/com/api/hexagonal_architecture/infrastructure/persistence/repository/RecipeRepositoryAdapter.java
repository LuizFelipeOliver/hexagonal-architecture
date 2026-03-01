package com.api.hexagonal_architecture.infrastructure.persistence.repository;

import java.util.List;
import java.util.Optional;

import com.api.hexagonal_architecture.domain.model.Recipe;
import com.api.hexagonal_architecture.domain.port.out.RecipeRepositoryPort;
import com.api.hexagonal_architecture.infrastructure.persistence.mapper.RecipePersistenceMapper;

public class RecipeRepositoryAdapter implements RecipeRepositoryPort {

    private final RecipeJpaRepository jpaRepository;
    private final RecipePersistenceMapper mapper;

    public RecipeRepositoryAdapter(RecipeJpaRepository jpaRepository, RecipePersistenceMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Recipe save(Recipe recipe) {
        var entity = mapper.toEntity(recipe);
        var saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<Recipe> findById(Long id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Recipe> findAll() {
        return jpaRepository.findAll().stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }
}
