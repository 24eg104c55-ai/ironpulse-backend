package com.gym.management.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

/**
 * Membership entity linking a Member (User) to a chosen Plan,
 * recording start date, calculated end date, and status.
 */
@Entity
@Table(name = "memberships")
public class Membership {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "plan_id", nullable = false)
    private Plan plan;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(nullable = false, length = 20)
    private String status = "ACTIVE"; // "ACTIVE" or "EXPIRED"

    public Membership() {
    }

    public Membership(User user, Plan plan, LocalDate startDate, LocalDate endDate, String status) {
        this.user = user;
        this.plan = plan;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status != null ? status : "ACTIVE";
    }

    // Helper to check if currently expired based on today's date
    public boolean isExpired() {
        return endDate != null && endDate.isBefore(LocalDate.now());
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

    public Plan getPlan() {
        return plan;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        // Dynamically reflect expired status if past end date
        if (isExpired() && !"EXPIRED".equalsIgnoreCase(this.status)) {
            return "EXPIRED";
        }
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
