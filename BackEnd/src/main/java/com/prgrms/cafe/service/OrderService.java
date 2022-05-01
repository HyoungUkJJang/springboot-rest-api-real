package com.prgrms.cafe.service;

import com.prgrms.cafe.model.Email;
import com.prgrms.cafe.model.Order;
import com.prgrms.cafe.model.OrderItem;
import java.util.List;

public interface OrderService {

    Order createOrder(Email email, String address, String postcode, List<OrderItem> orderItems);

}
