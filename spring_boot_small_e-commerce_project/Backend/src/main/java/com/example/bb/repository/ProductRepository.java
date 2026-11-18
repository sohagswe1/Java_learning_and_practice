package com.example.bb.repository;

import com.example.bb.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByNameContainingIgnoreCase(String name);
    List<Product> findByCategoryId(Long categoryId);
    List<Product> findByHasFreeGiftTrue();
    java.util.Optional<Product> findByNameIgnoreCase(String name);
}
