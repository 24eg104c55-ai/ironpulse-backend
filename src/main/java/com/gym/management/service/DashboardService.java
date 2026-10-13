package com.gym.management.service;

import com.gym.management.dto.DashboardStats;
import com.gym.management.repository.AttendanceRepository;
import com.gym.management.repository.MembershipRepository;
import com.gym.management.repository.PaymentRepository;
import com.gym.management.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Service aggregating high-level metrics for the Admin Dashboard.
 */
@Service
public class DashboardService {

    private final UserRepository userRepository;
    private final MembershipRepository membershipRepository;
    private final PaymentRepository paymentRepository;
    private final AttendanceRepository attendanceRepository;

    public DashboardService(UserRepository userRepository,
                            MembershipRepository membershipRepository,
                            PaymentRepository paymentRepository,
                            AttendanceRepository attendanceRepository) {
        this.userRepository = userRepository;
        this.membershipRepository = membershipRepository;
        this.paymentRepository = paymentRepository;
        this.attendanceRepository = attendanceRepository;
    }

    public DashboardStats getDashboardStats() {
        long totalMembers = userRepository.countByRole("MEMBER");
        long activeMemberships = membershipRepository.countByStatus("ACTIVE");
        long expiredMemberships = membershipRepository.countByStatus("EXPIRED");
        BigDecimal totalRevenue = paymentRepository.calculateTotalPaidRevenue();
        long todayCheckIns = attendanceRepository.findByDateOrderByCheckInTimeDesc(LocalDate.now()).size();

        return new DashboardStats(
                totalMembers,
                activeMemberships,
                expiredMemberships,
                totalRevenue != null ? totalRevenue : BigDecimal.ZERO,
                todayCheckIns
        );
    }
}
