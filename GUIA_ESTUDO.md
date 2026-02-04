# Guia de Estudo - Arquitetura Hexagonal

## Estrutura do Projeto

```
src/main/java/com/api/hexagonal_architecture/
├── adapter/
│   ├── input/controller/        # Controllers REST (entrada)
│   └── output/persistence/      # Banco de dados (saída)
│       ├── entity/              # Entidades JPA
│       ├── mapper/              # Conversores Domain <-> Entity
│       └── repository/          # Implementações de repositório
├── core/
│   ├── domain/                  # Modelos de domínio (negócio puro)
│   └── usecase/                 # Serviços (lógica de negócio)
├── port/
│   ├── input/                   # Interfaces de entrada
│   └── output/                  # Interfaces de saída
└── config/                      # Configuração Spring
```

---

## Responsabilidade de Cada Arquivo

| Arquivo | Camada | Responsabilidade |
|---------|--------|------------------|
| `Domain.java` | core/domain | Entidade de negócio pura, sem frameworks |
| `ServicePort.java` | port/input | Contrato do caso de uso (interface) |
| `RepositoryPort.java` | port/output | Contrato de persistência (interface) |
| `Service.java` | core/usecase | Lógica de negócio e orquestração |
| `Entity.java` | adapter/output/entity | Mapeamento JPA para o banco |
| `Mapper.java` | adapter/output/mapper | Converte Domain para Entity e vice-versa |
| `JpaRepository.java` | adapter/output/repository | Interface Spring Data JPA |
| `RepositoryImpl.java` | adapter/output/repository | Implementa a porta de saída |
| `Request.java` | adapter/input/controller | DTO de entrada HTTP |
| `Response.java` | adapter/input/controller | DTO de saída HTTP |
| `Controller.java` | adapter/input/controller | Endpoints REST |
| `AppConfig.java` | config | Injeção de dependência dos beans |

---

## Fluxo de Dados

```
Request HTTP → Controller → Service → RepositoryImpl → JPA → Banco
                  ↓            ↓            ↓
              Request      Domain       Entity
                DTO        Object       (JPA)
```

---

## Criando Category: Passo a Passo

### 1. Domain (Modelo de Negócio)

**`core/domain/Category.java`**

```java
package com.api.hexagonal_architecture.core.domain;

public class Category {
    private Long id;
    private String name;
    private String description;

    public Category() {}

    public Category(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
```

---

### 2. Output Port (Interface de Repositório)

**`port/output/CategoryRepositoryPort.java`**

```java
package com.api.hexagonal_architecture.port.output;

import com.api.hexagonal_architecture.core.domain.Category;
import java.util.List;
import java.util.Optional;

public interface CategoryRepositoryPort {
    Category save(Category category);
    Optional<Category> findById(Long id);
    List<Category> findAll();
    void deleteById(Long id);
}
```

---

### 3. Service (Caso de Uso)

**`core/usecase/CategoryService.java`**

```java
package com.api.hexagonal_architecture.core.usecase;

import com.api.hexagonal_architecture.core.domain.Category;
import com.api.hexagonal_architecture.port.output.CategoryRepositoryPort;
import java.util.List;
import java.util.Optional;

public class CategoryService {
    private final CategoryRepositoryPort categoryRepository;

    public CategoryService(CategoryRepositoryPort categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    public Optional<Category> getCategory(Long id) {
        return categoryRepository.findById(id);
    }

    public List<Category> listCategories() {
        return categoryRepository.findAll();
    }

    public Category updateCategory(Category category) {
        return categoryRepository.save(category);
    }

    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}
```

---

### 4. Entity (Entidade JPA)

**`adapter/output/persistence/entity/CategoryEntity.java`**

```java
package com.api.hexagonal_architecture.adapter.output.persistence.entity;

import com.api.hexagonal_architecture.core.domain.Category;
import jakarta.persistence.*;

@Entity
@Table(name = "categories")
public class CategoryEntity extends Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;

    public CategoryEntity() {}

    public CategoryEntity(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    @Override
    public Long getId() { return id; }
    @Override
    public void setId(Long id) { this.id = id; }
    @Override
    public String getName() { return name; }
    @Override
    public void setName(String name) { this.name = name; }
    @Override
    public String getDescription() { return description; }
    @Override
    public void setDescription(String description) { this.description = description; }
}
```

---

### 5. Mapper

**`adapter/output/persistence/mapper/CategoryMapper.java`**

