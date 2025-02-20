package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.contoller.ProductController;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ProductControllerTest {

    @InjectMocks
    private ProductController productController;

    @Mock
    private ProductServiceImpl productService;

    @Mock
    private Model model;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateProductPage() {
        String viewName = productController.createProductPage(model);
        assertEquals("CreateProduct", viewName);
    }

    @Test
    void testCreateProductPost() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Test Product");
        product.setProductQuantity(10);

        String viewName = productController.createProductPost(product, model);
        assertEquals("redirect:list", viewName);
    }

    @Test
    void testProductListPage() {
        String viewName = productController.productListPage(model);
        assertEquals("ProductList", viewName);
    }

    @Test
    void testEditProductPage() {
        String productId = "1";
        String viewName = productController.editProductPage(productId, model);
        assertEquals("EditProduct", viewName);
    }

    @Test
    void testEditProductPost() {

        // Create initial product
        Product initialProduct = new Product();
        initialProduct.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        initialProduct.setProductName("Sampo Cap Bambang");
        initialProduct.setProductQuantity(10);

        // Mock service behavior: findById should return initialProduct
        when(productService.findById("eb558e9f-1c39-460e-8860-71af6af63bd6")).thenReturn(initialProduct);

        // Create updated product
        Product updatedProduct = new Product();
        updatedProduct.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        updatedProduct.setProductName("Sampo Cap Bambang");
        updatedProduct.setProductQuantity(20);

        // Call the method
        String viewName = productController.editProductPost("eb558e9f-1c39-460e-8860-71af6af63bd6", updatedProduct, model);

        // Assertions
        assertEquals("redirect:/product/list", viewName);
        assertEquals(20, initialProduct.getProductQuantity()); // Ensure update happened

        // Verify that update method was called with modified product
        verify(productService).update(initialProduct);
    }

    @Test
    void testDeleteProduct() {
        String productId = "1";
        String viewName = productController.deleteProduct(productId);
        assertEquals("redirect:/product/list", viewName);
    }
}