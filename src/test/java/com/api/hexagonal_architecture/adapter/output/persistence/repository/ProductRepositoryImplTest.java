package com.api.hexagonal_architecture.adapter.output.persistence.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import com.api.hexagonal_architecture.adapter.output.persistence.mapper.ProductMapper;
import com.api.hexagonal_architecture.core.domain.Product;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@Import({ProductRepositoryImpl.class, ProductMapper.class})
class ProductRepositoryImplTest {

    @Autowired
    private ProductRepositoryImpl productRepository;

    @Test
    void saveShouldPersistAndReturnProductWithGeneratedId() {
        Product product = new Product();
        product.setName("Notebook");
        product.setPrice(new BigDecimal("2500.00"));

        Product saved = productRepository.save(product);

        assertNotNull(saved.getId());
        assertEquals("Notebook", saved.getName());
        assertEquals(new BigDecimal("2500.00"), saved.getPrice());
    }

    @Test
    void findByIdShouldReturnProductWhenExists() {
        Product product = new Product();
        product.setName("Mouse");
        product.setPrice(new BigDecimal("50.00"));
        Product saved = productRepository.save(product);

        Optional<Product> found = productRepository.findById(saved.getId());

        assertTrue(found.isPresent());
        assertEquals("Mouse", found.get().getName());
        assertEquals(new BigDecimal("50.00"), found.get().getPrice());
    }

    @Test
    void findByIdShouldReturnEmptyWhenNotExists() {
        Optional<Product> found = productRepository.findById(999L);

        assertTrue(found.isEmpty());
    }

    @Test
    void findAllShouldReturnAllProducts() {
        Product p1 = new Product();
        p1.setName("Notebook");
        p1.setPrice(new BigDecimal("2500.00"));
        productRepository.save(p1);

        Product p2 = new Product();
        p2.setName("Mouse");
        p2.setPrice(new BigDecimal("50.00"));
        productRepository.save(p2);

        List<Product> products = productRepository.findAll();

        assertEquals(2, products.size());
    }

    @Test
    void deleteByIdShouldRemoveProduct() {
        Product product = new Product();
        product.setName("Keyboard");
        product.setPrice(new BigDecimal("150.00"));
        Product saved = productRepository.save(product);

        productRepository.deleteById(saved.getId());

        Optional<Product> found = productRepository.findById(saved.getId());
        assertTrue(found.isEmpty());
    }
}
