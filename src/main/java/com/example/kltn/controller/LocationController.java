package com.example.kltn.controller;

import com.example.kltn.entity.Location;
import com.example.kltn.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/locations")
public class LocationController {
    
    private final LocationService locationService;
    
    @Autowired
    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }
    
    @PostMapping
    public ResponseEntity<?> createLocation(@RequestBody Location location, @RequestParam Long authorId) {
        try {
            Location savedLocation = locationService.saveLocation(location, authorId);
            return ResponseEntity.ok(savedLocation);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi khi tạo địa điểm: " + e.getMessage());
        }
    }
    
    // Các phương thức khác của controller
} 