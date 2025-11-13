package com.example.restaurantordermanagement.service;

import com.example.restaurantordermanagement.repository.ClockingRepository;

public class ClockingService {

    private final ClockingRepository clockingRepository;

    public ClockingService(ClockingRepository clockingRepository) {
        this.clockingRepository = clockingRepository;
    }

}
