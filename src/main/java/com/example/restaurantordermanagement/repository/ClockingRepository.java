package com.example.restaurantordermanagement.repository;

import com.example.restaurantordermanagement.DAO.ClockingDAO;
import com.example.restaurantordermanagement.models.Clocking;
import com.example.restaurantordermanagement.models.Table;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ClockingRepository {
    private final ClockingDAO clockingDAO;
    private final List<Clocking> clockingsList;

    public ClockingRepository(ClockingDAO clockingDAO) {
        this.clockingDAO = clockingDAO;
        clockingsList = new ArrayList<>(clockingDAO.loadList());
    }

    public void create(Clocking clocking){
        int id = clockingDAO.save(clocking);
        if(id != -1){
            clocking.setId(id);
            clockingsList.add(clocking);
        }

    }
    public void update(Clocking clocking){
        int index = clockingsList.indexOf(clocking);
        if(index != -1){
            clockingsList.set(index,clocking);
            clockingDAO.update(clocking);
        }


    }
    public void delete(Clocking clocking){
        int index = clockingsList.indexOf(clocking);
        if(index != -1){
            clockingsList.remove(index);
            clockingDAO.delete(clocking);
        }
    }
    public Clocking findElementById(int id){
        //Job jobToReturn = null;
        for(Clocking t:clockingsList){
            if(t.getId() == id){
                return t;
            }
        }
        return null;
    }
    public List<Clocking> allTables(){
        //trimit o lista
        return Collections.unmodifiableList(clockingsList);
    }
}
