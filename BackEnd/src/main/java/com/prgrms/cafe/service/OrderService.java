package com.prgrms.cafe.service;

import com.prgrms.cafe.model.Email;
import com.prgrms.cafe.model.Order;
import com.prgrms.cafe.model.OrderItem;
import java.util.List;
import java.util.UUID;

public interface OrderService {

    Order createOrder(Email email, String address, String postcode, List<OrderItem> orderItems);

    List<Order> getOrders();

    void changeStatus(UUID orderId, String orderStatus);

    void changeAddressAndPostcode(UUID orderId, String address, String postcode);

    Order getOrder(UUID orderId);

}
