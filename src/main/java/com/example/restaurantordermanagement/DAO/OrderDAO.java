package com.example.restaurantordermanagement.DAO;

import com.example.restaurantordermanagement.models.Order;
import com.example.restaurantordermanagement.models.Product;

import java.util.List;

public interface OrderDAO {

    List<Order> loadList();
    int save(Order order);
    void delete(Order order);
    void update(Order order);

}
