package com.example.vehicleloc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.vehicleloc.entity.VehicleLocation;

@Repository
public interface VehicleLocationRepository extends JpaRepository<VehicleLocation, Integer> {
}