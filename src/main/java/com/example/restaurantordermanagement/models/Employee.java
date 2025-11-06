package com.example.restaurantordermanagement.models;

public class Employee {

    private int Id;
    private String firstName;
    private String lastName;

    private int JobId;
    private Job job;

    public Employee(int id, String firstName, String lastName, int jobId) {
        Id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        JobId = jobId;
    }

    public Job GetJob(){
        if(job == null){

        }
        return job;
    }

    public String GenerateTxtString(){
        return Id +";"+firstName+";"+lastName+";"+JobId+";\n";
    }
}
