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

@RestController
@RequestMapping("/api/v1/locations")
@RequiredArgsConstructor
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

    @PostMapping(value = "/upload-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadLocationImage(@RequestParam("file") MultipartFile file) {
        try {
            // Kiểm tra file
            BufferedImage bi = ImageIO.read(file.getInputStream());
            if (bi == null) {
                return ResponseEntity.badRequest().body("Error: Invalid image file");
            }

            // Upload lên Cloudinary
            Map result = cloudinaryService.upload(file);
            
            // Tạo ImageLocation
            ImageLocation imageLocation = new ImageLocation();
            imageLocation.setImageLink((String) result.get("url"));
            imageLocation.setIdCloud((String) result.get("public_id"));
            
            // Set thời gian
            DateTimeFormatter formatter = DateTimeFormatter.ISO_INSTANT;
            Instant instant = Instant.from(formatter.parse((String) result.get("created_at")));
            imageLocation.setDate(Date.from(instant));
            
            // Set type và size
            imageLocation.setType((String) result.get("format"));
            int bytes = (int) result.get("bytes");
            double size = (double) bytes / 1024;
            imageLocation.setSize(String.format("%.3f KB", size));
            
            // Lưu và trả về kết quả
            return ResponseEntity.ok().body(imageLocationService.save(imageLocation));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Upload failed: " + e.getMessage());
        }
    }
} 