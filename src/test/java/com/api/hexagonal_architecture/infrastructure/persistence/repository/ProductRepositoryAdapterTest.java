package com.api.hexagonal_architecture.infrastructure.persistence.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import com.api.hexagonal_architecture.domain.model.Product;
import com.api.hexagonal_architecture.infrastructure.persistence.mapper.ProductPersistenceMapper;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@Import({ProductRepositoryAdapter.class, ProductPersistenceMapper.class})
class ProductRepositoryAdapterTest {

    @Autowired
    private ProductRepositoryAdapter productRepository;

    @Test
    void saveShouldPersistAndReturnProductWithGeneratedId() {
        Product product = Product.create("Notebook", new BigDecimal("2500.00"), "A powerful notebook");

        Product saved = productRepository.save(product);

        assertNotNull(saved.getId());
        assertEquals("Notebook", saved.getName());
        assertEquals(new BigDecimal("2500.00"), saved.getPrice());
        assertEquals("A powerful notebook", saved.getDescription());
    }

    @Test
    void findByIdShouldReturnProductWhenExists() {
        Product product = Product.create("Mouse", new BigDecimal("50.00"), "Wireless mouse");
        Product saved = productRepository.save(product);

        Optional<Product> found = productRepository.findById(saved.getId());

        assertTrue(found.isPresent());
        assertEquals("Mouse", found.get().getName());
        assertEquals(new BigDecimal("50.00"), found.get().getPrice());
        assertEquals("Wireless mouse", found.get().getDescription());
    }

    @Test
    void findByIdShouldReturnEmptyWhenNotExists() {
        Optional<Product> found = productRepository.findById(999L);

        assertTrue(found.isEmpty());
    }

    @Test
    void findAllShouldReturnAllProducts() {
        productRepository.save(Product.create("Notebook", new BigDecimal("2500.00"), "desc1"));
        productRepository.save(Product.create("Mouse", new BigDecimal("50.00"), "desc2"));

        List<Product> products = productRepository.findAll();

        assertEquals(2, products.size());
    }

    @Test
    void deleteByIdShouldRemoveProduct() {
        Product saved = productRepository.save(Product.create("Keyboard", new BigDecimal("150.00"), "Mechanical keyboard"));

        productRepository.deleteById(saved.getId());

        Optional<Product> found = productRepository.findById(saved.getId());
        assertTrue(found.isEmpty());
    }

    @Test
    void findByNameShouldReturnMatchingProducts() {
        productRepository.save(Product.create("Notebook Pro", new BigDecimal("3000.00"), "desc1"));
        productRepository.save(Product.create("Notebook Air", new BigDecimal("2000.00"), "desc2"));
        productRepository.save(Product.create("Mouse", new BigDecimal("50.00"), "desc3"));

        List<Product> results = productRepository.findByName("notebook");

        assertEquals(2, results.size());
    }
}
