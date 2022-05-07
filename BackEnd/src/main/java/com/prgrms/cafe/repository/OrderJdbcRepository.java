package com.prgrms.cafe.repository;

import static com.prgrms.cafe.repository.JdbcUtils.toLocalDateTime;
import static com.prgrms.cafe.repository.JdbcUtils.toUUID;

import com.prgrms.cafe.model.Email;
import com.prgrms.cafe.model.Order;
import com.prgrms.cafe.model.OrderStatus;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class OrderJdbcRepository implements OrderRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public OrderJdbcRepository(
        NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Order save(Order order) {
        int update = jdbcTemplate.update(
            "insert into orders(order_id, email, address, postcode, order_status, created_at, updated_at)"
                + " values(UUID_TO_BIN(:orderId), :email, :address, :postcode, :orderStatus, :createdAt, :updatedAt)",
            toOrderParamMap(order));
        if (update == 0) {
            throw new RuntimeException("저장이 되지 않았습니다.");
        }

        return order;
    }

    @Override
    public void update(UUID orderId, String address, String postCode) {
        jdbcTemplate.update(
            "update orders set address = :address, postcode = :postcode, updated_at = :updatedAt"
                + " where order_id = UUID_TO_BIN(:orderId)",
            new HashMap<String, Object>() {{
                put("orderId", orderId.toString().getBytes());
                put("address", address);
                put("postcode", postCode);
                put("updatedAt", LocalDateTime.now());
            }}
        );
    }

    @Override
    public void updateStatus(UUID orderId, String orderStatus) {
        int update = jdbcTemplate.update(
            "update orders set order_status = :orderStatus"
                + " where order_id = UUID_TO_BIN(:orderId)",
            new HashMap<String, Object>() {{
                put("orderId", orderId.toString().getBytes());
                put("orderStatus", orderStatus);
            }});
        if (update == 0) {
            throw new RuntimeException("수정이 되지 않았습니다.");
        }
    }

    @Override
    public List<Order> findAll() {
        return jdbcTemplate.query("select * from orders", orderRowMapper);
    }

    @Override
    public Optional<Order> findById(UUID orderId) {
        try {
            Optional<Order> order = Optional.ofNullable(
                jdbcTemplate.queryForObject(
                    "select * from orders where order_id = UUID_TO_BIN(:orderId)"
                    , Collections.singletonMap("orderId", orderId.toString().getBytes()),
                    orderRowMapper));
            return order;
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public void deleteById(UUID orderId) {
        jdbcTemplate.update("delete from orders where order_id = UUID_TO_BIN(:orderId)",
            Collections.singletonMap("orderId", orderId.toString().getBytes()));
    }

    private static final RowMapper<Order> orderRowMapper = (resultSet, i) -> {
        UUID orderId = toUUID(resultSet.getBytes("order_id"));
        Email emil = new Email(resultSet.getString("email"));
        String address = resultSet.getString("address");
        String postcode = resultSet.getString("postcode");
        OrderStatus orderStatus = OrderStatus.valueOf(resultSet.getString("order_status"));
        LocalDateTime createdAt = toLocalDateTime(resultSet.getTimestamp("created_at"));
        LocalDateTime updatedAt = toLocalDateTime(resultSet.getTimestamp("updated_at"));
        return new Order(
            orderId, emil, address, postcode, Collections.emptyList(), orderStatus, createdAt,
            updatedAt
        );
    };

    private Map<String, Object> toOrderParamMap(Order order) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("orderId", order.getOrderId().toString().getBytes());
        paramMap.put("email", order.getEmail().getAddress());
        paramMap.put("address", order.getAddress());
        paramMap.put("postcode", order.getPostcode());
        paramMap.put("orderStatus", order.getOrderStatus().toString());
        paramMap.put("createdAt", order.getCreatedAt());
        paramMap.put("updatedAt", order.getUpdatedAt());
        return paramMap;
    }

}
