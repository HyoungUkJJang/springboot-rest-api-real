package com.prgrms.cafe.repository;

import com.prgrms.cafe.model.Order;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderRepository {

    Order save(Order order);

    void update(UUID orderId, String address, String postcde);

    void updateStatus(UUID orderId, String orderStatus);

    List<Order> findAll();

    Optional<Order> findById(UUID orderId);

}
