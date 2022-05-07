package com.prgrms.cafe.model;

import java.util.UUID;

public record OrderItem (UUID orderId, UUID productId, Category category, long price, int quantity){
}
