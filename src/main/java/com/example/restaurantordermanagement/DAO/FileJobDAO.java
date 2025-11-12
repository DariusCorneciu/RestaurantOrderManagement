package com.example.restaurantordermanagement.DAO;

import com.example.restaurantordermanagement.models.Job;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileJobDAO implements JobDAO{

    private final String filePath = "data/job.txt";

    @Override
    public List<Job> loadList(){
       List<Job> jobs = new ArrayList<Job>();
       try(Scanner cin = new Scanner(new File(filePath))){
           while(cin.hasNextLine()){
               String data = cin.nextLine();
              // System.out.println(data);
               if(data.isEmpty()) continue;

               String[] columns = data.split(";");

               if(columns.length < 2){
                   throw new IOException("Linia "+data+" are format incorect!");
               }
               int id = Integer.parseInt(columns[0]);
               String jobName = columns[1];
               Job newJob = new Job(id,jobName);
               jobs.add(newJob);

           }
       } catch (Exception e) {
           e.printStackTrace();
           System.out.println(e.getMessage());

       }
       //poate nu e sortat fisierul si sa fiu sigur ca aleg maximul la counterId
        Job.counterId = jobs.stream()
                .map(e->e.getId())
                .max(Integer::compareTo)
                .orElse(0);
        return jobs;
    }


    @Override
    public int save(Job job) {
        job.setId(++Job.counterId);
        String data = job.generateFileFormat();

        try(BufferedWriter writer = new BufferedWriter(
                new FileWriter(filePath,true))){
            writer.write(data);
            return job.getId();

        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public void delete(Job job) {
        List<Job> jobList = new ArrayList<>(loadList());
        int index = jobList.indexOf(job);
        if(index != -1){
            jobList.remove(index);
            saveAll(jobList);
        }
    }

    @Override
    public void update(Job job) {
        List<Job> jobList = new ArrayList<>(loadList());
        int index = jobList.indexOf(job);
        if(index !=-1){
            jobList.set(index,job);
            saveAll(jobList);
        }

    }

    private void saveAll(List<Job> jobList){
        try(BufferedWriter writer = new BufferedWriter(
                new FileWriter(filePath,false))){
            for(Job j: jobList){
                writer.write(j.generateFileFormat());
            }


        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}
