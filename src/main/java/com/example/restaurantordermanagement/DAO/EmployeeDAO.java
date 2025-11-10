package com.example.restaurantordermanagement.DAO;

import com.example.restaurantordermanagement.models.Employee;

import java.util.List;

/**
 * Folosesc o interfata pentru a putea in cazul in care vreau sa modific
 * logica si sa fac spre exemplu un sistem care sa contina o baza de date
 * creez o clasa noua DAO care implementeaza Interfata si restul de cod
 * ramane nemodificat.
 *
 * @author Darius Corneciu
 * @since 2025-11-06
 * @version 1.0
 * @see com.example.restaurantordermanagement.DAO.FileEmployeeDAO
 */
public interface EmployeeDAO {
     List<Employee> loadList();
     int save(Employee e);
     void update(Employee e);
    void delete(Employee e);
}
