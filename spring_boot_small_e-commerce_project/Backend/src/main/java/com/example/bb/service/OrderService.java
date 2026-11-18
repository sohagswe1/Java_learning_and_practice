package com.example.bb.service;

import com.example.bb.entity.*;
import com.example.bb.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private static final double FREE_DELIVERY_THRESHOLD = 2000.0;
    private static final double DELIVERY_CHARGE = 100.0;

    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Transactional
    public Order placeOrder(Long userId, String shippingAddress) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart is empty"));

        if (cart.getItems() == null || cart.getItems().isEmpty()) {
            throw new RuntimeException("Cart is empty. Add products before placing order.");
        }

        // Build order
        Order order = Order.builder()
                .user(user)
                .status("PENDING")
                .shippingAddress(shippingAddress != null ? shippingAddress : user.getAddress())
                .items(new ArrayList<>())
                .build();

        Order savedOrder = orderRepository.save(order);

        // Build order items from cart
        double total = 0.0;
        List<OrderItem> orderItems = new ArrayList<>();

        for (CartItem cartItem : cart.getItems()) {
            Product product = cartItem.getProduct();

            // Decrease stock
            if (product.getStock() < cartItem.getQuantity()) {
                throw new RuntimeException("Insufficient stock for: " + product.getName());
            }
            product.setStock(product.getStock() - cartItem.getQuantity());
            productRepository.save(product);

            double price = cartItem.isFree() ? 0.0 : product.getPrice();

            OrderItem orderItem = OrderItem.builder()
                    .order(savedOrder)
                    .product(product)
                    .quantity(cartItem.getQuantity())
                    .unitPrice(price)
                    .hasFreeGift(product.isHasFreeGift())
                    .freeGiftDescription(product.getFreeGiftDescription())
                    .build();

            orderItems.add(orderItem);
            total += price * cartItem.getQuantity();
        }

        // User star rating discount: 2% per star
        int stars = user.getStars() != null ? user.getStars() : 0;
        double discountPercentage = stars * 0.02;
        double discountAmount = total * discountPercentage;
        double discountedSubtotal = total - discountAmount;

        // Apply delivery charge rule: free if total after discount >= 2000 BDT
        double deliveryCharge = (discountedSubtotal >= FREE_DELIVERY_THRESHOLD) ? 0.0 : DELIVERY_CHARGE;
        double finalAmount = discountedSubtotal + deliveryCharge;

        savedOrder.setItems(orderItems);
        savedOrder.setTotalAmount(total);
        savedOrder.setDiscountAmount(discountAmount);
        savedOrder.setDeliveryCharge(deliveryCharge);
        savedOrder.setFinalAmount(finalAmount);

        orderRepository.save(savedOrder);

        // Clear cart after ordering
        cartItemRepository.deleteByCartId(cart.getId());

        return savedOrder;
    }

    public List<Order> getOrdersByUser(Long userId) {
        return orderRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }

    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAllByOrderByCreatedAtDesc();
    }

    public Order updateOrderStatus(Long orderId, String status) {
        Order order = getOrderById(orderId);
        order.setStatus(status);
        return orderRepository.save(order);
    }

    public void cancelOrder(Long orderId, Long userId) {
        Order order = getOrderById(orderId);
        if (!order.getUser().getId().equals(userId)) {
            throw new RuntimeException("You can only cancel your own orders");
        }
        if (!order.getStatus().equals("PENDING")) {
            throw new RuntimeException("Only PENDING orders can be cancelled");
        }

        // Restore stock
        for (OrderItem item : order.getItems()) {
            Product product = item.getProduct();
            product.setStock(product.getStock() + item.getQuantity());
            productRepository.save(product);
        }

        order.setStatus("CANCELLED");
        orderRepository.save(order);
    }
}
