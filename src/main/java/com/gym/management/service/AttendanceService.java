package com.gym.management.service;

import com.gym.management.entity.Attendance;
import com.gym.management.entity.User;
import com.gym.management.repository.AttendanceRepository;
import com.gym.management.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Service managing member gym check-ins and check-outs.
 */
@Service
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final UserRepository userRepository;

    public AttendanceService(AttendanceRepository attendanceRepository, UserRepository userRepository) {
        this.attendanceRepository = attendanceRepository;
        this.userRepository = userRepository;
    }

    /**
     * Checks in a member for today.
     */
    public Attendance checkIn(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Member not found with ID: " + userId));

        LocalDate today = LocalDate.now();

        // Prevent multiple active check-ins on the same day
        Optional<Attendance> existingActive = attendanceRepository.findByUserIdAndDateAndStatus(userId, today, "CHECKED_IN");
        if (existingActive.isPresent()) {
            throw new IllegalArgumentException(user.getName() + " is already checked in today! Please check out first.");
        }

        Attendance attendance = new Attendance();
        attendance.setUser(user);
        attendance.setDate(today);
        attendance.setCheckInTime(LocalDateTime.now());
        attendance.setStatus("CHECKED_IN");

        return attendanceRepository.save(attendance);
    }

    /**
     * Checks out a member for today.
     */
    public Attendance checkOut(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Member not found with ID: " + userId));

        LocalDate today = LocalDate.now();

        Attendance attendance = attendanceRepository.findByUserIdAndDateAndStatus(userId, today, "CHECKED_IN")
                .orElseThrow(() -> new IllegalArgumentException("No active check-in found for this member today."));

        attendance.setCheckOutTime(LocalDateTime.now());
        attendance.setStatus("COMPLETED");

        return attendanceRepository.save(attendance);
    }

    public List<Attendance> getTodayAttendance() {
        return attendanceRepository.findByDateOrderByCheckInTimeDesc(LocalDate.now());
    }

    public List<Attendance> getUserAttendance(Long userId) {
        return attendanceRepository.findByUserIdOrderByDateDescCheckInTimeDesc(userId);
    }

    public List<Attendance> getAllAttendance() {
        return attendanceRepository.findAllByOrderByDateDescCheckInTimeDesc();
    }
}
