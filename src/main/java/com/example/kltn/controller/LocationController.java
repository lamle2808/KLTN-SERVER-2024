package com.example.kltn.controller;

import com.example.kltn.entity.Location;
import com.example.kltn.entity.ImageLocation;
import com.example.kltn.service.LocationService;
import com.example.kltn.service.ImageLocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/locations")
public class LocationController {
    
    private final LocationService locationService;
    private final ImageLocationService imageLocationService;
    
    @Autowired
    public LocationController(LocationService locationService, ImageLocationService imageLocationService) {
        this.locationService = locationService;
        this.imageLocationService = imageLocationService;
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
    
    @PostMapping(value = "/upload-images/{locationId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadLocationImages(
            @RequestParam("files") MultipartFile[] files,
            @PathVariable Long locationId) {
        try {
            if (files.length == 0) {
                return ResponseEntity.badRequest()
                    .body(Map.of("error", "Không có file nào được chọn"));
            }

            List<ImageLocation> uploadedImages = new ArrayList<>();
            for (MultipartFile file : files) {
                if (!file.isEmpty()) {
                    ImageLocation imageLocation = imageLocationService.uploadImageForLocation(file, locationId);
                    uploadedImages.add(imageLocation);
                }
            }

            return ResponseEntity.ok(uploadedImages);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body(Map.of("error", "Lỗi khi upload ảnh: " + e.getMessage()));
        }
    }
    
    // Các phương thức khác của controller
} 