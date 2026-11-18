package com.example.bb.repository;

import com.example.bb.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    Optional<CartItem> findByCartIdAndProductIdAndIsFree(Long cartId, Long productId, boolean isFree);
    void deleteByCartId(Long cartId);
}
