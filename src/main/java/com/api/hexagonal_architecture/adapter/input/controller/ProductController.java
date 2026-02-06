package com.api.hexagonal_architecture.adapter.input.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

import com.api.hexagonal_architecture.core.domain.Product;
import com.api.hexagonal_architecture.core.usecase.ProductService;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductRequest productRequest) {
        Product product = new Product();
        product.setName(productRequest.name());
        product.setPrice(productRequest.price());

        Product productCreate = productService.createProduct(product);
        ProductResponse response = new ProductResponse(productCreate.getId(), productCreate.getName(),
            productCreate.getPrice());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProduct(@PathVariable Long id) {
        Optional<Product> productOpt = productService.findProductById(id);
        return productOpt.map(product -> ResponseEntity.ok(
            new ProductResponse(
                product.getId(),
                product.getName(),
                product.getPrice())))
            .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> listProduct() {
        List<ProductResponse> products = productService.listProduct().stream()
            .map(p -> new ProductResponse(p.getId(), p.getName(), p.getPrice()))
            .collect(Collectors.toList());

        return ResponseEntity.ok(products);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable Long id,
        @RequestBody ProductRequest productRequest) {
        Product product = new Product();
        product.setId(id);
        product.setName(productRequest.name());
        product.setPrice(productRequest.price());

        Product updateProduct = productService.updateProduct(product);
        ProductResponse response = new ProductResponse(updateProduct.getId(), updateProduct.getName(),
            updateProduct.getPrice());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
