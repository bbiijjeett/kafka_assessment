package com.example.cabbook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.cabbook.entity.VehicleLocation;


@Repository
public interface VehicleLocationRepository extends JpaRepository<VehicleLocation, Integer> {
}