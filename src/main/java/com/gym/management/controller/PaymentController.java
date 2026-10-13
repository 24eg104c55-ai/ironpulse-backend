package com.gym.management.controller;

import com.gym.management.dto.PaymentRequest;
import com.gym.management.entity.Payment;
import com.gym.management.service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller handling payments recording and history tracking.
 */
@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    /**
     * POST /api/payments
     * Record a new payment manually.
     */
    @PostMapping
    public ResponseEntity<Payment> recordPayment(@Valid @RequestBody PaymentRequest request) {
        Payment payment = paymentService.recordPayment(request);
        return new ResponseEntity<>(payment, HttpStatus.CREATED);
    }

    /**
     * GET /api/payments
     * Get all payment history logs (Admin).
     */
    @GetMapping
    public ResponseEntity<List<Payment>> getAllPayments() {
        return ResponseEntity.ok(paymentService.getAllPayments());
    }

    /**
     * GET /api/payments/user/{userId}
     * Get payment history for a specific member.
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Payment>> getPaymentsByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(paymentService.getPaymentsByUserId(userId));
    }
}
