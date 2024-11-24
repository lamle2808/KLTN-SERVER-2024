package com.example.kltn.controller;

import com.example.kltn.entity.Location;
import com.example.kltn.entity.ImageLocation;
import com.example.kltn.service.LocationService;
import com.example.kltn.service.CloudinaryService;
import com.example.kltn.service.ImageLocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import java.util.HashMap;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/locations")
@RequiredArgsConstructor
@Slf4j
public class LocationController {

    private final LocationService locationService;
    private final CloudinaryService cloudinaryService;
    private final ImageLocationService imageLocationService;

    @GetMapping
    public ResponseEntity<List<Location>> getAllLocations() {
        List<Location> locations = locationService.getAll();
        return ResponseEntity.ok(locations);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Location> getLocationById(@PathVariable Long id) {
        Location location = locationService.getById(id);
        return ResponseEntity.ok(location);
    }

    @PostMapping
    public ResponseEntity<Location> createLocation(@RequestBody Location location) {
        Location createdLocation = locationService.saveOrUpdate(location);
        return ResponseEntity.ok(createdLocation);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Location> updateLocation(@PathVariable Long id, @RequestBody Location location) {
        location.setId(id);
        Location updatedLocation = locationService.saveOrUpdate(location);
        return ResponseEntity.ok(updatedLocation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLocation(@PathVariable Long id) {
        locationService.deleteLocation(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(
        value = "/{locationId}/upload-image",
        consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<Map<String, Object>> uploadImage(
        @PathVariable Long locationId,
        @RequestPart("file") MultipartFile file
    ) {
        try {
            log.info("Start uploading image for location: {}", locationId);
            
            if (file.isEmpty()) {
                return ResponseEntity.badRequest()
                    .body(Map.of("error", "File is empty"));
            }

            ImageLocation result = imageLocationService.uploadImageForLocation(file, locationId);
            
            Map<String, Object> response = new HashMap<>();
            response.put("id", result.getId());
            response.put("imageUrl", result.getImageLink());
            response.put("size", result.getSize());
            response.put("type", result.getType());
            response.put("uploadDate", result.getDate());
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            log.error("Error uploading image: ", e);
            return ResponseEntity.badRequest()
                .body(Map.of("error", e.getMessage()));
        }
    }
} 