package com.prgrms.cafe.controller.api;

import com.prgrms.cafe.controller.dto.CreateOrderRequest;
import com.prgrms.cafe.model.Email;
import com.prgrms.cafe.model.Order;
import com.prgrms.cafe.service.OrderService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderRestController {

    private final OrderService orderService;

    public OrderRestController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/api/v1/orders")
    public Order createOrder(@RequestBody CreateOrderRequest orderRequest) {
        return orderService.createOrder(
            new Email(orderRequest.email()),
            orderRequest.address(),
            orderRequest.postcode(),
            orderRequest.orderItems()
        );
    }

}