```java
package com.api.hexagonal_architecture.adapter.output.persistence.mapper;

import com.api.hexagonal_architecture.adapter.output.persistence.entity.CategoryEntity;
import com.api.hexagonal_architecture.core.domain.Category;

public class CategoryMapper {
    public CategoryEntity toEntity(Category category) {
        return new CategoryEntity(category.getId(), category.getName(), category.getDescription());
    }

    public Category toDomain(CategoryEntity entity) {
        return new Category(entity.getId(), entity.getName(), entity.getDescription());
    }
}
```

---

### 6. JPA Repository

**`adapter/output/persistence/repository/CategoryJpaRepository.java`**

```java
package com.api.hexagonal_architecture.adapter.output.persistence.repository;

import com.api.hexagonal_architecture.adapter.output.persistence.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryJpaRepository extends JpaRepository<CategoryEntity, Long> {}
```

---

### 7. Repository Implementation

**`adapter/output/persistence/repository/CategoryRepositoryImpl.java`**

```java
package com.api.hexagonal_architecture.adapter.output.persistence.repository;

import com.api.hexagonal_architecture.adapter.output.persistence.mapper.CategoryMapper;
import com.api.hexagonal_architecture.core.domain.Category;
import com.api.hexagonal_architecture.port.output.CategoryRepositoryPort;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public class CategoryRepositoryImpl implements CategoryRepositoryPort {
    private final CategoryJpaRepository jpaRepository;
    private final CategoryMapper mapper;

    public CategoryRepositoryImpl(CategoryJpaRepository jpaRepository, CategoryMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Category save(Category category) {
        var entity = mapper.toEntity(category);
        var saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<Category> findById(Long id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Category> findAll() {
        return jpaRepository.findAll().stream().map(mapper::toDomain).toList();
    }

    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }
}
```

---

### 8. DTOs

**`adapter/input/controller/CategoryRequest.java`**

```java
package com.api.hexagonal_architecture.adapter.input.controller;

public record CategoryRequest(String name, String description) {}
```

**`adapter/input/controller/CategoryResponse.java`**

```java
package com.api.hexagonal_architecture.adapter.input.controller;

public record CategoryResponse(Long id, String name, String description) {}
```

---

### 9. Controller

**`adapter/input/controller/CategoryController.java`**

```java
package com.api.hexagonal_architecture.adapter.input.controller;

import com.api.hexagonal_architecture.core.domain.Category;
import com.api.hexagonal_architecture.core.usecase.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<CategoryResponse> create(@RequestBody CategoryRequest request) {
        Category category = new Category();
        category.setName(request.name());
        category.setDescription(request.description());
        Category created = categoryService.createCategory(category);
        return new ResponseEntity<>(
            new CategoryResponse(created.getId(), created.getName(), created.getDescription()),
            HttpStatus.CREATED
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> getById(@PathVariable Long id) {
        return categoryService.getCategory(id)
            .map(c -> new CategoryResponse(c.getId(), c.getName(), c.getDescription()))
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<CategoryResponse> getAll() {
        return categoryService.listCategories().stream()
            .map(c -> new CategoryResponse(c.getId(), c.getName(), c.getDescription()))
            .toList();
    }

    @PutMapping("/{id}")
    public CategoryResponse update(@PathVariable Long id, @RequestBody CategoryRequest request) {
        Category category = new Category(id, request.name(), request.description());
        Category updated = categoryService.updateCategory(category);
        return new CategoryResponse(updated.getId(), updated.getName(), updated.getDescription());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}
```

---

### 10. Configuração (adicionar ao AppConfig.java)

```java
@Bean
public CategoryService categoryService(CategoryRepositoryPort port) {
    return new CategoryService(port);
}

@Bean
public CategoryRepositoryPort categoryRepositoryPort(CategoryJpaRepository repo, CategoryMapper mapper) {
    return new CategoryRepositoryImpl(repo, mapper);
}

@Bean
public CategoryMapper categoryMapper() {
    return new CategoryMapper();
}
```

---

## Checklist

- [ ] `core/domain/Category.java`
- [ ] `port/output/CategoryRepositoryPort.java`
- [ ] `core/usecase/CategoryService.java`
- [ ] `adapter/output/persistence/entity/CategoryEntity.java`
- [ ] `adapter/output/persistence/mapper/CategoryMapper.java`
- [ ] `adapter/output/persistence/repository/CategoryJpaRepository.java`
- [ ] `adapter/output/persistence/repository/CategoryRepositoryImpl.java`
- [ ] `adapter/input/controller/CategoryRequest.java`
- [ ] `adapter/input/controller/CategoryResponse.java`
- [ ] `adapter/input/controller/CategoryController.java`
- [ ] Beans no `config/AppConfig.java`
