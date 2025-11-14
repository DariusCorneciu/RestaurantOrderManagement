package com.example.restaurantordermanagement.repository;

import com.example.restaurantordermanagement.DAO.TableDAO;
import com.example.restaurantordermanagement.models.Job;
import com.example.restaurantordermanagement.models.Table;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TableRepository {
    private final TableDAO tableDAO;
    private final List<Table> tableList;

    public TableRepository(TableDAO tableDAO) {
        this.tableDAO = tableDAO;
        tableList = new ArrayList<Table>(tableDAO.loadList());
    }
    public void create(Table table){
        int id = tableDAO.save(table);
        if(id != -1){
            table.setId(id);
            tableList.add(table);
        }

    }
    public void update(Table table){
        int index = tableList.indexOf(table);
        if(index != -1){
            tableList.set(index,table);
            tableDAO.update(table);
        }


    }
    public void delete(Table table){
        int index = tableList.indexOf(table);
        if(index != -1){
            tableList.remove(index);
            tableDAO.delete(table);
        }
    }
    public Table findElementById(int id){
        //Job jobToReturn = null;
        for(Table t:tableList){
            if(t.getId() == id){
                return t;
            }
        }
        return null;
    }
    public List<Table> allTables(){
        //trimit o lista
        return Collections.unmodifiableList(tableList);
    }

    public boolean tableWithTheSameCoords(Table table){
        final double EPSILON = 0.05; // marja de eroare de 5%

        return tableList.stream().anyMatch(e->
                Math.abs(e.getxPercent() -table.getxPercent()) <EPSILON
                && Math.abs(e.getyPercent() -table.getyPercent()) <EPSILON);
    }


}
