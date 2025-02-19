package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import id.ac.ui.cs.advprog.eshop.service.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductServiceImplTest {
    @Autowired
    private ProductServiceImpl productService;

    private Product product;

    @BeforeEach
    void setUp() {
        // Create a test product
        product = new Product();
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
    }

    @Test
    void testCreateAndFindProduct() {
        // Test create
        Product created = productService.create(product);
        assertNotNull(created.getProductId());
        assertEquals("Sampo Cap Bambang", created.getProductName());
        assertEquals(100, created.getProductQuantity());

        // Test findById using the created product
        Product found = productService.findById(created.getProductId());
        assertNotNull(found);
        assertEquals(created.getProductId(), found.getProductId());
        assertEquals(created.getProductName(), found.getProductName());
        assertEquals(created.getProductQuantity(), found.getProductQuantity());
    }

    @Test
    void testFindAllProducts() {
        // Create multiple products
        Product product1 = new Product();
        product1.setProductName("Product 1");
        product1.setProductQuantity(10);
        productService.create(product1);

        Product product2 = new Product();
        product2.setProductName("Product 2");
        product2.setProductQuantity(20);
        productService.create(product2);

        // Test findAll
        List<Product> products = productService.findAll();
        assertTrue(products.size() >= 2);
        assertTrue(products.stream().anyMatch(p -> p.getProductName().equals("Product 1")));
        assertTrue(products.stream().anyMatch(p -> p.getProductName().equals("Product 2")));
    }

    @Test
    void testUpdateProduct() {
        // First create a product
        Product created = productService.create(product);
        String originalId = created.getProductId();

        // Update the product
        created.setProductName("Updated Name");
        created.setProductQuantity(200);
        Product updated = productService.update(created);

        // Verify the update
        assertNotNull(updated);
        assertEquals(originalId, updated.getProductId());
        assertEquals("Updated Name", updated.getProductName());
        assertEquals(200, updated.getProductQuantity());

        // Verify using findById
        Product found = productService.findById(originalId);
        assertEquals("Updated Name", found.getProductName());
        assertEquals(200, found.getProductQuantity());
    }

    @Test
    void testDeleteProduct() {
        // First create a product
        Product created = productService.create(product);
        String productId = created.getProductId();

        // Verify it exists
        assertNotNull(productService.findById(productId));

        // Delete the product
        productService.delete(productId);

        // Verify it's deleted
        assertNull(productService.findById(productId));
    }

    @Test
    void testFindByIdNonExistent() {
        Product result = productService.findById("non-existent-id");
        assertNull(result);
    }

    @Test
    void testUpdateNonExistentProduct() {
        Product nonExistentProduct = new Product();
        nonExistentProduct.setProductId("non-existent-id");
        nonExistentProduct.setProductName("Test Product");
        nonExistentProduct.setProductQuantity(1);

        Product result = productService.update(nonExistentProduct);
        assertNull(result);
    }

    @Test
    void testFindAllEmptyList() {
        // Delete all existing products first
        List<Product> existingProducts = productService.findAll();
        for (Product p : existingProducts) {
            productService.delete(p.getProductId());
        }

        List<Product> products = productService.findAll();
        assertTrue(products.isEmpty());
    }
}