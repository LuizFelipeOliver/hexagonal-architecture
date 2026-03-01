package com.api.hexagonal_architecture.adapter.in.web;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.hexagonal_architecture.domain.exception.RecipeNotFoundException;
import com.api.hexagonal_architecture.domain.exception.RawMaterialNotFoundException;
import com.api.hexagonal_architecture.domain.model.RawMaterial;
import com.api.hexagonal_architecture.domain.model.Recipe;
import com.api.hexagonal_architecture.domain.model.RecipeItem;
import com.api.hexagonal_architecture.domain.port.in.RawMaterialServicePort;
import com.api.hexagonal_architecture.domain.port.in.RecipeServicePort;

@RestController
@RequestMapping("/api/recipes")
public class RecipeController {

    private final RecipeServicePort recipeService;
    private final RawMaterialServicePort rawMaterialService;

    public RecipeController(RecipeServicePort recipeService, RawMaterialServicePort rawMaterialService) {
        this.recipeService = recipeService;
        this.rawMaterialService = rawMaterialService;
    }

    @PostMapping
    public ResponseEntity<RecipeResponse> create(@RequestBody RecipeRequest request) {
        List<RecipeItem> items = resolveItems(request);
        Recipe created = recipeService.create(request.name(), request.yield(), items);
        return new ResponseEntity<>(RecipeResponse.from(created), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecipeResponse> findById(@PathVariable Long id) {
        Recipe recipe = recipeService.findById(id)
                .orElseThrow(() -> new RecipeNotFoundException(id));
        return ResponseEntity.ok(RecipeResponse.from(recipe));
    }

    @GetMapping
    public ResponseEntity<List<RecipeResponse>> findAll() {
        List<RecipeResponse> list = recipeService.findAll().stream()
                .map(RecipeResponse::from)
                .toList();
        return ResponseEntity.ok(list);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecipeResponse> update(@PathVariable Long id,
                                                  @RequestBody RecipeRequest request) {
        List<RecipeItem> items = resolveItems(request);
        Recipe updated = recipeService.update(id, request.name(), request.yield(), items);
        return ResponseEntity.ok(RecipeResponse.from(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        recipeService.delete(id);
        return ResponseEntity.noContent().build();
    }

    private List<RecipeItem> resolveItems(RecipeRequest request) {
        return request.items().stream()
                .map(itemReq -> {
                    RawMaterial rawMaterial = rawMaterialService.findById(itemReq.rawMaterialId())
                            .orElseThrow(() -> new RawMaterialNotFoundException(itemReq.rawMaterialId()));
                    return new RecipeItem(rawMaterial, itemReq.quantity());
                })
                .toList();
    }
}
