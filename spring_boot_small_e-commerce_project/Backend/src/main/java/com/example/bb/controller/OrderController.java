package com.example.bb.controller;

import com.example.bb.entity.Order;
import com.example.bb.service.OrderService;
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
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;

    private Long getUserId(Authentication auth) {
        return userService.getUserByUsername(auth.getName()).getId();
    }

    // Place a new order from cart
    @PostMapping("/orders/place")
    public ResponseEntity<?> placeOrder(Authentication auth,
                                        @RequestParam(required = false) String shippingAddress) {
        try {
            Order order = orderService.placeOrder(getUserId(auth), shippingAddress);
            return ResponseEntity.ok(order);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // Get current user's order history
    @GetMapping("/orders/my")
    public ResponseEntity<List<Order>> getMyOrders(Authentication auth) {
        return ResponseEntity.ok(orderService.getOrdersByUser(getUserId(auth)));
    }

    // Get order by id (user can only see own)
    @GetMapping("/orders/{id}")
    public ResponseEntity<?> getOrderById(Authentication auth, @PathVariable Long id) {
        try {
            Order order = orderService.getOrderById(id);
            // Users can only see their own orders
            if (!order.getUser().getUsername().equals(auth.getName())
                    && auth.getAuthorities().stream()
                        .noneMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
                return ResponseEntity.status(403).body(Map.of("error", "Access denied"));
            }
            return ResponseEntity.ok(order);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // Cancel order (user can cancel own PENDING orders)
    @PutMapping("/orders/{id}/cancel")
    public ResponseEntity<?> cancelOrder(Authentication auth, @PathVariable Long id) {
        try {
            orderService.cancelOrder(id, getUserId(auth));
            return ResponseEntity.ok(Map.of("message", "Order cancelled successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // ADMIN: get all orders
    @GetMapping("/admin/orders")
    public ResponseEntity<List<Order>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    // ADMIN: update order status
    @PutMapping("/admin/orders/{id}/status")
    public ResponseEntity<?> updateOrderStatus(@PathVariable Long id,
                                               @RequestParam String status) {
        try {
            Order order = orderService.updateOrderStatus(id, status);
            return ResponseEntity.ok(order);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}
