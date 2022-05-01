package com.prgrms.cafe.service;

import com.prgrms.cafe.model.Email;
import com.prgrms.cafe.model.Order;
import com.prgrms.cafe.model.OrderItem;
import com.prgrms.cafe.model.OrderStatus;
import com.prgrms.cafe.repository.OrderRepository;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Order createOrder(Email email, String address, String postcode, List<OrderItem> orderItems) {
        return orderRepository.save(
            new Order(
                UUID.randomUUID(), email, address, postcode, orderItems, OrderStatus.ACCEPTED)
        );
    }
}
