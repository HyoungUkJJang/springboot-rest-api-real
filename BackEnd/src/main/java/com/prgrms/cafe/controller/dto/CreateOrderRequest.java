package com.prgrms.cafe.controller.dto;

import com.prgrms.cafe.model.OrderItem;
import java.util.List;

public record CreateOrderRequest(String email, String address, String postcode, List<OrderItem> orderItems) {
}
