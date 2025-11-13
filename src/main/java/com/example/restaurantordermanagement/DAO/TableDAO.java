package com.example.restaurantordermanagement.DAO;

import com.example.restaurantordermanagement.models.Table;

import java.util.List;

public interface TableDAO {
    List<Table> loadList();
    int save(Table table);
    void delete(Table table);
    void update(Table table);
}
