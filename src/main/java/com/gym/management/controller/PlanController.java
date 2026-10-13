package com.gym.management.controller;

import com.gym.management.dto.PlanDto;
import com.gym.management.service.PlanService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Controller managing membership plans (Admin & Member viewing).
 */
@RestController
@RequestMapping("/api/plans")
public class PlanController {

    private final PlanService planService;

    public PlanController(PlanService planService) {
        this.planService = planService;
    }

    /**
     * GET /api/plans
     * List all membership plans.
     */
    @GetMapping
    public ResponseEntity<List<PlanDto>> getAllPlans() {
        return ResponseEntity.ok(planService.getAllPlans());
    }

    /**
     * GET /api/plans/active
     * List only currently active plans.
     */
    @GetMapping("/active")
    public ResponseEntity<List<PlanDto>> getActivePlans() {
        return ResponseEntity.ok(planService.getActivePlans());
    }

    /**
     * GET /api/plans/{id}
     * Get plan by ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PlanDto> getPlanById(@PathVariable Long id) {
        return ResponseEntity.ok(planService.getPlanById(id));
    }

    /**
     * POST /api/plans
     * Create a new plan.
     */
    @PostMapping
    public ResponseEntity<PlanDto> createPlan(@Valid @RequestBody PlanDto planDto) {
        PlanDto created = planService.createPlan(planDto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    /**
     * PUT /api/plans/{id}
     * Update an existing plan.
     */
    @PutMapping("/{id}")
    public ResponseEntity<PlanDto> updatePlan(@PathVariable Long id, @Valid @RequestBody PlanDto planDto) {
        PlanDto updated = planService.updatePlan(id, planDto);
        return ResponseEntity.ok(updated);
    }

    /**
     * DELETE /api/plans/{id}
     * Delete a plan by ID.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deletePlan(@PathVariable Long id) {
        planService.deletePlan(id);
        return ResponseEntity.ok(Map.of("message", "Plan successfully deleted"));
    }
}
