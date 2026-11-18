package com.example.bb.service;

import com.example.bb.entity.Category;
import com.example.bb.entity.Product;
import com.example.bb.repository.CategoryRepository;
import com.example.bb.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
    }

    public List<Product> searchProducts(String name) {
        return productRepository.findByNameContainingIgnoreCase(name);
    }

    public List<Product> getProductsByCategory(Long categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }

    public List<Product> getFreeGiftProducts() {
        return productRepository.findByHasFreeGiftTrue();
    }

    public Product createProduct(Product product, Long categoryId) {
        Category category;
        if (categoryId == null || categoryId <= 0) {
            category = categoryRepository.findAll().stream().findFirst()
                    .orElseGet(() -> categoryRepository.save(Category.builder().name("Sports").description("Default Category").build()));
        } else {
            category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new RuntimeException("Category not found: " + categoryId));
        }
        product.setCategory(category);
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, Product updated, Long categoryId) {
        Product existing = getProductById(id);
        existing.setName(updated.getName());
        existing.setDescription(updated.getDescription());
        existing.setPrice(updated.getPrice());
        existing.setStock(updated.getStock());
        existing.setImageUrl(updated.getImageUrl());
        existing.setBrand(updated.getBrand());
        existing.setHasFreeGift(updated.isHasFreeGift());
        existing.setFreeGiftDescription(updated.getFreeGiftDescription());

        if (categoryId != null && categoryId > 0) {
            Category category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new RuntimeException("Category not found: " + categoryId));
            existing.setCategory(category);
        } else if (existing.getCategory() == null) {
            Category category = categoryRepository.findAll().stream().findFirst()
                    .orElseGet(() -> categoryRepository.save(Category.builder().name("Sports").description("Default Category").build()));
            existing.setCategory(category);
        }
        return productRepository.save(existing);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
