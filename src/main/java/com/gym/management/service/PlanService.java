package com.gym.management.service;

import com.gym.management.dto.PlanDto;
import com.gym.management.entity.Plan;
import com.gym.management.repository.PlanRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service managing membership plan offerings.
 */
@Service
public class PlanService {

    private final PlanRepository planRepository;

    public PlanService(PlanRepository planRepository) {
        this.planRepository = planRepository;
    }

    public List<PlanDto> getAllPlans() {
        return planRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public List<PlanDto> getActivePlans() {
        return planRepository.findByActiveTrue().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public PlanDto getPlanById(Long id) {
        Plan plan = planRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Plan not found with ID: " + id));
        return mapToDto(plan);
    }

    public PlanDto createPlan(PlanDto dto) {
        Plan plan = new Plan();
        plan.setName(dto.getName().trim());
        plan.setPrice(dto.getPrice());
        plan.setDurationDays(dto.getDurationDays());
        plan.setDescription(dto.getDescription());
        plan.setActive(dto.getActive() != null ? dto.getActive() : true);

        Plan saved = planRepository.save(plan);
        return mapToDto(saved);
    }

    public PlanDto updatePlan(Long id, PlanDto dto) {
        Plan plan = planRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Plan not found with ID: " + id));

        plan.setName(dto.getName().trim());
        plan.setPrice(dto.getPrice());
        plan.setDurationDays(dto.getDurationDays());
        plan.setDescription(dto.getDescription());
        if (dto.getActive() != null) {
            plan.setActive(dto.getActive());
        }

        Plan saved = planRepository.save(plan);
        return mapToDto(saved);
    }

    public void deletePlan(Long id) {
        if (!planRepository.existsById(id)) {
            throw new IllegalArgumentException("Plan not found with ID: " + id);
        }
        planRepository.deleteById(id);
    }

    public PlanDto mapToDto(Plan plan) {
        return new PlanDto(
                plan.getId(),
                plan.getName(),
                plan.getPrice(),
                plan.getDurationDays(),
                plan.getDescription(),
                plan.getActive()
        );
    }
}
