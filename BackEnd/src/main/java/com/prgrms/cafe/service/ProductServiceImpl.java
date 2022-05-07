package com.prgrms.cafe.service;

import com.prgrms.cafe.model.Category;
import com.prgrms.cafe.model.Product;
import com.prgrms.cafe.repository.ProductRepository;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getProductsByCategory(Category category) {
        return productRepository.findByCategory(category);
    }

    @Override
    public Product getProductByProductName(String productName) {
        return productRepository.findByName(productName)
            .orElseThrow(NoSuchElementException::new);
    }

    @Override
    public Product getProductByProductId(UUID productId) {
        return productRepository.findById(productId)
            .orElseThrow(NoSuchElementException::new);
    }

    @Override
    public Product createProduct(String productName, Category category, long price) {
        return productRepository.save(
            new Product(UUID.randomUUID(), productName, category, price)
        );
    }

    @Override
    @Transactional
    public Product createProduct(String productName, Category category, long price, String description) {
        return productRepository.save(
            new Product(UUID.randomUUID(), productName, category, price, description)
        );
    }

    @Override
    @Transactional
    public Product updateProduct(UUID productId, String productName, Category category, long price,
        String description) {
        return productRepository.update(
            new Product(productId, productName, category, price, description)
        );
    }

    @Override
    @Transactional
    public void removeProduct(UUID productId) {
        productRepository.deleteById(productId);
    }

}
