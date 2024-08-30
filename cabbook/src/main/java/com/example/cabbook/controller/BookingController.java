package com.example.cabbook.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.cabbook.model.UserLocation;
import com.example.cabbook.service.BookingService;

@RestController
@RequestMapping("/book")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping
    public String bookVehicle(@RequestBody UserLocation userLocation) {
        return bookingService.bookCab(userLocation);
    }
}
