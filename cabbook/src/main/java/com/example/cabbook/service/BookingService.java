package com.example.cabbook.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.cabbook.entity.VehicleLocation;
import com.example.cabbook.model.UserLocation;
import com.example.cabbook.repository.VehicleLocationRepository;

import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

    @Autowired
    private VehicleLocationRepository repository;

    public String bookCab(UserLocation userLocation) {
        List<VehicleLocation> vehicles = repository.findAll();
        Optional<VehicleLocation> closestVehicle = vehicles.stream()
                .filter(vehicle -> {
                    int userLatIntPart = userLocation.getLat().intValue();
                    int userLongIntPart = userLocation.getLongitude().intValue();
                    int vehicleLatIntPart = vehicle.getLat().intValue();
                    int vehicleLongIntPart = vehicle.getLongitude().intValue();

                    double userLatDecimal = userLocation.getLat() - userLatIntPart;
                    double userLongDecimal = userLocation.getLongitude() - userLongIntPart;
                    double vehicleLatDecimal = vehicle.getLat() - vehicleLatIntPart;
                    double vehicleLongDecimal = vehicle.getLongitude() - vehicleLongIntPart;

                    return userLatIntPart == vehicleLatIntPart
                            && userLongIntPart == vehicleLongIntPart
                            && Math.abs(userLatDecimal - vehicleLatDecimal) <= 0.01
                            && Math.abs(userLongDecimal - vehicleLongDecimal) <= 0.01;
                })
                .findFirst();

        if (closestVehicle.isPresent()) {
            VehicleLocation vehicle = closestVehicle.get();
            return vehicle.getVehicleNumber() + " is assigned to user " + userLocation.getUserName();
        } else {
            return "Sorry! No vehicles found near your location.";
        }
    }
}