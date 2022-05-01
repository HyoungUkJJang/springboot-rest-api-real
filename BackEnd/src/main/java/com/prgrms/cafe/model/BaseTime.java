package com.prgrms.cafe.model;

import java.time.LocalDateTime;

public class BaseTime {

    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public BaseTime(LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void changeUpdateTime() {
        this.updatedAt = LocalDateTime.now();
    }

}
