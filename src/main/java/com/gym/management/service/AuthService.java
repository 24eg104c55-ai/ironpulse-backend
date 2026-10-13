package com.gym.management.service;

import com.gym.management.dto.LoginRequest;
import com.gym.management.dto.RegisterRequest;
import com.gym.management.dto.UserResponse;
import com.gym.management.entity.Membership;
import com.gym.management.entity.User;
import com.gym.management.repository.MembershipRepository;
import com.gym.management.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service for handling registration and login authentication.
 */
@Service
public class AuthService {

    private final UserRepository userRepository;
    private final MembershipRepository membershipRepository;

    public AuthService(UserRepository userRepository, MembershipRepository membershipRepository) {
        this.userRepository = userRepository;
        this.membershipRepository = membershipRepository;
    }

    /**
     * Registers a new user with email uniqueness validation.
     */
    public UserResponse register(RegisterRequest request) {
        String cleanEmail = request.getEmail().trim().toLowerCase();

        // Check for duplicate email
        if (userRepository.existsByEmail(cleanEmail)) {
            throw new IllegalArgumentException("An account with email '" + cleanEmail + "' already exists.");
        }

        // Validate and normalize role
        String role = "MEMBER";
        if (request.getRole() != null && request.getRole().equalsIgnoreCase("ADMIN")) {
            role = "ADMIN";
        }

        User user = new User();
        user.setName(request.getName().trim());
        user.setEmail(cleanEmail);
        user.setPassword(request.getPassword()); // Stored cleanly for beginner project
        user.setPhone(request.getPhone() != null ? request.getPhone().trim() : null);
        user.setRole(role);

        User savedUser = userRepository.save(user);
        return mapToResponse(savedUser);
    }

    /**
     * Authenticates user credentials.
     */
    public UserResponse login(LoginRequest request) {
        String cleanEmail = request.getEmail().trim().toLowerCase();

        User user = userRepository.findByEmail(cleanEmail)
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));

        // Match password
        if (!user.getPassword().equals(request.getPassword())) {
            throw new IllegalArgumentException("Invalid email or password");
        }

        return mapToResponse(user);
    }

    /**
     * Helper to map User entity to UserResponse DTO, populating membership data if present.
     */
    public UserResponse mapToResponse(User user) {
        UserResponse response = new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPhone(),
                user.getRole(),
                user.getCreatedAt()
        );

        // Find latest membership status for members
        Optional<Membership> latestMembership = membershipRepository.findTopByUserIdOrderByEndDateDesc(user.getId());
        if (latestMembership.isPresent()) {
            Membership m = latestMembership.get();
            response.setActiveMembershipId(m.getId());
            response.setCurrentPlanName(m.getPlan().getName());
            response.setMembershipStartDate(m.getStartDate());
            response.setMembershipEndDate(m.getEndDate());
            response.setMembershipStatus(m.getStatus());
        } else {
            response.setMembershipStatus("NO_PLAN");
        }

        return response;
    }
}
