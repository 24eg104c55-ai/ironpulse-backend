package com.gym.management.dto;

import java.math.BigDecimal;

public class DashboardStats {

    private long totalMembers;
    private long activeMemberships;
    private long expiredMemberships;
    private BigDecimal totalRevenue;
    private long todayCheckIns;

    public DashboardStats() {
    }

    public DashboardStats(long totalMembers, long activeMemberships, long expiredMemberships, BigDecimal totalRevenue, long todayCheckIns) {
        this.totalMembers = totalMembers;
        this.activeMemberships = activeMemberships;
        this.expiredMemberships = expiredMemberships;
        this.totalRevenue = totalRevenue != null ? totalRevenue : BigDecimal.ZERO;
        this.todayCheckIns = todayCheckIns;
    }

    public long getTotalMembers() {
        return totalMembers;
    }

    public void setTotalMembers(long totalMembers) {
        this.totalMembers = totalMembers;
    }

    public long getActiveMemberships() {
        return activeMemberships;
    }

    public void setActiveMemberships(long activeMemberships) {
        this.activeMemberships = activeMemberships;
    }

    public long getExpiredMemberships() {
        return expiredMemberships;
    }

    public void setExpiredMemberships(long expiredMemberships) {
        this.expiredMemberships = expiredMemberships;
    }

    public BigDecimal getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(BigDecimal totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public long getTodayCheckIns() {
        return todayCheckIns;
    }

    public void setTodayCheckIns(long todayCheckIns) {
        this.todayCheckIns = todayCheckIns;
    }
}
