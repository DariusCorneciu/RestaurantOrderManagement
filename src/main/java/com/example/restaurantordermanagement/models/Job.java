package com.example.restaurantordermanagement.models;

public class Job {
    private int id;
    private String jobName;
    public static int counterId = 0;
    public Job(int id, String jobName) {
        this.id = id;
        this.jobName = jobName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }
    public String generateFileFormat(){
        return this.id+";"+this.jobName;
    }

    @Override
    public boolean equals(Object obj) {
       if(this == obj) return true;
       if(obj == null || obj.getClass() != this.getClass()) return false;
       Job job = (Job) obj;
       return this.id == job.id;
    }
}
