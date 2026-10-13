package com.gym.management.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public class AssignPlanRequest {

    @NotNull(message = "User ID is required")
    private Long userId;

    @NotNull(message = "Plan ID is required")
    private Long planId;

    private LocalDate startDate; // Defaults to LocalDate.now() if null

    private boolean recordPayment = true;

    private String paymentMethod = "CASH"; // CASH, CARD, UPI, BANK_TRANSFER

    public AssignPlanRequest() {
    }

    public AssignPlanRequest(Long userId, Long planId, LocalDate startDate, boolean recordPayment, String paymentMethod) {
        this.userId = userId;
        this.planId = planId;
        this.startDate = startDate;
        this.recordPayment = recordPayment;
        this.paymentMethod = paymentMethod;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getPlanId() {
        return planId;
    }

    public void setPlanId(Long planId) {
        this.planId = planId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public boolean isRecordPayment() {
        return recordPayment;
    }

    public void setRecordPayment(boolean recordPayment) {
        this.recordPayment = recordPayment;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}
