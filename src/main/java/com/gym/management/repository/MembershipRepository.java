package com.gym.management.repository;

import com.gym.management.entity.Membership;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for Membership entity.
 */
@Repository
public interface MembershipRepository extends JpaRepository<Membership, Long> {

    List<Membership> findByUserId(Long userId);

    Optional<Membership> findTopByUserIdOrderByEndDateDesc(Long userId);

    long countByStatus(String status);
}
