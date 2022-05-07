package com.prgrms.cafe.repository;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.is;
import com.prgrms.cafe.model.Category;
import com.prgrms.cafe.model.Email;
import com.prgrms.cafe.model.Order;
import com.prgrms.cafe.model.OrderItem;
import com.prgrms.cafe.model.OrderStatus;
import com.prgrms.cafe.model.Product;
import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.ScriptResolver;
import com.wix.mysql.config.Charset;
import com.wix.mysql.config.MysqldConfig;
import com.wix.mysql.distribution.Version;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.validation.annotation.Validated;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
@ActiveProfiles("test")
class OrderItemJdbcRepositoryTest {

    private final Order VALID_ORDER = new Order(
        UUID.randomUUID(), new Email("dnr759@naver.com"), "address", "12345",
        Collections.emptyList(), OrderStatus.ACCEPTED, LocalDateTime.now(), LocalDateTime.now()
    );

    private final Product VALID_PRODUCT = new Product(
        UUID.randomUUID(), "new-product",
        Category.STARBUCKS_COFFEE_PACKAGE, 1000L, "description");

    @Autowired
    OrderItemRepository orderItemRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    ProductRepository productRepository;

    static EmbeddedMysql embeddedMysql;

    @BeforeAll
    void setUp() {
        MysqldConfig config = MysqldConfig.aMysqldConfig(Version.v8_0_11)
            .withCharset(Charset.UTF8)
            .withPort(2215)
            .withUser("test", "test1234!")
            .withTimeZone("Asia/Seoul")
            .build();

        embeddedMysql = EmbeddedMysql.anEmbeddedMysql(config)
            .addSchema("test-order_mgmt", ScriptResolver.classPathScript("schema.sql"))
            .start();

        orderRepository.save(VALID_ORDER);
        productRepository.save(VALID_PRODUCT);
    }

    @AfterAll
    void cleanUp() {
        embeddedMysql.stop();
    }

    @Test
    @org.junit.jupiter.api.Order(1)
    @DisplayName("아이템 저장 및 조회 테스트")
    void saveTest() {
        List<OrderItem> orderItems = List.of(
            new OrderItem(VALID_ORDER.getOrderId(), VALID_PRODUCT.getProductId(),
                Category.STARBUCKS_COFFEE_PACKAGE, 1000L, 3));
        orderItems.forEach(item -> orderItemRepository.save(item));

        List<OrderItem> findOrderItems = orderItemRepository.findById(VALID_ORDER.getOrderId());

        assertThat(findOrderItems.size(), is(1));
    }

}