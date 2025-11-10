package com.example.restaurantordermanagement.repository;

import com.example.restaurantordermanagement.DAO.JobDAO;
import com.example.restaurantordermanagement.models.Employee;
import com.example.restaurantordermanagement.models.Job;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class JobRepository {
    private final JobDAO jobDAO;
    private final List<Job> jobList;

    public JobRepository(JobDAO jobDAO) {
        this.jobDAO = jobDAO;
        jobList = new ArrayList<Job>(jobDAO.loadList());
    }
    public void create(Job job){
        int id = jobDAO.save(job);
        if(id != -1){
            job.setId(id);
            jobList.add(job);
        }

    }
    public void update(Job job){
        int index = jobList.indexOf(job);
        if(index != -1){
            jobList.set(index,job);
            jobDAO.update(job);
        }


    }
    public void delete(Job job){
        int index = jobList.indexOf(job);
        if(index != -1){
            jobList.remove(index);
            jobDAO.update(job);
        }
    }

    public Job findElementById(int id){
        //Job jobToReturn = null;
        for(Job j:jobList){
            if(j.getId() == id){
                return j;
            }
        }
        return null;
    }
    public boolean jobExists(String jobName){
        return jobList.stream()
                .anyMatch(e->e.getJobName() ==jobName);
    }
    public List<Job> allJobs(){
        //trimit o lista
        return Collections.unmodifiableList(jobList);
    }


}
