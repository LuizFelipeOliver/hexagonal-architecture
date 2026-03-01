package com.api.hexagonal_architecture.adapter.in.web;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.api.hexagonal_architecture.domain.exception.ProductNotFoundException;
import com.api.hexagonal_architecture.domain.model.Product;
import com.api.hexagonal_architecture.domain.port.in.ProductServicePort;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductServicePort productService;

    public ProductController(ProductServicePort productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductRequest request) {
        Product product = Product.create(request.name(), request.description(), request.price());
        Product created = productService.createProduct(product);
        return new ResponseEntity<>(ProductResponse.from(created), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProduct(@PathVariable Long id) {
        Product product = productService.findProductById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        return ResponseEntity.ok(ProductResponse.from(product));
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> listProducts() {
        List<ProductResponse> products = productService.listProducts().stream()
                .map(ProductResponse::from)
                .toList();
        return ResponseEntity.ok(products);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable Long id,
                                                          @RequestBody ProductRequest request) {
        Product updated = productService.updateProduct(id, request.name(), request.description(), request.price());
        return ResponseEntity.ok(ProductResponse.from(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductResponse>> findByName(@RequestParam String name) {
        List<ProductResponse> products = productService.findProductByName(name).stream()
                .map(ProductResponse::from)
                .toList();
        return ResponseEntity.ok(products);
    }

    @PatchMapping("/{id}/recipe/{recipeId}")
    public ResponseEntity<ProductResponse> linkRecipe(@PathVariable Long id,
                                                       @PathVariable Long recipeId) {
        Product updated = productService.linkRecipe(id, recipeId);
        return ResponseEntity.ok(ProductResponse.from(updated));
    }

    @DeleteMapping("/{id}/recipe")
    public ResponseEntity<ProductResponse> unlinkRecipe(@PathVariable Long id) {
        Product updated = productService.unlinkRecipe(id);
        return ResponseEntity.ok(ProductResponse.from(updated));
    }
}
