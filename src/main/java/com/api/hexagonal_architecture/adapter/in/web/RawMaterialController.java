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

import com.api.hexagonal_architecture.domain.model.RawMaterial;
import com.api.hexagonal_architecture.domain.port.in.RawMaterialServicePort;

@RestController
@RequestMapping("/api/raw-materials")
public class RawMaterialController {

    private final RawMaterialServicePort rawMaterialService;

    public RawMaterialController(RawMaterialServicePort rawMaterialService) {
        this.rawMaterialService = rawMaterialService;
    }

    @PostMapping
    public ResponseEntity<RawMaterialResponse> create(@RequestBody RawMaterialRequest request) {
        RawMaterial rawMaterial = RawMaterial.create(request.name(), request.unit(), request.unitCost());
        RawMaterial created = rawMaterialService.create(rawMaterial);
        return new ResponseEntity<>(RawMaterialResponse.from(created), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RawMaterialResponse> findById(@PathVariable Long id) {
        RawMaterial rawMaterial = rawMaterialService.findById(id)
                .orElseThrow(() -> new com.api.hexagonal_architecture.domain.exception.RawMaterialNotFoundException(id));
        return ResponseEntity.ok(RawMaterialResponse.from(rawMaterial));
    }

    @GetMapping
    public ResponseEntity<List<RawMaterialResponse>> findAll() {
        List<RawMaterialResponse> list = rawMaterialService.findAll().stream()
                .map(RawMaterialResponse::from)
                .toList();
        return ResponseEntity.ok(list);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RawMaterialResponse> update(@PathVariable Long id,
                                                       @RequestBody RawMaterialRequest request) {
        RawMaterial updated = rawMaterialService.update(id, request.name(), request.unit(), request.unitCost());
        return ResponseEntity.ok(RawMaterialResponse.from(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        rawMaterialService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
