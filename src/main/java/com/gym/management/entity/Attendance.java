package com.gym.management.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Attendance entity for logging check-in and check-out events of members.
 */
@Entity
@Table(name = "attendances")
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private LocalDate date;

    @Column(name = "check_in_time", nullable = false)
    private LocalDateTime checkInTime;

    @Column(name = "check_out_time")
    private LocalDateTime checkOutTime;

    @Column(nullable = false, length = 20)
    private String status = "CHECKED_IN"; // "CHECKED_IN", "COMPLETED"

    @PrePersist
    protected void onCreate() {
        if (this.date == null) {
            this.date = LocalDate.now();
        }
        if (this.checkInTime == null) {
            this.checkInTime = LocalDateTime.now();
        }
        if (this.status == null) {
            this.status = "CHECKED_IN";
        }
    }

    public Attendance() {
    }

    public Attendance(User user, LocalDate date, LocalDateTime checkInTime, LocalDateTime checkOutTime, String status) {
        this.user = user;
        this.date = date != null ? date : LocalDate.now();
        this.checkInTime = checkInTime != null ? checkInTime : LocalDateTime.now();
        this.checkOutTime = checkOutTime;
        this.status = status != null ? status : "CHECKED_IN";
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalDateTime getCheckInTime() {
        return checkInTime;
    }

    public void setCheckInTime(LocalDateTime checkInTime) {
        this.checkInTime = checkInTime;
    }

    public LocalDateTime getCheckOutTime() {
        return checkOutTime;
    }

    public void setCheckOutTime(LocalDateTime checkOutTime) {
        this.checkOutTime = checkOutTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
