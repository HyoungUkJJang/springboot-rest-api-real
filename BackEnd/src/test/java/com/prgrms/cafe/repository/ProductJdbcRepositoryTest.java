package com.prgrms.cafe.repository;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.is;

import com.prgrms.cafe.model.Category;
import com.prgrms.cafe.model.Product;
import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.ScriptResolver;
import com.wix.mysql.config.Charset;
import com.wix.mysql.config.MysqldConfig;
import com.wix.mysql.distribution.Version;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
class ProductJdbcRepositoryTest {

    private static final Product VALID_PRODUCT = new Product(
        UUID.randomUUID(), "new-product",
        Category.COFFEE_BEAN_PACKAGE, 1000L, "description");

    private static final Product VALID_UPDATE_PRODUCT = new Product(
        VALID_PRODUCT.getProductId(), "update-product",
        Category.COFFEE_BEAN_PACKAGE, 5000L, "update-description");

    static EmbeddedMysql embeddedMysql;

    @BeforeAll
    static void setUp() {
        MysqldConfig config = MysqldConfig.aMysqldConfig(Version.v8_0_11)
            .withCharset(Charset.UTF8)
            .withPort(2215)
            .withUser("test", "test1234!")
            .withTimeZone("Asia/Seoul")
            .build();

        embeddedMysql = EmbeddedMysql.anEmbeddedMysql(config)
            .addSchema("test-order_mgmt", ScriptResolver.classPathScript("schema.sql"))
            .start();
    }

    @Autowired
    ProductRepository productRepository;

    @AfterAll
    static void cleanUp() {
        embeddedMysql.stop();
    }

    @Test
    @Order(1)
    @DisplayName("상품추가 테스트")
    void testSave() {
        productRepository.save(VALID_PRODUCT);
        Optional<Product> findProduct = productRepository.findById(VALID_PRODUCT.getProductId());
        assertThat(findProduct.isEmpty(), Matchers.is(false));
    }

    @Test
    @Order(2)
    @DisplayName("아이디를 통한 상품조회 테스트")
    void testFindById() {
        Optional<Product> findProduct = productRepository.findById(VALID_PRODUCT.getProductId());
        assertThat(findProduct.isEmpty(), Matchers.is(false));
    }

    @Test
    @Order(3)
    @DisplayName("이름을 통한 상품조회 테스트")
    void testFindByName() {
        Optional<Product> findProduct = productRepository.findByName(VALID_PRODUCT.getProductName());
        assertThat(findProduct.isEmpty(), Matchers.is(false));
    }

    @Test
    @Order(4)
    @DisplayName("카테고리를 통한 상품조회 테스트")
    void testFindByCategory() {
        List<Product> products = productRepository.findByCategory(VALID_PRODUCT.getCategory());
        assertThat(products.isEmpty(), Matchers.is(false));
    }

    @Test
    @Order(5)
    @DisplayName("상품 수정 테스트")
    void testUpdate() {
        productRepository.update(VALID_UPDATE_PRODUCT);
        Optional<Product> findProduct = productRepository.findById(VALID_PRODUCT.getProductId());

        assertThat(findProduct.get(), Matchers.samePropertyValuesAs(VALID_UPDATE_PRODUCT));
    }

    @Test
    @Order(6)
    @DisplayName("상품삭제 테스트")
    void testDeleteAll() {
        productRepository.deleteAll();
        assertThat(productRepository.findAll().isEmpty(), is(true));
    }

}