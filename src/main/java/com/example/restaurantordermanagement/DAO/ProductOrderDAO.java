package com.example.restaurantordermanagement.DAO;

import com.example.restaurantordermanagement.models.Product;
import com.example.restaurantordermanagement.models.ProductOrder;

import java.util.List;

public interface ProductOrderDAO {

    List<ProductOrder> loadList();
    int save(ProductOrder product);
    void delete(ProductOrder product);
    void update(ProductOrder product);

}
