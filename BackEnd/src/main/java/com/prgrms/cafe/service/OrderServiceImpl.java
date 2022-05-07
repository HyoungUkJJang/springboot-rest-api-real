package com.prgrms.cafe.service;

import com.prgrms.cafe.model.Email;
import com.prgrms.cafe.model.Order;
import com.prgrms.cafe.model.OrderItem;
import com.prgrms.cafe.model.OrderStatus;
import com.prgrms.cafe.repository.OrderItemRepository;
import com.prgrms.cafe.repository.OrderRepository;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    public OrderServiceImpl(OrderRepository orderRepository,
        OrderItemRepository orderItemRepository) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
    }

    @Override
    @Transactional
    public Order createOrder(Email email, String address, String postcode, List<OrderItem> orderItems) {
        Order order = orderRepository.save(
            new Order(UUID.randomUUID(), email, address, postcode, orderItems, OrderStatus.ACCEPTED)
        );

        orderItems.stream().map(
                item -> new OrderItem(order.getOrderId(), item.productId(), item.category(),
                    item.price(), item.quantity()))
            .forEach(i -> orderItemRepository.save(i));

        return order;
    }

    @Override
    public List<Order> getOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Order getOrder(UUID orderId) {
        List<OrderItem> orderItems = orderItemRepository.findById(orderId);
        Order order = orderRepository.findById(orderId)
            .orElseThrow(NoSuchElementException::new);

        return new Order(order.getOrderId(), order.getEmail(), order.getAddress(), order.getPostcode(),
            orderItems, order.getOrderStatus(), order.getCreatedAt(), order.getUpdatedAt());
    }

    @Override
    @Transactional
    public void changeStatus(UUID orderId, String orderStatus) {
        orderRepository.updateStatus(orderId, orderStatus);
    }

    @Override
    @Transactional
    public void changeAddressAndPostcode(UUID orderId, String address, String postcode) {
        orderRepository.update(orderId, address, postcode);
    }

}
