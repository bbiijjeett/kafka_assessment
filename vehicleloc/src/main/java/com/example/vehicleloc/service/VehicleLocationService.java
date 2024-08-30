package com.example.vehicleloc.service;

import com.example.vehicleloc.entity.VehicleLocation;
import com.example.vehicleloc.repository.VehicleLocationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class VehicleLocationService {

    @Autowired
    private VehicleLocationRepository repository;

    @Autowired
    private KafkaTemplate<String, VehicleLocation> kafkaTemplate;

    private ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private boolean generateLocations = false;

    private static final String TOPIC = "vehicle_location";

    public VehicleLocation saveVehicle(VehicleLocation vehicle) {
        return repository.save(vehicle);
    }

    public List<VehicleLocation> getAllVehicles() {
        return repository.findAll();
    }

    public void generateRandomLocations() {
        generateLocations = true;
        scheduler.scheduleAtFixedRate(() -> {
            if (generateLocations) {
                List<VehicleLocation> vehicles = repository.findAll();
                Random random = new Random();

                vehicles.forEach(vehicle -> {
                    vehicle.setLat(20.0 + (30.0 - 20.0) * random.nextDouble());
                    vehicle.setLongitude(70.0 + (80.0 - 70.0) * random.nextDouble());
                    repository.save(vehicle);
                    kafkaTemplate.send(TOPIC, vehicle);
                });
            }
        }, 0, 5, TimeUnit.SECONDS);
    }

    public void stopGeneratingLocations() {
        generateLocations = false;
    }
}