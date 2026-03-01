package com.api.hexagonal_architecture.domain.exception;

public class RawMaterialNotFoundException extends RuntimeException {
    public RawMaterialNotFoundException(Long id) {
        super("RawMaterial not found with id: " + id);
    }
}
