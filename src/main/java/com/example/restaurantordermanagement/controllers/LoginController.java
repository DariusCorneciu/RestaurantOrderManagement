package com.example.restaurantordermanagement.controllers;

import com.example.restaurantordermanagement.DAO.EmployeeDAO;
import com.example.restaurantordermanagement.DAO.FileEmployeeDAO;
import com.example.restaurantordermanagement.models.Employee;
import com.example.restaurantordermanagement.repository.EmployeeRepository;
import com.example.restaurantordermanagement.service.EmployeeService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.List;

public class LoginController {
    @FXML
    private Label welcomeText;


   private EmployeeDAO employeeDAO = new FileEmployeeDAO();
    private EmployeeRepository employeeRepository = new EmployeeRepository(employeeDAO);
    private EmployeeService employeeService = new EmployeeService(employeeRepository);
    @FXML
    protected void onHelloButtonClick() {
        List<String> formattedEmployee = employeeService.showEmployee();
        welcomeText.setText(String.join("\n",formattedEmployee));
    }


}
