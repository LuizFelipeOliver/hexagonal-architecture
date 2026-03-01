package com.api.hexagonal_architecture.infrastructure.persistence.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "recipe")
public class RecipeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private BigDecimal yield;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<RecipeItemEntity> items = new ArrayList<>();

    public RecipeEntity() {
    }

    public RecipeEntity(Long id, String name, BigDecimal yield, List<RecipeItemEntity> items) {
        this.id = id;
        this.name = name;
        this.yield = yield;
        this.items = items != null ? items : new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getYield() {
        return yield;
    }

    public void setYield(BigDecimal yield) {
        this.yield = yield;
    }

    public List<RecipeItemEntity> getItems() {
        return items;
    }

    public void setItems(List<RecipeItemEntity> items) {
        this.items = items;
    }
}
