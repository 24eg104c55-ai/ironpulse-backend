package com.gym.management.service;

import com.gym.management.dto.RegisterRequest;
import com.gym.management.dto.UserResponse;
import com.gym.management.entity.User;
import com.gym.management.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service managing member records (CRUD operations).
 */
@Service
public class UserService {

    private final UserRepository userRepository;
    private final AuthService authService;

    public UserService(UserRepository userRepository, AuthService authService) {
        this.userRepository = userRepository;
        this.authService = authService;
    }

    /**
     * Retrieve all members (role = 'MEMBER').
     */
    public List<UserResponse> getAllMembers() {
        return userRepository.findByRole("MEMBER").stream()
                .map(authService::mapToResponse)
                .collect(Collectors.toList());
    }

    /**
     * Retrieve any user (admin or member) by ID.
     */
    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + id));
        return authService.mapToResponse(user);
    }

    /**
     * Add a new member manually (used by Admin).
     */
    public UserResponse createMember(RegisterRequest request) {
        request.setRole("MEMBER");
        return authService.register(request);
    }

    /**
     * Update an existing member's information.
     */
    public UserResponse updateMember(Long id, RegisterRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + id));

        String newEmail = request.getEmail().trim().toLowerCase();
        // If email was changed, check that new email isn't already taken
        if (!user.getEmail().equalsIgnoreCase(newEmail) && userRepository.existsByEmail(newEmail)) {
            throw new IllegalArgumentException("Email '" + newEmail + "' is already in use by another user.");
        }

        user.setName(request.getName().trim());
        user.setEmail(newEmail);
        if (request.getPassword() != null && !request.getPassword().trim().isEmpty()) {
            user.setPassword(request.getPassword());
        }
        user.setPhone(request.getPhone() != null ? request.getPhone().trim() : null);

        User updatedUser = userRepository.save(user);
        return authService.mapToResponse(updatedUser);
    }

    /**
     * Delete a member by ID.
     */
    public void deleteMember(Long id) {
        if (!userRepository.existsById(id)) {
            throw new IllegalArgumentException("User not found with ID: " + id);
        }
        userRepository.deleteById(id);
    }
}
