package com.gym.management.service;

import com.gym.management.dto.PaymentRequest;
import com.gym.management.entity.Membership;
import com.gym.management.entity.Payment;
import com.gym.management.entity.User;
import com.gym.management.repository.MembershipRepository;
import com.gym.management.repository.PaymentRepository;
import com.gym.management.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Service managing payment logging and revenue tracking.
 */
@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final UserRepository userRepository;
    private final MembershipRepository membershipRepository;

    public PaymentService(PaymentRepository paymentRepository,
                          UserRepository userRepository,
                          MembershipRepository membershipRepository) {
        this.paymentRepository = paymentRepository;
        this.userRepository = userRepository;
        this.membershipRepository = membershipRepository;
    }

    public Payment recordPayment(PaymentRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + request.getUserId()));

        Membership membership = null;
        if (request.getMembershipId() != null) {
            membership = membershipRepository.findById(request.getMembershipId()).orElse(null);
        }

        Payment payment = new Payment();
        payment.setUser(user);
        payment.setMembership(membership);
        payment.setAmount(request.getAmount());
        payment.setPaymentMethod(request.getPaymentMethod().toUpperCase());
        payment.setPaymentDate(LocalDateTime.now());
        payment.setStatus(request.getStatus() != null ? request.getStatus().toUpperCase() : "PAID");

        return paymentRepository.save(payment);
    }

    public List<Payment> getAllPayments() {
        return paymentRepository.findAllByOrderByPaymentDateDesc();
    }

    public List<Payment> getPaymentsByUserId(Long userId) {
        return paymentRepository.findByUserIdOrderByPaymentDateDesc(userId);
    }
}
