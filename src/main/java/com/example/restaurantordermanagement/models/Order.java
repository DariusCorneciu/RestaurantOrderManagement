package com.example.restaurantordermanagement.models;

import com.example.restaurantordermanagement.utils.AppContext;
import com.example.restaurantordermanagement.utils.ElementNotFoundException;

import java.util.ArrayList;
import java.util.List;

public class Order {
private int id;
private int tableId;
private Table table;
private int employeeId;
private Employee employee;

public static int counterId =0;
private List<ProductOrder> products;
private boolean isActive;


    public Order(int id, int tableId, int employeeId,boolean isActive) {
        this.id = id;
        this.tableId = tableId;
        this.employeeId = employeeId;
        this.isActive = isActive;
    }
    public Order(Order other){
        this.id = other.id;
        this.tableId = other.tableId;
        this.employeeId = other.employeeId;
        this.isActive = other.isActive;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Table getTable() {
        if(table == null){
            this.table = AppContext.getTableService().getTableAtId(tableId);

        }
        return table;
    }

    public Employee getEmployee() {
        if(employee == null){
            this.employee = AppContext.getEmployeeService().findEmployeeById(employeeId);
        }
        return employee;
    }

    public List<ProductOrder> getProducts() {

        this.products = AppContext.getProductOrderService().productsOfAnOrder(id);
        return products;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getTableId() {
        return tableId;
    }

    public int getEmployeeId() {
        return employeeId;
    }
    public String generateFileFormat(){
        return id+";"+tableId+";"+employeeId+";"+isActive+"\n";
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id == order.id;
    }


}
