package com.example.restaurantordermanagement.service;

import com.example.restaurantordermanagement.models.Job;
import com.example.restaurantordermanagement.repository.JobRepository;
import com.example.restaurantordermanagement.utils.AppContext;
import com.example.restaurantordermanagement.utils.ElementNotFoundException;
import com.example.restaurantordermanagement.utils.EmptyStringException;
import com.example.restaurantordermanagement.utils.SameElementException;

import java.io.NotActiveException;
import java.util.List;

public class JobService {
    private final JobRepository jobRepository;

    public JobService(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    public Job getJobAtId(int id){
        Job job = jobRepository.findElementById(id);
        if(job!= null){
            return job;
        }
        throw new ElementNotFoundException("Job not found!");
    }

    public void create(Job job){
        if(job.getJobName() == null ||job.getJobName().isBlank()){
            throw new EmptyStringException("Empty Job name!");
        }

        if(jobRepository.jobExists(job.getJobName())){
            throw new SameElementException("This job with the name "+job.getJobName()
                    +" already exists!");
        }
        jobRepository.create(job);

    }

    public void delete(int id){
        Job job = jobRepository.findElementById(id);
        if(job == null){
            throw new ElementNotFoundException("There is no job with the "+id);
        }
        jobRepository.delete(job);
    }


    public void update(Job job){
        if(job.getJobName() == null || job.getJobName().isBlank()){

            throw new EmptyStringException("Job Name is empty or blank!");
        }


        Job realJob = jobRepository.findElementById(job.getId());
        if(jobRepository.jobExists(job.getJobName())){
            throw new SameElementException("Same job, no need to update!");
        }
        jobRepository.update(job);
    }


    public List<Job> getAllJobs(){
        return jobRepository.allJobs();
    }

    public Job findJob(String name){
        if(name == null || name.isBlank()){
            throw new EmptyStringException("Search field is invalid!");
        }

        Job j = jobRepository.findElementByName(name.toLowerCase());
        if(j == null){
            throw new ElementNotFoundException("The job you searched does not exists!");
        }
        return j;
    }


}
