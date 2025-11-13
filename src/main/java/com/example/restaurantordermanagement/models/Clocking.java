package com.example.restaurantordermanagement.models;

import com.example.restaurantordermanagement.utils.AppContext;

import java.time.LocalDateTime;
import java.util.Objects;

public class Clocking {
    private int id;
    private int employeeId;
    private Employee employee;
    private LocalDateTime clockin;
    private LocalDateTime clockout;
    public static int counterId=0;

    public Clocking(int id, int employeeId, LocalDateTime clockin, LocalDateTime clockout) {
        this.id = id;
        this.employeeId = employeeId;
        this.clockin = clockin;
        this.clockout = clockout;
    }

    public Clocking(Clocking other) {
        this.id = other.id;
        this.employeeId = other.employeeId;
        this.clockin = other.clockin;
        this.clockout = other.clockout;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public Employee getEmployee() {
        if(employee == null){
            employee = AppContext.getEmployeeService().findEmployeeById(employeeId);
        }
        return employee;
    }

    public LocalDateTime getClockin() {
        return clockin;
    }

    public LocalDateTime getClockout() {
        return clockout;
    }

    public void setClockout(LocalDateTime clockout) {
        this.clockout = clockout;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Clocking clocking = (Clocking) o;
        return id == clocking.id;
    }
    public String generateFileFormat(){
        return id+";"+employeeId+";"+clockin+";"+clockout+"\n";
    }
}
