package com.example.restaurantordermanagement.models;

public class Order {
private int id;
private int tableId;
private Table table;
private int employeeId;
private Employee employee;
private boolean isActive;
    public Order(int id, int tableId, int employeeId) {
        this.id = id;
        this.tableId = tableId;
        this.employeeId = employeeId;
        isActive = false;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
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
        return id+";"+tableId+";"+employeeId+"\n";
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id == order.id;
    }


}
