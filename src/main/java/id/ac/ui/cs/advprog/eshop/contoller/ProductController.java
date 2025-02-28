package id.ac.ui.cs.advprog.eshop.contoller;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequestMapping("/product")
public class ProductController implements CrudController<Product, String> {
    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);
    private static final String PRODUCT_LIST_PAGE = "redirect:/product/list";

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public String getDefaultPage() {
        return PRODUCT_LIST_PAGE;
    }

    @Override
    @GetMapping("/create")
    public String getCreatePage(Model model) {
        model.addAttribute("product", new Product());
        return "CreateProduct";
    }

    @Override
    @PostMapping("/create")
    public String postCreate(@ModelAttribute Product product, Model model) {
        productService.create(product);
        return "redirect:list";
    }

    @Override
    @GetMapping("/list")
    public String getListPage(Model model) {
        List<Product> allProducts = productService.findAll();
        model.addAttribute("products", allProducts);
        return "ProductList";
    }

    @Override
    @GetMapping("/edit/{productId}")
    public String getEditPage(@PathVariable String productId, Model model) {
        Product product = productService.findById(productId);
        model.addAttribute("product", product);
        return "EditProduct";
    }

    @Override
    @PostMapping("/edit/{productId}")
    public String postUpdate(@PathVariable String productId, @ModelAttribute Product product, Model model) {
        logger.info("Updating product ID: {}", productId);
        Product existingProduct = productService.findById(productId);
        existingProduct.setProductName(product.getProductName());
        existingProduct.setProductQuantity(product.getProductQuantity());
        productService.update(existingProduct);
        return PRODUCT_LIST_PAGE;
    }

    @Override
    @GetMapping("/delete/{productId}")
    public String postDelete(@PathVariable String productId) {
        productService.delete(productId);
        return PRODUCT_LIST_PAGE;
    }
}