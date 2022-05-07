package com.prgrms.cafe.controller;

import com.prgrms.cafe.controller.dto.CreateProductRequest;
import com.prgrms.cafe.controller.dto.UpdateProductRequest;
import com.prgrms.cafe.service.ProductService;
import java.util.UUID;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/new-product")
    public String newProductPage() {
        return "new-product";
    }

    @GetMapping("/products/{productId}")
    public String productPage(@PathVariable UUID productId, Model model) {
        model.addAttribute("product", productService.getProductByProductId(productId));

        return "product";
    }

    @GetMapping("/products/update/{productId}")
    public String productUpdatePage(@PathVariable UUID productId, Model model) {
        model.addAttribute("product", productService.getProductByProductId(productId));

        return "update-product";
    }

    @GetMapping("/products/delete/{productId}")
    public String deleteProduct(@PathVariable UUID productId, Model model) {
        productService.removeProduct(productId);
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

    @PostMapping("/products/update/{productId}")
    public String updateProduct(@PathVariable UUID productId, UpdateProductRequest updateProductRequest) {
        productService.updateProduct(
            productId,
            updateProductRequest.productName(),
            updateProductRequest.category(),
            updateProductRequest.price(),
            updateProductRequest.description()
        );

        return "redirect:/products/" + productId;
    }

}
