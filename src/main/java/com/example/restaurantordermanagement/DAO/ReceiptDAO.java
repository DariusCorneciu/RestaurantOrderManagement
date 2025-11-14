package com.example.restaurantordermanagement.DAO;

import com.example.restaurantordermanagement.models.Receipt;
import com.example.restaurantordermanagement.models.Table;

import java.util.List;

public interface ReceiptDAO {
    List<Receipt> loadList();
    int save(Receipt receipt);
    void delete(Receipt receipt);
    void update(Receipt receipt);
}
