package com.example.vehicleloc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.vehicleloc.entity.VehicleLocation;
import com.example.vehicleloc.service.VehicleLocationService;

import java.util.List;

@RestController
@RequestMapping("/vehicle")
public class VehicleLocationController {

    @Autowired
    private VehicleLocationService service;

    @PostMapping
    public VehicleLocation addVehicle(@RequestBody VehicleLocation vehicle) {
        return service.saveVehicle(vehicle);
    }

    @GetMapping
    public List<VehicleLocation> getVehicles() {
        return service.getAllVehicles();
    }

    @PostMapping("/generateloc")
    public String startGeneratingLocations() {
        service.generateRandomLocations();
        return "Location generation started.";
    }

    @PostMapping("/stopgen")
    public String stopGeneratingLocations() {
        service.stopGeneratingLocations();
        return "Location generation stopped.";
    }
}