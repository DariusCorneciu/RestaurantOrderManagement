package com.example.restaurantordermanagement.repository;

import com.example.restaurantordermanagement.DAO.JobDAO;
import com.example.restaurantordermanagement.models.Employee;
import com.example.restaurantordermanagement.models.Job;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Repository-ul pentru Job, aici creem o lista const pe care lucram mai departe
 * in program, nu folosim direct fisierul, pentru asta avem DAO.
 * Contine metodele CRUD, care apeleaza si modificarea fisierului prin intermediul DAO
 * Plus alte metode pentru filtare simple
 * @author Darius Corneciu
 * @since 2025-11-11
 * @see com.example.restaurantordermanagement.DAO.JobDAO
 * @see com.example.restaurantordermanagement.models.Job
 */
public class JobRepository {
    private final JobDAO jobDAO;
    private final List<Job> jobList;

    public JobRepository(JobDAO jobDAO) {
        this.jobDAO = jobDAO;
        jobList = new ArrayList<Job>(jobDAO.loadList());
    }

    /**
     * Metoda creata pentru a adauga un element in lista din repository si ulterior pasat catre fisier
     * @author Darius Corneciu
     * @since 2025-11-12
     * @param job elementul pentru primit pentru a fii adaugat in lista
     */
    public void create(Job job){
        int id = jobDAO.save(job);
        if(id != -1){
            job.setId(id);
            jobList.add(job);
        }

    }

    /**
     * Metoda cauta elementul, iar in cazul in care exista este updatat in lista din reporsitory si ulterior in fisier
     * @author Darius Corneciu
     * @since 2025-11-12
     * @param job elementul primit pentur a fii editat
     */
    public void update(Job job){
        int index = jobList.indexOf(job);
        if(index != -1){
            jobList.set(index,job);
            jobDAO.update(job);
        }


    }
    /**
     * Metoda cauta elementul, iar in cazul in care exista este sters in lista din reporsitory si ulterior in fisier
     * @author Darius Corneciu
     * @since 2025-11-12
     * @param job elementul primit pentur a fii editat
     */
    public void delete(Job job){
        int index = jobList.indexOf(job);
        if(index != -1){
            jobList.remove(index);
            jobDAO.delete(job);
        }
    }
    /**
     * Metoda parcurge lista, iar in cazul in care orice element contine variabila name, atunci este intors in layer-ul inferior
     * @author Darius Corneciu
     * @since 2025-11-12
     * @param name numele elementului cautat
     */
    public Job findElementByName(String name){
        //Job jobToReturn = null;
        for(Job j:jobList){
            if(j.getJobName().toLowerCase().contains(name)){
                return j;
            }
        }
        return null;
    }
    /**
     * Metoda parcurge lista, iar in cazul in care gaseste un element cu acelasi id cu cel cautat, acesta este intors in layer-ul inferior
     * @author Darius Corneciu
     * @since 2025-11-12
     * @param id id-ul elemntului cautat
     */
    public Job findElementById(int id){
        //Job jobToReturn = null;
        for(Job j:jobList){
            if(j.getId() == id){
                return j;
            }
        }
        return null;
    }
    /**
     * Verifica daca un job exista pentru a nu fii adaugat de doua ori in lista
     * @author Darius Corneciu
     * @since 2025-11-12
     * @param jobName numele elementului cautat
     */
    public boolean jobExists(String jobName){
        return jobList.stream()
                .anyMatch(e->e.getJobName().toLowerCase().equals(jobName.toLowerCase()));
    }
    /**
     * Intore o lista finala, care nu poate fii editata.
     * Totusi elementele din lista pot fii editate
     * @author Darius Corneciu
     * @since 2025-11-12
     */
    public List<Job> allJobs(){
        //trimit o lista
        return Collections.unmodifiableList(jobList);
    }


}
