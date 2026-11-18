package com.example.bb.controller;

import com.example.bb.entity.User;
import com.example.bb.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // Get current logged-in user profile
    @GetMapping("/user/profile")
    public ResponseEntity<?> getProfile(Authentication auth) {
        try {
            User user = userService.getUserByUsername(auth.getName());
            user.setPassword(null); // don't send password
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // Update current user profile
    @PutMapping("/user/profile")
    public ResponseEntity<?> updateProfile(Authentication auth, @RequestBody User updated) {
        try {
            User current = userService.getUserByUsername(auth.getName());
            User result = userService.updateUser(current.getId(), updated);
            result.setPassword(null);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // ADMIN: Get all users
    @GetMapping("/admin/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        users.forEach(u -> u.setPassword(null));
        return ResponseEntity.ok(users);
    }

    // ADMIN: Get user by id
    @GetMapping("/admin/users/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        try {
            User user = userService.getUserById(id);
            user.setPassword(null);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // ADMIN: Delete user
    @DeleteMapping("/admin/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.ok(Map.of("message", "User deleted successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // ADMIN: Update user stars
    @PutMapping("/admin/users/{id}/stars")
    public ResponseEntity<?> updateUserStars(@PathVariable Long id, @RequestParam Integer stars) {
        try {
            User user = userService.updateUserStars(id, stars);
            user.setPassword(null);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}
