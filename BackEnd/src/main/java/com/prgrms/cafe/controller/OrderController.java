package com.prgrms.cafe.controller;

import com.prgrms.cafe.controller.dto.UpdateOrderRequest;
import com.prgrms.cafe.model.OrderStatus;
import com.prgrms.cafe.service.OrderService;
import java.util.UUID;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/orders")
    public String ordersPage(Model model) {
        model.addAttribute("orders", orderService.getOrders());
        return "orders";
    }

    @GetMapping("/orders/{orderId}")
    public String orderPage(@PathVariable UUID orderId, Model model) {
        model.addAttribute("order", orderService.getOrder(orderId));
        model.addAttribute("statuses", OrderStatus.values());
        return "order";
    }

    @GetMapping("/orders/update/{orderId}")
    public String updatePage(@PathVariable UUID orderId, Model model) {
        model.addAttribute("order", orderService.getOrder(orderId));
        return "update-order";
    }

    @PostMapping("/orders/update/{orderId}")
    public String updateOrder(@PathVariable UUID orderId, UpdateOrderRequest updateOrderRequest) {
        orderService.changeAddressAndPostcode(orderId, updateOrderRequest.address(), updateOrderRequest.postcode());
        return "redirect:/orders/" + orderId;
    }

    @PutMapping("/orders/{orderId}/setStatus")
    @ResponseBody
    public String changeStatus(@PathVariable UUID orderId, @RequestBody String orderStatus) {
        orderService.changeStatus(orderId, orderStatus);
        return "success";
    }

}
