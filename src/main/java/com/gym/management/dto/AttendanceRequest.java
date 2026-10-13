package com.gym.management.dto;

import jakarta.validation.constraints.NotNull;

public class AttendanceRequest {

    @NotNull(message = "User ID is required")
    private Long userId;

    public AttendanceRequest() {
    }

    public AttendanceRequest(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
