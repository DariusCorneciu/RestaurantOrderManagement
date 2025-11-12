package com.example.restaurantordermanagement.DAO;

import com.example.restaurantordermanagement.models.Employee;
import com.example.restaurantordermanagement.models.Job;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Clasa care implementeaza inerfata JobDAO pentru fisiere de tip txt
 * Contine metode de read,save,update si delete pentru fisiere txt
 * @author Darius Corneciu
 * @since 2025-11-06
 * @version 1.0
 */
public class FileJobDAO implements JobDAO{

    private final String filePath = "data/job.txt";

    /**
     * Citeste toate joburile din fisierul job.txt
     * si returneaza o lista de obiecte Job.
     *
     * @author Darius Corneciu
     * @since 2025-11-12
     * @return lista de joburi
     */
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

    /**
     * Salveaza un job in fisierul txt aflat la filePath
     * @param job obiectul din clasa Job care trebuie salvat
     */
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

    /**
     * Metoda foloseste job pentru a cauta in lista de angajati.
     * Metoda equals este in clasa Job, care foloseste ID pentru cautare.
     * Ulterior daca index-ul este valid, continua si sterge elementul.
     * @param job obiectul primit care trebuie sters din lista
     * @author Darius Corneciu
     * @since 2025-11-12
     * @version 1.0
     * @see com.example.restaurantordermanagement.models.Job
     * @see #saveAll(List)  metoda folosita intern pentru a rescrie fisierul text.
     */
    @Override
    public void delete(Job job) {
        List<Job> jobList = new ArrayList<>(loadList());
        int index = jobList.indexOf(job);
        if(index != -1){
            jobList.remove(index);
            saveAll(jobList);
        }
    }

    /**
     * Metoda foloseste job pentru a cauta in lista de angajati.
     * Metoda equals este in clasa Job, care foloseste ID pentru cautare.
     * Ulterior seteaza la acel index job
     * @param job obiectul primit updatat, dar care pastreaza acelasi ID
     * @author Darius Corneciu
     * @since 2025-11-12
     * @version 1.0
     * @see com.example.restaurantordermanagement.models.Job
     * @see #saveAll(List) metoda folosita intern pentru a rescrie fisierul text.
     */
    @Override
    public void update(Job job) {
        List<Job> jobList = new ArrayList<>(loadList());
        int index = jobList.indexOf(job);
        if(index !=-1){
            jobList.set(index,job);
            saveAll(jobList);
        }

    }

    /**
     * Metoda folosita intern pentru a avea un cod mai curat, aceasta primeste o lista si suprascrie
     * fisierul text aflat la FilePath
     * @param jobList lista primita pentru a fii salvata
     * @author Darius Corneciu
     * @since 2025-11-06
     * @version 1.0
     * @see com.example.restaurantordermanagement.models.Job
     */
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
