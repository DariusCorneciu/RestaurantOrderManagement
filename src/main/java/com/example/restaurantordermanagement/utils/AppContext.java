package com.example.restaurantordermanagement.utils;

import com.example.restaurantordermanagement.DAO.EmployeeDAO;
import com.example.restaurantordermanagement.DAO.FileEmployeeDAO;
import com.example.restaurantordermanagement.repository.EmployeeRepository;
import com.example.restaurantordermanagement.service.EmployeeService;

/**
 * App context, o clasa singleton care are ca scop pasarea serviciilor in controllerele
 * in care este nevoie.
 * Am ales varianta asta pentru a avea un cod mai curat, toate serviciile sunt aici, acest lucru
 * face sa efit declararea in fiecare controller pentru DAO,Repository. Tot ce fac este sa cer de la AppContext Serviciul
 *
 * Folosesc variabile statice ca sa fie o singura instanta in toata aplicatia pentru fiecare DAO,Repository si Service
 *
 * @author Darius Corneciu
 * @since 2025-11-07
 *
 */
public class AppContext {

    // DAO
    private static EmployeeDAO employeeDAO;


    // Repository
    private static EmployeeRepository employeeRepository;

    // Service
    private static EmployeeService employeeService;

    private AppContext(){}

    /**
     * In cazul in care nu este initializat serviciul o fac pe loc cand este prima oara ceruta
     * Evitand asftel sa generez toate serviciile o data si sa nu fie folosite in aplicatie
     * @return Referinta statica la Serviciul de Employee
     */
    public static EmployeeService getEmployeeService(){
        if(employeeService == null){
            employeeDAO = new FileEmployeeDAO();
            employeeRepository = new EmployeeRepository(employeeDAO);
            employeeService = new EmployeeService(employeeRepository);
        }
        return employeeService;
    }
}
