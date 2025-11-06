package com.example.restaurantordermanagement.DAO;

import com.example.restaurantordermanagement.models.Employee;

import java.util.List;

public interface EmployeeDAO {
    public List<Employee> loadList();
    public void save(Employee e);
}
