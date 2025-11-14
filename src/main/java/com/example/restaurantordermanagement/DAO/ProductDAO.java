package com.example.restaurantordermanagement.DAO;

import com.example.restaurantordermanagement.models.Product;


import java.util.List;

public interface ProductDAO {
    List<Product> loadList();
    int save(Product product);
    void delete(Product product);
    void update(Product product);
}
