package com.example.restaurantordermanagement.DAO;

import com.example.restaurantordermanagement.models.Job;

import java.util.List;

public interface JobDAO {
    List<Job> loadList();
    int save(Job job);
    void delete(Job job);
    void update(Job job);


}
