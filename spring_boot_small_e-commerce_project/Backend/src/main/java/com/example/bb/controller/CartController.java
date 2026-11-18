package com.example.bb.controller;

import com.example.bb.entity.Cart;
import com.example.bb.service.CartService;
import com.example.bb.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    private final UserService userService;

    private Long getUserId(Authentication auth) {
        return userService.getUserByUsername(auth.getName()).getId();
    }

    // Get current user's cart
    @GetMapping
    public ResponseEntity<?> getCart(Authentication auth) {
        try {
            Cart cart = cartService.getCartByUserId(getUserId(auth));
            return ResponseEntity.ok(cart);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // Add item to cart
    @PostMapping("/add")
    public ResponseEntity<?> addToCart(Authentication auth,
                                       @RequestParam Long productId,
                                       @RequestParam(defaultValue = "1") int quantity) {
        try {
            Cart cart = cartService.addToCart(getUserId(auth), productId, quantity);
            return ResponseEntity.ok(cart);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // Update cart item quantity
    @PutMapping("/update/{cartItemId}")
    public ResponseEntity<?> updateItem(Authentication auth,
                                        @PathVariable Long cartItemId,
                                        @RequestParam int quantity) {
        try {
            Cart cart = cartService.updateCartItem(getUserId(auth), cartItemId, quantity);
            return ResponseEntity.ok(cart);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // Remove item from cart
    @DeleteMapping("/remove/{cartItemId}")
    public ResponseEntity<?> removeItem(Authentication auth,
                                        @PathVariable Long cartItemId) {
        try {
            Cart cart = cartService.removeFromCart(getUserId(auth), cartItemId);
            return ResponseEntity.ok(cart);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // Clear entire cart
    @DeleteMapping("/clear")
    public ResponseEntity<?> clearCart(Authentication auth) {
        try {
            cartService.clearCart(getUserId(auth));
            return ResponseEntity.ok(Map.of("message", "Cart cleared successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}
