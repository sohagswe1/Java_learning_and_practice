package com.example.bb.controller;

import com.example.bb.entity.User;
import com.example.bb.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    @org.springframework.beans.factory.annotation.Autowired
    private com.example.bb.repository.CartItemRepository cartItemRepository;
    @org.springframework.beans.factory.annotation.Autowired
    private com.example.bb.repository.CartRepository cartRepository;
    @org.springframework.beans.factory.annotation.Autowired
    private com.example.bb.repository.ProductRepository productRepository;

    @GetMapping("/test/db")
    public ResponseEntity<?> testDb() {
        try {
            return ResponseEntity.ok(Map.of(
                "cartItemsCount", cartItemRepository.count(),
                "cartsCount", cartRepository.count(),
                "productsCount", productRepository.count(),
                "cartItems", cartItemRepository.findAll().stream().map(ci -> Map.of(
                    "id", ci.getId(),
                    "cartId", ci.getCart().getId(),
                    "productId", ci.getProduct().getId(),
                    "productName", ci.getProduct().getName(),
                    "quantity", ci.getQuantity(),
                    "isFree", ci.isFree()
                )).toList()
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        try {
            Map<String, Object> result = authService.register(user);
            return ResponseEntity.ok(result);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        try {
            String username = body.get("username");
            String password = body.get("password");
            Map<String, Object> result = authService.login(username, password);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Invalid username or password"));
        }
    }
}
