package com.gym.management.controller;

import com.gym.management.dto.AttendanceRequest;
import com.gym.management.entity.Attendance;
import com.gym.management.service.AttendanceService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller handling member gym check-ins and check-outs.
 */
@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {

    private final AttendanceService attendanceService;

    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    /**
     * POST /api/attendance/check-in
     * Check in a member today.
     */
    @PostMapping("/check-in")
    public ResponseEntity<Attendance> checkIn(@Valid @RequestBody AttendanceRequest request) {
        Attendance attendance = attendanceService.checkIn(request.getUserId());
        return new ResponseEntity<>(attendance, HttpStatus.CREATED);
    }

    /**
     * POST /api/attendance/check-out
     * Check out a member today.
     */
    @PostMapping("/check-out")
    public ResponseEntity<Attendance> checkOut(@Valid @RequestBody AttendanceRequest request) {
        Attendance attendance = attendanceService.checkOut(request.getUserId());
        return ResponseEntity.ok(attendance);
    }

    /**
     * GET /api/attendance/today
     * Get list of all check-ins for the current day.
     */
    @GetMapping("/today")
    public ResponseEntity<List<Attendance>> getTodayAttendance() {
        return ResponseEntity.ok(attendanceService.getTodayAttendance());
    }

    /**
     * GET /api/attendance/user/{userId}
     * Get attendance history for a single member.
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Attendance>> getUserAttendance(@PathVariable Long userId) {
        return ResponseEntity.ok(attendanceService.getUserAttendance(userId));
    }

    /**
     * GET /api/attendance
     * Get all attendance logs.
     */
    @GetMapping
    public ResponseEntity<List<Attendance>> getAllAttendance() {
        return ResponseEntity.ok(attendanceService.getAllAttendance());
    }
}
