package com.example.restaurantordermanagement.repository;

import com.example.restaurantordermanagement.DAO.ClockingDAO;
import com.example.restaurantordermanagement.models.Clocking;
import com.example.restaurantordermanagement.models.Table;

import java.time.LocalDate;
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
    public List<Clocking> allClocksFromToday(){
        //trimit o lista

        LocalDate today = LocalDate.now();

        return Collections.unmodifiableList(
                clockingsList.stream()
                        .filter(c -> c.getClockin().toLocalDate().equals(today) && c.getClockout() == null)
                        .toList()
        );
    }
    public List<Clocking> allClocksOfAnEmployee(int employeeId){


        return Collections.unmodifiableList(
                clockingsList.stream()
                        .filter(e->e.getEmployeeId() == employeeId)
                        .toList()
        );
    }


    public boolean isClockedIn(int employeeId) {
        LocalDate today = LocalDate.now();

        return clockingsList.stream()
                .filter(e -> e.getEmployeeId() == employeeId)
                .filter(e -> e.getClockin().toLocalDate().equals(today))
                .anyMatch(e -> e.getClockout() == null);
    }
}
