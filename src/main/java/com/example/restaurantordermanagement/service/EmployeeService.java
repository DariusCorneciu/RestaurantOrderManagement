package com.example.restaurantordermanagement.service;

import com.example.restaurantordermanagement.models.Employee;
import com.example.restaurantordermanagement.repository.EmployeeRepository;

import java.util.List;

public class EmployeeService {

    public final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public void create(Employee employee){

//        if(employee.getFirstName() == null || employee.getFirstName().isBlank()){
//
//        }
//        if(employee.getLastName() == null || employee.getLastName().isBlank()){
//
//        }

        String fullName = employee.getFirstName()+" "+employee.getLastName();
        List<Employee> employeeList = employeeRepository.searchByName(fullName);

        if(employeeList.isEmpty()){
            employeeRepository.create(employee);
        }
    }


   // public

    public List<String> showEmployee(){
        return employeeRepository.employees.stream()
                .map(e->e.getId()+"|"+e.getFirstName()+" "+e.getLastName()
                        +" ["+e.getJobId()+"]").toList();
    }
}
