package com.gym.management.repository;

import com.gym.management.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for Member attendance tracking.
 */
@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    List<Attendance> findByUserIdOrderByDateDescCheckInTimeDesc(Long userId);

    List<Attendance> findByDateOrderByCheckInTimeDesc(LocalDate date);

    Optional<Attendance> findByUserIdAndDateAndStatus(Long userId, LocalDate date, String status);

    List<Attendance> findAllByOrderByDateDescCheckInTimeDesc();
}
