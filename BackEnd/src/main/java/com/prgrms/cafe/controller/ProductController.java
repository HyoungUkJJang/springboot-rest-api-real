package com.prgrms.cafe.controller;

import com.prgrms.cafe.controller.dto.CreateProductRequest;
import com.prgrms.cafe.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public String productsPage(Model model) {
        model.addAttribute("products", productService.getProducts());
        return "products";
    }

    @PostMapping("/products")
    public String newProduct(CreateProductRequest createProductRequest) {
        productService.createProduct(
            createProductRequest.productName(),
            createProductRequest.category(),
            createProductRequest.price(),
            createProductRequest.description());
        return "redirect:/products";
    }

    @GetMapping("/new-product")
    public String newProductPage() {
        return "new-product";
    }

}
