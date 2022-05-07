package com.prgrms.cafe.repository;

import com.prgrms.cafe.model.OrderItem;
import java.util.List;
import java.util.UUID;

public interface OrderItemRepository {

    OrderItem save(OrderItem orderItem);

    List<OrderItem> findById(UUID orderId);

}
