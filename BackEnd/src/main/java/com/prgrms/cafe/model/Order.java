package com.prgrms.cafe.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class Order extends BaseTime {

    private final UUID orderId;
    private final Email email;
    private String address;
    private String postcode;
    private List<OrderItem> orderItems;
    private OrderStatus orderStatus;

    public Order(UUID orderId, Email email, String address, String postcode, List<OrderItem> orderItems,
        OrderStatus orderStatus, LocalDateTime createdAt, LocalDateTime updatedAt) {
        super(createdAt, updatedAt);
        this.orderId = orderId;
        this.email = email;
        this.address = address;
        this.postcode = postcode;
        this.orderItems = orderItems;
        this.orderStatus = orderStatus;
    }

    public Order(UUID orderId, Email email, String address, String postcode,
        List<OrderItem> orderItems, OrderStatus orderStatus) {
        super(LocalDateTime.now(), LocalDateTime.now());
        this.orderId = orderId;
        this.email = email;
        this.address = address;
        this.postcode = postcode;
        this.orderItems = orderItems;
        this.orderStatus = orderStatus;
    }

    public void setAddress(String address) {
        this.address = address;
        changeUpdateTime();
    }

    public void postcode(String postcode) {
        this.postcode = postcode;
        changeUpdateTime();
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
        changeUpdateTime();
    }

    public UUID getOrderId() {
        return orderId;
    }

    public Email getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public String getPostcode() {
        return postcode;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

}
