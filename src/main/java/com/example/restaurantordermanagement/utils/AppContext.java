package com.example.restaurantordermanagement.utils;

import com.example.restaurantordermanagement.DAO.*;
import com.example.restaurantordermanagement.repository.*;
import com.example.restaurantordermanagement.service.*;

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


    // Service
    private static EmployeeService employeeService;
    private static JobService jobService;
    private static ProductService productService;
    private static TableService tableService;
    private static ClockingService clockingService;
    private static ProductOrderService productOrderService;
    private static OrderService orderService;
    private AppContext(){}

    /**
     * In cazul in care nu este initializat serviciul o fac pe loc cand este prima oara ceruta
     * Evitand asftel sa generez toate serviciile o data si sa nu fie folosite in aplicatie
     * @return Referinta statica la Serviciul de Employee
     */
    public static EmployeeService getEmployeeService(){
        if(employeeService == null){
            // DAO
            EmployeeDAO employeeDAO = new FileEmployeeDAO();
            // Repository
            EmployeeRepository employeeRepository = new EmployeeRepository(employeeDAO);
            employeeService = new EmployeeService(employeeRepository);
        }
        return employeeService;
    }
    public static JobService getJobService(){
        if(jobService == null){
            JobDAO jobDAO = new FileJobDAO();
            JobRepository jobRepository = new JobRepository(jobDAO);
            jobService = new JobService(jobRepository);
        }
        return jobService;
    }
    public static ProductService getProductService(){
        if(productService == null){
            ProductDAO productDAO = new FileProductDAO();
            ProductRepository productRepository = new ProductRepository(productDAO);
            productService = new ProductService(productRepository);
        }
        return productService;
    }
    public static TableService getTableService(){
        if(tableService == null){
            TableDAO tableDAO = new FileTableDAO();
            TableRepository tableRepository = new TableRepository(tableDAO);
            tableService = new TableService(tableRepository);
        }
        return tableService;
    }
    public static ClockingService getClockingService(){
        if(clockingService == null){
            ClockingDAO clockingDAO = new FileClockingDAO();
            ClockingRepository clockingRepository = new ClockingRepository(clockingDAO);
            clockingService = new ClockingService(clockingRepository);
        }
        return clockingService;
    }

    public static ProductOrderService getProductOrderService(){
        if(productOrderService == null){
            ProductOrderDAO productOrderDAO = new FileProductOrderDTO();
            ProductOrderRepository productOrderRepository = new ProductOrderRepository(productOrderDAO);
            productOrderService = new ProductOrderService(productOrderRepository);
        }
        return productOrderService;
    }
    public static OrderService getOrderService(){
        if(orderService == null){
            OrderDAO orderDAO = new FileOrderDAO();
            OrderRepository productOrderRepository = new OrderRepository(orderDAO);
            orderService = new OrderService(productOrderRepository);
        }
        return orderService;
    }

}
