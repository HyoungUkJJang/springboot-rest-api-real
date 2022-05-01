package com.prgrms.cafe.service;

import com.prgrms.cafe.model.Category;
import com.prgrms.cafe.model.Product;
import java.util.List;
import java.util.UUID;

public interface ProductService {

    List<Product> getProducts();

    List<Product> getProductsByCategory(Category category);

    Product getProductByProductName(String productName);

    Product getProductByProductId(UUID productId);

    Product createProduct(String productName, Category category, long price);

    Product createProduct(String productName, Category category, long price, String description);

}
