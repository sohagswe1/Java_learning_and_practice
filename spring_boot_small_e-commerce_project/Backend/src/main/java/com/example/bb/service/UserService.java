package com.example.bb.service;

import com.example.bb.entity.User;
import com.example.bb.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));
    }

    public User updateUser(Long id, User updated) {
        User existing = getUserById(id);
        existing.setPhone(updated.getPhone());
        existing.setAddress(updated.getAddress());
        existing.setEmail(updated.getEmail());
        if (updated.getPassword() != null && !updated.getPassword().isEmpty()) {
            existing.setPassword(passwordEncoder.encode(updated.getPassword()));
        }
        return userRepository.save(existing);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public User updateUserStars(Long id, Integer stars) {
        User existing = getUserById(id);
        existing.setStars(stars != null ? stars : 0);
        return userRepository.save(existing);
    }
}
