package com.example.restaurantordermanagement.service;

import com.example.restaurantordermanagement.models.Clocking;
import com.example.restaurantordermanagement.models.Employee;
import com.example.restaurantordermanagement.models.Job;
import com.example.restaurantordermanagement.models.Table;
import com.example.restaurantordermanagement.repository.ClockingRepository;
import com.example.restaurantordermanagement.utils.*;

import java.io.InvalidObjectException;
import java.util.List;

public class ClockingService {

    private final ClockingRepository clockingRepository;

    public ClockingService(ClockingRepository clockingRepository) {
        this.clockingRepository = clockingRepository;
    }


    public void create(Clocking clocking){
        clocking.getEmployee();
        if(alreadyClockedIn(clocking.getEmployeeId())){
            throw new AlreadyExistsException("This employee is already clockedIn today!");
        }
        clockingRepository.create(clocking);
    }

    public void update(Clocking updatedClocking){
        if(updatedClocking.getClockin() == null || updatedClocking.getClockout() == null){

            throw new EmptyStringException("Clock in/out does not exists!");
        }

        if (updatedClocking.getClockin().isAfter(updatedClocking.getClockout()) ||
                updatedClocking.getClockin().isEqual(updatedClocking.getClockout())) {
            throw new InvalidDateException( "Clock-in time is greater than or equal to clock-out. Try again!");
        }

        clockingRepository.update(updatedClocking);
    }

    public void deleteallClockingsOfAnEmployee(int employeeId){

        List<Clocking> list = clockingRepository.allClocksOfAnEmployee(employeeId);
        for(Clocking e:list){
            clockingRepository.delete(e);
        }
    }


    public List<Clocking> getAllClockings(){
        return clockingRepository.allClocksFromToday();
    }
    public boolean alreadyClockedIn(int employeeId){

        return clockingRepository.isClockedIn(employeeId);

    }
}
