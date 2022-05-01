package com.prgrms.cafe.controller.api;

import com.prgrms.cafe.model.Category;
import com.prgrms.cafe.model.Product;
import com.prgrms.cafe.service.ProductService;
import java.util.List;
import java.util.Optional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductRestController {

    private final ProductService productService;

    public ProductRestController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/api/v1/products")
    public List<Product> products(@RequestParam Optional<Category> category) {
        return category.isPresent() ? productService.getProductsByCategory(category.get())
            : productService.getProducts();
    }

}
