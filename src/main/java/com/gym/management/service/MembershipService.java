package com.gym.management.service;

import com.gym.management.dto.AssignPlanRequest;
import com.gym.management.entity.Membership;
import com.gym.management.entity.Payment;
import com.gym.management.entity.Plan;
import com.gym.management.entity.User;
import com.gym.management.repository.MembershipRepository;
import com.gym.management.repository.PaymentRepository;
import com.gym.management.repository.PlanRepository;
import com.gym.management.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Service managing assignment and lifecycle of member subscriptions.
 */
@Service
public class MembershipService {

    private final MembershipRepository membershipRepository;
    private final UserRepository userRepository;
    private final PlanRepository planRepository;
    private final PaymentRepository paymentRepository;

    public MembershipService(MembershipRepository membershipRepository,
                             UserRepository userRepository,
                             PlanRepository planRepository,
                             PaymentRepository paymentRepository) {
        this.membershipRepository = membershipRepository;
        this.userRepository = userRepository;
        this.planRepository = planRepository;
        this.paymentRepository = paymentRepository;
    }

    /**
     * Assigns a membership plan to a member and optionally records the corresponding payment.
     */
    @Transactional
    public Membership assignPlan(AssignPlanRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + request.getUserId()));

        Plan plan = planRepository.findById(request.getPlanId())
                .orElseThrow(() -> new IllegalArgumentException("Plan not found with ID: " + request.getPlanId()));

        LocalDate startDate = request.getStartDate() != null ? request.getStartDate() : LocalDate.now();
        LocalDate endDate = startDate.plusDays(plan.getDurationDays());

        Membership membership = new Membership();
        membership.setUser(user);
        membership.setPlan(plan);
        membership.setStartDate(startDate);
        membership.setEndDate(endDate);
        membership.setStatus("ACTIVE");

        Membership savedMembership = membershipRepository.save(membership);

        // Optionally record payment automatically upon plan assignment
        if (request.isRecordPayment()) {
            Payment payment = new Payment();
            payment.setUser(user);
            payment.setMembership(savedMembership);
            payment.setAmount(plan.getPrice());
            payment.setPaymentMethod(request.getPaymentMethod() != null ? request.getPaymentMethod() : "CASH");
            payment.setPaymentDate(LocalDateTime.now());
            payment.setStatus("PAID");
            paymentRepository.save(payment);
        }

        return savedMembership;
    }

    public List<Membership> getAllMemberships() {
        return membershipRepository.findAll();
    }

    public List<Membership> getMembershipsByUserId(Long userId) {
        return membershipRepository.findByUserId(userId);
    }

    public Optional<Membership> getLatestMembershipByUserId(Long userId) {
        return membershipRepository.findTopByUserIdOrderByEndDateDesc(userId);
    }
}
