package com.example.kltn.controller;

import com.example.kltn.entity.Location;
import com.example.kltn.entity.Account;
import com.example.kltn.config.JwtService;
import com.example.kltn.service.LocationService;
import com.example.kltn.repository.AccountRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/locations")
@RequiredArgsConstructor
public class LocationController {

    private final JwtService jwtService;
    @Autowired
    private LocationService locationService;
    private final AccountRepo accountRepo;

    @GetMapping
    public ResponseEntity<?> getAllLocations() {
        try {
            return ResponseEntity.ok(locationService.getAll());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                               .body("Có lỗi xảy ra: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getLocationById(@PathVariable Long id) {
        try {
            Location location = locationService.getById(id);
            return ResponseEntity.ok(location);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi: " + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> createLocation(
            @RequestHeader("Authorization") String token,
            @RequestBody Location location) {
        try {
            String email = jwtService.extractUsername(token.substring(7));
            Account author = accountRepo.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy tài khoản"));

            location.setAuthor(author);
            Location savedLocation = locationService.saveOrUpdate(location);

            return ResponseEntity.ok(savedLocation);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateLocation(
            @PathVariable Long id,
            @RequestBody Location location) {
        try {
            Location existingLocation = locationService.getById(id);
            if (existingLocation == null) {
                return ResponseEntity.notFound().build();
            }
            
            // Giữ nguyên author từ location cũ
            location.setId(id);
            location.setAuthor(existingLocation.getAuthor());
            
            Location updatedLocation = locationService.saveOrUpdate(location);
            return ResponseEntity.ok(updatedLocation);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteLocation(@PathVariable Long id) {
        try {
            locationService.deleteLocation(id);
            return ResponseEntity.ok("Xóa location thành công");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi: " + e.getMessage());
        }
    }
} 