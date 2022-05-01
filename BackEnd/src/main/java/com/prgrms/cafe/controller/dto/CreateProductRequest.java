package com.prgrms.cafe.controller.dto;

import com.prgrms.cafe.model.Category;

public record CreateProductRequest(String productName, Category category, long price, String description) {
}
