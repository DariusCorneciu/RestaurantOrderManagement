package com.example.restaurantordermanagement.service;

import com.example.restaurantordermanagement.models.Employee;
import com.example.restaurantordermanagement.models.Job;
import com.example.restaurantordermanagement.models.Table;
import com.example.restaurantordermanagement.repository.TableRepository;
import com.example.restaurantordermanagement.utils.AppContext;
import com.example.restaurantordermanagement.utils.ElementNotFoundException;
import com.example.restaurantordermanagement.utils.EmptyStringException;
import com.example.restaurantordermanagement.utils.SameElementException;

import java.util.List;

public class TableService {

    private final TableRepository tableRepository;

    public TableService(TableRepository tableRepository) {
        this.tableRepository = tableRepository;
    }
    public void create(Table table){
        if(table.getName() == null ||table.getName().isBlank()){
            throw new EmptyStringException("Empty Table name!");
        }
        if(tableRepository.tableWithTheSameCoords(table)){
            throw new SameElementException("Table with the same or in range with other table!");
        }

        tableRepository.create(table);

    }
    public void update(Table updatedTable){

        if(updatedTable.getName() == null || updatedTable.getName().isBlank()){

            throw new EmptyStringException("Table Name is empty or blank!");
        }

        if(tableRepository.tableWithTheSameCoords(updatedTable)){
            throw new SameElementException("Table with the same or in range with other table!");
        }
        tableRepository.update(updatedTable);
    }

    public void delete(int id){
        Table table = tableRepository.findElementById(id);
        if(table == null){
            throw new ElementNotFoundException("There is no table with the "+id);
        }
        tableRepository.delete(table);
    }

    public boolean hasActiveOrder(){
        return false;
    }
}
