package com.app.ecommerce.service;

import com.app.ecommerce.dto.CartItemRequest;
import com.app.ecommerce.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartService {
    private final ProductRepository productRepository;

    public void addToCart(String userId, CartItemRequest request) {
        
    }
}
