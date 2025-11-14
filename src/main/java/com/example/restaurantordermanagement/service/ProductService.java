package com.example.restaurantordermanagement.service;

import com.example.restaurantordermanagement.models.Product;
import com.example.restaurantordermanagement.repository.ProductRepository;

import java.util.List;

public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product getProductById(int id){
        return productRepository.findElementById(id);
    }
    public List<Product> getAllProducts(){
        return productRepository.allProducts();
    }

}
