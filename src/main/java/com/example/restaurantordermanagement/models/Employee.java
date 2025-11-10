package com.example.restaurantordermanagement.models;

import com.example.restaurantordermanagement.utils.AppContext;
import com.example.restaurantordermanagement.utils.ElementNotFoundException;
import javafx.fxml.FXML;

public class Employee {

    private int Id;
    private String firstName;
    private String lastName;

    private int salary;
    public static int counterId = 0;
    private int JobId;
    private Job job;

    public Employee(int id, String firstName, String lastName, int jobId,int salary) {
        Id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        JobId = jobId;
        this.salary = salary;
    }

    public Job GetJob(){
        if(job == null){

            try{
                this.job = AppContext.getJobService().getJobAtId(JobId);
            } catch (ElementNotFoundException e) {
                this.job = new Job(-1,e.getMessage());
            }

        }
        return job;
    }

    @Override
    public boolean equals(Object obj){
        if(this == obj) return true;
        if(obj == null || this.getClass() != obj.getClass()) return false;
        Employee other = (Employee) obj;
        return Id == other.Id;
    }

    public int getId() {
        return Id;
    }

    public int getJobId() {
        return JobId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getSalary() {
        return salary;
    }

    public void setId(int id) {
        Id = id;
    }

    public String GenerateTxtString(){
        return Id +";"+firstName+";"+lastName+";"+JobId+";"+salary+"\n";
    }
    @Override
    public String toString() {

        return Id + "| " + firstName + " " + lastName + " | " + salary + " RON";
    }

}
