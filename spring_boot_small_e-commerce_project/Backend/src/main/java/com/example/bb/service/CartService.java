package com.example.bb.service;

import com.example.bb.entity.*;
import com.example.bb.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final jakarta.persistence.EntityManager entityManager;

    public Cart getCartByUserId(Long userId) {
        return cartRepository.findByUserId(userId)
                .orElseGet(() -> {
                    User user = userRepository.findById(userId)
                            .orElseThrow(() -> new RuntimeException("User not found"));
                    Cart newCart = Cart.builder()
                            .user(user)
                            .items(new ArrayList<>())
                            .build();
                    return cartRepository.save(newCart);
                });
    }

    @Transactional
    public Cart addToCart(Long userId, Long productId, int quantity) {
        System.out.println("🚀 [CartService] addToCart called - userId: " + userId + ", productId: " + productId + ", quantity: " + quantity);
        Cart cart = getCartByUserId(userId);
        System.out.println("🚀 [CartService] Retrieved Cart ID: " + cart.getId());

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found: " + productId));
        System.out.println("🚀 [CartService] Retrieved Product: " + product.getName() + " (Price: " + product.getPrice() + ")");

        // 1. Add/update the main product (with isFree = false)
        addItemToCartInternal(cart, product, quantity, false);
        System.out.println("🚀 [CartService] Saved main product to cart items database");

        // 2. Add/update the free gift if applicable
        String giftName = getFreeGiftNameForProduct(product);
        if (giftName != null) {
            System.out.println("🚀 [CartService] Product has free gift: " + giftName);
            productRepository.findByNameIgnoreCase(giftName).ifPresent(giftProduct -> {
                addItemToCartInternal(cart, giftProduct, quantity, true);
                System.out.println("🚀 [CartService] Saved free gift to cart items database");
            });
        }

        entityManager.flush();
        entityManager.clear();
        
        Cart result = cartRepository.findById(cart.getId()).orElseThrow();
        System.out.println("🚀 [CartService] Reloaded Cart from DB. Items count: " + result.getItems().size());
        return result;
    }

    private void addItemToCartInternal(Cart cart, Product product, int quantity, boolean isFree) {
        Optional<CartItem> existingItem = cartItemRepository
                .findByCartIdAndProductIdAndIsFree(cart.getId(), product.getId(), isFree);

        if (existingItem.isPresent()) {
            CartItem item = existingItem.get();
            item.setQuantity(item.getQuantity() + quantity);
            cartItemRepository.save(item);
        } else {
            CartItem item = CartItem.builder()
                    .cart(cart)
                    .product(product)
                    .quantity(quantity)
                    .isFree(isFree)
                    .build();
            cartItemRepository.save(item);
        }
    }

    @Transactional
    public Cart updateCartItem(Long userId, Long cartItemId, int quantity) {
        Cart cart = getCartByUserId(userId);
        CartItem item = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));

        if (!item.getCart().getId().equals(cart.getId())) {
            throw new RuntimeException("Item does not belong to your cart");
        }

        Product mainProduct = item.getProduct();
        int oldQuantity = item.getQuantity();
        boolean isFreeItem = item.isFree();

        if (quantity <= 0) {
            cartItemRepository.delete(item);
            if (!isFreeItem) {
                removeFreeGiftForProduct(cart, mainProduct, oldQuantity);
            }
        } else {
            item.setQuantity(quantity);
            cartItemRepository.save(item);
            if (!isFreeItem) {
                updateFreeGiftQuantityForProduct(cart, mainProduct, quantity);
            }
        }

        entityManager.flush();
        entityManager.clear();
        return cartRepository.findById(cart.getId()).orElseThrow();
    }

    @Transactional
    public Cart removeFromCart(Long userId, Long cartItemId) {
        Cart cart = getCartByUserId(userId);
        CartItem item = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));

        if (!item.getCart().getId().equals(cart.getId())) {
            throw new RuntimeException("Item does not belong to your cart");
        }

        Product mainProduct = item.getProduct();
        int quantity = item.getQuantity();
        boolean isFreeItem = item.isFree();

        cart.getItems().remove(item);
        cartItemRepository.delete(item);
        
        if (!isFreeItem) {
            removeFreeGiftForProduct(cart, mainProduct, quantity);
        }

        entityManager.flush();
        entityManager.clear();
        return cartRepository.findById(cart.getId()).orElseThrow();
    }

    @Transactional
    public void clearCart(Long userId) {
        Cart cart = getCartByUserId(userId);
        cartItemRepository.deleteByCartId(cart.getId());
    }

    // Helper methods for Free Gifts
    private String getFreeGiftNameForProduct(Product product) {
        if (product.isHasFreeGift() && product.getFreeGiftDescription() != null) {
            String gift = product.getFreeGiftDescription().trim();
            if (gift.equalsIgnoreCase("anklet") || gift.equalsIgnoreCase("grip") || gift.equalsIgnoreCase("sticker")) {
                if (gift.equalsIgnoreCase("anklet")) return "Anklet";
                if (gift.equalsIgnoreCase("grip")) return "Grip";
                if (gift.equalsIgnoreCase("sticker")) return "Sticker";
            }
        }
        return null;
    }

    private void updateFreeGiftQuantityForProduct(Cart cart, Product mainProduct, int newQuantity) {
        String giftName = getFreeGiftNameForProduct(mainProduct);
        if (giftName != null) {
            productRepository.findByNameIgnoreCase(giftName).ifPresent(giftProduct -> {
                cartItemRepository.findByCartIdAndProductIdAndIsFree(cart.getId(), giftProduct.getId(), true)
                        .ifPresent(giftItem -> {
                            giftItem.setQuantity(newQuantity);
                            cartItemRepository.save(giftItem);
                        });
            });
        }
    }

    private void removeFreeGiftForProduct(Cart cart, Product mainProduct, int quantityToRemove) {
        String giftName = getFreeGiftNameForProduct(mainProduct);
        if (giftName != null) {
            productRepository.findByNameIgnoreCase(giftName).ifPresent(giftProduct -> {
                cartItemRepository.findByCartIdAndProductIdAndIsFree(cart.getId(), giftProduct.getId(), true)
                        .ifPresent(giftItem -> {
                            int newQty = giftItem.getQuantity() - quantityToRemove;
                            if (newQty <= 0) {
                                cart.getItems().remove(giftItem);
                                cartItemRepository.delete(giftItem);
                            } else {
                                giftItem.setQuantity(newQty);
                                cartItemRepository.save(giftItem);
                            }
                        });
            });
        }
    }
}
