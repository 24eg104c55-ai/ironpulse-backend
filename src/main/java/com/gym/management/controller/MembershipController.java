package com.gym.management.controller;

import com.gym.management.dto.AssignPlanRequest;
import com.gym.management.entity.Membership;
import com.gym.management.service.MembershipService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller managing member subscription assignments.
 */
@RestController
@RequestMapping("/api/memberships")
public class MembershipController {

    private final MembershipService membershipService;

    public MembershipController(MembershipService membershipService) {
        this.membershipService = membershipService;
    }

    /**
     * POST /api/memberships/assign
     * Assigns a plan to a member and records initial payment if chosen.
     */
    @PostMapping("/assign")
    public ResponseEntity<Membership> assignPlan(@Valid @RequestBody AssignPlanRequest request) {
        Membership membership = membershipService.assignPlan(request);
        return new ResponseEntity<>(membership, HttpStatus.CREATED);
    }

    /**
     * GET /api/memberships
     * Get all memberships.
     */
    @GetMapping
    public ResponseEntity<List<Membership>> getAllMemberships() {
        return ResponseEntity.ok(membershipService.getAllMemberships());
    }

    /**
     * GET /api/memberships/user/{userId}
     * Get membership history for a specific member.
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Membership>> getMembershipsByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(membershipService.getMembershipsByUserId(userId));
    }

    /**
     * GET /api/memberships/user/{userId}/latest
     * Get most recent/active membership for a user.
     */
    @GetMapping("/user/{userId}/latest")
    public ResponseEntity<Membership> getLatestMembershipByUserId(@PathVariable Long userId) {
        return membershipService.getLatestMembershipByUserId(userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
    }
}
