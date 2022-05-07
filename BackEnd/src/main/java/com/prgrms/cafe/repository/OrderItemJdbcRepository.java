package com.prgrms.cafe.repository;

import static com.prgrms.cafe.repository.JdbcUtils.toUUID;

import com.prgrms.cafe.model.Category;
import com.prgrms.cafe.model.OrderItem;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class OrderItemJdbcRepository implements OrderItemRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public OrderItemJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public OrderItem save(OrderItem orderItem) {
        int update = jdbcTemplate.update(
            "insert into order_items(order_id, product_id, category, price, quantity, created_at, updated_at)"
                + " values(UUID_TO_BIN(:orderId), UUID_TO_BIN(:productId), :category, :price, :quantity, :createdAt, :updatedAt)",
            toOrderItemParamMap(orderItem));

        if (update == 0) {
            throw new RuntimeException("수정이 되지 않았습니다.");
        }

        return orderItem;
    }

    @Override
    public List<OrderItem> findById(UUID orderId) {
        return jdbcTemplate.query(
            "select * from order_items where order_id = UUID_TO_BIN(:orderId)",
            Collections.singletonMap("orderId", orderId.toString().getBytes()), orderItemRowMapper);
    }

    // 상품 로우매퍼
    private static final RowMapper<OrderItem> orderItemRowMapper = (resultSet, i) -> {
        UUID orderId = toUUID(resultSet.getBytes("order_id"));
        UUID productId = toUUID(resultSet.getBytes("product_id"));
        Category category = Category.valueOf(resultSet.getString("category"));
        long price = resultSet.getLong("price");
        int quantity = resultSet.getInt("quantity");
        return new OrderItem(
            orderId, productId, category, price, quantity
        );
    };

    private Map<String, Object> toOrderItemParamMap(OrderItem orderItem) {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("orderId", orderItem.orderId().toString().getBytes());
        paramMap.put("productId", orderItem.productId().toString().getBytes());
        paramMap.put("category", orderItem.category().toString());
        paramMap.put("price", orderItem.price());
        paramMap.put("quantity", orderItem.quantity());
        paramMap.put("createdAt", LocalDateTime.now());
        paramMap.put("updatedAt", LocalDateTime.now());
        return paramMap;
    }

}
