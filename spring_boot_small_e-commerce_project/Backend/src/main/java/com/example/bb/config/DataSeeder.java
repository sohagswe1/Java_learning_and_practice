package com.example.bb.config;

import com.example.bb.entity.User;
import com.example.bb.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final com.example.bb.repository.CartItemRepository cartItemRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        seedAdmin();
        clearCartItems();
    }

    private void clearCartItems() {
        try {
            cartItemRepository.deleteAll();
            System.out.println("🧹 Successfully cleared old cart items!");
        } catch (Exception e) {
            System.err.println("Failed to clear cart items: " + e.getMessage());
        }
    }

    private void seedAdmin() {
        if (!userRepository.existsByUsername("admin")) {
            User admin = User.builder()
                    .username("admin")
                    .email("admin@sports.com")
                    .password(passwordEncoder.encode("admin123"))
                    .role("ADMIN")
                    .phone("01700000000")
                    .address("Dhaka, Bangladesh")
                    .build();
            userRepository.save(admin);
            System.out.println("✅ Admin user created: admin / admin123");
        }
    }
}
