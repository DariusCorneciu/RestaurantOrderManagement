package com.example.restaurantordermanagement.DAO;

import com.example.restaurantordermanagement.models.Clocking;

import java.util.List;

public interface ClockingDAO {
    List<Clocking> loadList();
    int save(Clocking c);
    void update(Clocking clocking);
    void delete(Clocking clocking);
}
