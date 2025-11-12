package com.example.restaurantordermanagement.models;

public class Job implements Comparable<Job>{
    private int id;
    private String jobName;
    public static int counterId = 0;
    public Job(int id, String jobName) {
        this.id = id;
        this.jobName = jobName;
    }
    public Job(Job other) {
        this.id = other.id;
        this.jobName = other.jobName;

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
        return this.id+";"+this.jobName+"\n";
    }

    @Override
    public boolean equals(Object obj) {
       if(this == obj) return true;
       if(obj == null || obj.getClass() != this.getClass()) return false;
       Job job = (Job) obj;
       return this.id == job.id;
    }

    @Override
    public int compareTo(Job obj) { // 0 -> egal, -1 ->this, 1->o
        if(this == obj) return 0;
        if(this.id < obj.id){
            return -1;
        }else if (this.id == obj.id){
            return 0;
        }else{
            return 1;
        }
        //
        //return Integer.compare(this.id,obj.id);
    }
}
