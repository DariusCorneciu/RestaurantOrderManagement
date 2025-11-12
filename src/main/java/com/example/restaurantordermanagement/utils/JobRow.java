package com.example.restaurantordermanagement.utils;

public class JobRow {
    private String name;
    private long employeesNumber;

    public JobRow() {

    }

    public JobRow(String name, long employeesNumber) {
        this.name = name;
        this.employeesNumber = employeesNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getEmployeesNumber() {
        return employeesNumber;
    }

    public void setEmployeesNumber(int employeesNumber) {
        this.employeesNumber = employeesNumber;
    }
}
