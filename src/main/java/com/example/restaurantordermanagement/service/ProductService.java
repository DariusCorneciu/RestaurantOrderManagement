package com.example.restaurantordermanagement.service;

import com.example.restaurantordermanagement.repository.ProductRepository;

public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
}
