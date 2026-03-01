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
import com.api.hexagonal_architecture.infrastructure.persistence.mapper.RawMaterialPersistenceMapper;
import com.api.hexagonal_architecture.infrastructure.persistence.mapper.RecipePersistenceMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@ActiveProfiles("test")
@Import({ProductRepositoryAdapter.class, ProductPersistenceMapper.class,
        RecipePersistenceMapper.class, RawMaterialPersistenceMapper.class})
class ProductRepositoryAdapterTest {

    @Autowired
    private ProductRepositoryAdapter productRepository;

    @Test
    void saveShouldPersistAndReturnProductWithGeneratedId() {
        Product product = Product.create("Notebook", "A powerful notebook", new BigDecimal("2500.00"));

        Product saved = productRepository.save(product);

        assertNotNull(saved.getId());
        assertEquals("Notebook", saved.getName());
        assertEquals("A powerful notebook", saved.getDescription());
        assertEquals(new BigDecimal("2500.00"), saved.getPrice());
    }

    @Test
    void findByIdShouldReturnProductWhenExists() {
        Product product = Product.create("Mouse", "Wireless mouse", new BigDecimal("50.00"));
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
        productRepository.save(Product.create("Notebook", "desc1", new BigDecimal("2500.00")));
        productRepository.save(Product.create("Mouse", "desc2", new BigDecimal("50.00")));

        List<Product> products = productRepository.findAll();

        assertEquals(2, products.size());
    }

    @Test
    void deleteByIdShouldRemoveProduct() {
        Product saved = productRepository.save(
                Product.create("Keyboard", "Mechanical keyboard", new BigDecimal("150.00")));

        productRepository.deleteById(saved.getId());

        Optional<Product> found = productRepository.findById(saved.getId());
        assertTrue(found.isEmpty());
    }

    @Test
    void findByNameShouldReturnMatchingProducts() {
        productRepository.save(Product.create("Notebook Pro", "desc1", new BigDecimal("3000.00")));
        productRepository.save(Product.create("Notebook Air", "desc2", new BigDecimal("2000.00")));
        productRepository.save(Product.create("Mouse", "desc3", new BigDecimal("50.00")));

        List<Product> results = productRepository.findByName("notebook");

        assertEquals(2, results.size());
    }
}
