package com.gym.management.repository;

import com.gym.management.entity.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository for Plan entity.
 */
@Repository
public interface PlanRepository extends JpaRepository<Plan, Long> {

    List<Plan> findByActiveTrue();
}
