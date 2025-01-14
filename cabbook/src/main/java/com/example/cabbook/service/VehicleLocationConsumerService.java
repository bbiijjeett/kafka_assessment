package com.example.cabbook.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.example.cabbook.entity.VehicleLocation;
import com.example.cabbook.repository.VehicleLocationRepository;

@Service
public class VehicleLocationConsumerService {

    @Autowired
    private VehicleLocationRepository repository;

    @KafkaListener(topics = "vehicle_location", groupId = "vehicle_group")
    public void consume(VehicleLocation vehicleLocation) {
        repository.save(vehicleLocation);
    }
}