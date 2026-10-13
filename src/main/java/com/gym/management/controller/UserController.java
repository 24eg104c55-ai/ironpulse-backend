package com.gym.management.controller;

import com.gym.management.dto.RegisterRequest;
import com.gym.management.dto.UserResponse;
import com.gym.management.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Controller managing gym members (Admin operations: CRUD).
 */
@RestController
@RequestMapping("/api/members")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * GET /api/members
     * Fetch list of all registered gym members.
     */
    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllMembers() {
        return ResponseEntity.ok(userService.getAllMembers());
    }

    /**
     * GET /api/members/{id}
     * Fetch details of a specific member by ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getMemberById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    /**
     * POST /api/members
     * Add a new member manually from admin panel.
     */
    @PostMapping
    public ResponseEntity<UserResponse> createMember(@Valid @RequestBody RegisterRequest request) {
        UserResponse response = userService.createMember(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * PUT /api/members/{id}
     * Update an existing member's information.
     */
    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateMember(@PathVariable Long id, @Valid @RequestBody RegisterRequest request) {
        UserResponse response = userService.updateMember(id, request);
        return ResponseEntity.ok(response);
    }

    /**
     * DELETE /api/members/{id}
     * Remove a member from the database.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteMember(@PathVariable Long id) {
        userService.deleteMember(id);
        return ResponseEntity.ok(Map.of("message", "Member successfully deleted"));
    }
}
