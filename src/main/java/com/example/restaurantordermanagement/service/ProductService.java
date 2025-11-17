package com.example.restaurantordermanagement.service;

import com.example.restaurantordermanagement.models.Product;
import com.example.restaurantordermanagement.models.ProductOrder;
import com.example.restaurantordermanagement.repository.ProductRepository;
import com.example.restaurantordermanagement.utils.AppContext;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


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
    public int[][] buildProductMatrix() {
        Map<Integer, List<ProductOrder>> grouped = getMostBothProducts();
        int[][] matrix = new int[grouped.size()][2];
        int index = 0;
        for (var entry : grouped.entrySet()) {
            int productId = entry.getKey();
            int totalQty = entry.getValue().stream()
                    .mapToInt(ProductOrder::getQuantity)
                    .sum();

            matrix[index][0] = productId;
            matrix[index][1] = totalQty;
            index++;
        }


        Arrays.sort(matrix, (a, b) -> Integer.compare(b[1], a[1]));

        return matrix;
    }



    private  Map<Integer, List<ProductOrder>> getMostBothProducts(){
        List<ProductOrder> productOrders = AppContext.getProductOrderService().getAllProductOrders();
        Map<Integer, List<ProductOrder>> grouped =
                productOrders.stream()
                        .collect(Collectors.groupingBy(ProductOrder::getProductId));

        grouped.values().forEach(list ->
                list.sort(Comparator.comparingInt(ProductOrder::getQuantity).reversed())
        );

        return grouped;
    }


}
