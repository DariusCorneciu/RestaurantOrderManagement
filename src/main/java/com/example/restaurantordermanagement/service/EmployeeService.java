package com.example.restaurantordermanagement.service;

import com.example.restaurantordermanagement.controllers.EmployeesController;
import com.example.restaurantordermanagement.models.Employee;
import com.example.restaurantordermanagement.models.Job;
import com.example.restaurantordermanagement.repository.EmployeeRepository;
import com.example.restaurantordermanagement.utils.*;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class EmployeeService {

    public final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    /**
     * Face verificari pentru nume si verifica daca angajatul exista.
     * In cazul in care verificarile sunt toate valide, trimite pe stratul repository
     * @author Darius Corenciu
     * @since 2025-11-07
     * @throws EmptyStringException - in cazul in care First/Last Name sunt blank sau null
     * @throws AlreadyExistsException - in cazul in care angajatul deja exista(cautare dupa nume)
     * @param employee - primeste un angajat partial(fara id)
     * @see EmployeesController
     * @see com.example.restaurantordermanagement.repository.EmployeeRepository
     */
    public void create(Employee employee){


        if(employee.getFirstName() == null || employee.getFirstName().isBlank()){

            throw new EmptyStringException("First Name is empty or blank!");
        }
        if(employee.getLastName() == null || employee.getLastName().isBlank()){
            throw new EmptyStringException("First Name is empty or blank!");
        }

        String fullName = employee.getFirstName()+" "+employee.getLastName();
        List<Employee> employeeList = employeeRepository.searchByName(fullName);

        if(employeeList.isEmpty()){
            employeeRepository.create(employee);
        }else{
            throw new AlreadyExistsException("Employee by the name "+fullName+" already exists!");
        }
    }

    public void deleteEmployee(int id){
       Employee e = findEmployeeById(id);
        employeeRepository.delete(e);
    }

    public Employee findEmployeeById(int id){
        System.out.println(id);
        Employee e = employeeRepository.findElementById(id);
        if(e == null){
            throw new ElementNotFoundException("Employee not found");
        }
        return e;
    }

    public void update(Employee employee){

        if(employee.getFirstName() == null || employee.getFirstName().isBlank()){

            throw new EmptyStringException("First Name is empty or blank!");
        }
        if(employee.getLastName() == null || employee.getLastName().isBlank()){
            throw new EmptyStringException("First Name is empty or blank!");
        }
        Employee realEmployee = employeeRepository.findElementById(employee.getId());

        if(realEmployee.getLastName().equals(employee.getFirstName())
                && realEmployee.getLastName().equals(employee.getLastName())
        && realEmployee.getSalary() == employee.getSalary()
        && realEmployee.getJobId() == realEmployee.getJobId()){
            throw new SameElementException("No changes in at this employee!");
        }
        employeeRepository.update(employee);

    }

    public List<String> showEmployee(){


        return employeeRepository.employees.stream()
                .map(e-> e.toString()
                        +" - "+e.GetJob().getJobName()+"").toList();
    }

    public List<JobRow> countEmployeesForAllJobs(){
           List<JobRow> jobRows = new ArrayList<>();

           List<Job> jobList = AppContext.getJobService().getAllJobs();


           for(Job j: jobList) {
               String name = j.getJobName();
               long number = employeeRepository
                       .filterEmployeesByJob(j.getId()).stream().count();
               jobRows.add(
                       new JobRow(name, number)
               );

           }

           return jobRows;
    }
    public long countEmployeeAtJobById(int id){
        return employeeRepository.filterEmployeesByJob(id)
                .stream()
                .count();
    }
}
