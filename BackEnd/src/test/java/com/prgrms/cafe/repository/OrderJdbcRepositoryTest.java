package com.prgrms.cafe.repository;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.is;
import com.prgrms.cafe.model.Email;
import com.prgrms.cafe.model.Order;
import com.prgrms.cafe.model.OrderStatus;
import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.ScriptResolver;
import com.wix.mysql.config.Charset;
import com.wix.mysql.config.MysqldConfig;
import com.wix.mysql.distribution.Version;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
class OrderJdbcRepositoryTest {

    private static final Order VALID_ORDER = new Order(
        UUID.randomUUID(), new Email("dnr759@naver.com"), "address", "12345",
        Collections.emptyList(), OrderStatus.ACCEPTED, LocalDateTime.now(), LocalDateTime.now()
    );

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

    @AfterAll
    static void cleanUp() {
        embeddedMysql.stop();
    }

    @Autowired
    OrderRepository orderRepository;

    @Test
    @org.junit.jupiter.api.Order(1)
    @DisplayName("주문추가 테스트")
    void testSave() {
        orderRepository.save(VALID_ORDER);
        Optional<Order> findOrder = orderRepository.findById(VALID_ORDER.getOrderId());
        assertThat(findOrder.isEmpty(), is(false));
    }

    @Test
    @org.junit.jupiter.api.Order(1)
    @DisplayName("주문 아이디를 통한 상세조회 테스트")
    void testFindById() {
        Optional<Order> findOrder = orderRepository.findById(VALID_ORDER.getOrderId());
        assertThat(findOrder.isEmpty(), is(false));
    }

    @Test
    @org.junit.jupiter.api.Order(3)
    @DisplayName("주문 업데이트 테스트")
    void testUpdate() {
        orderRepository.update(VALID_ORDER.getOrderId(), "new address", "67890");
        Optional<Order> findOrder = orderRepository.findById(VALID_ORDER.getOrderId());

        assertThat(findOrder.get().getAddress().equals("new address"), is(true));
    }

    @Test
    @org.junit.jupiter.api.Order(4)
    @DisplayName("주문 삭제 테스트")
    void testDeleteById() {
        orderRepository.deleteById(VALID_ORDER.getOrderId());
        Optional<Order> findOrder = orderRepository.findById(VALID_ORDER.getOrderId());

        assertThat(findOrder.isEmpty(), is(true));
    }

}