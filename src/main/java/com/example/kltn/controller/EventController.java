package com.example.kltn.controller;

import com.example.kltn.entity.ImageEvent;
import com.example.kltn.service.CloudinaryService;
import com.example.kltn.service.ImageEventService;
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
import java.util.Map;

@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
public class EventController {

    private final CloudinaryService cloudinaryService;
    private final ImageEventService imageEventService;
    
    @PostMapping(value = "/upload-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadEventImage(@RequestParam("file") MultipartFile file) {
        try {
            Map result = cloudinaryService.upload(file);
            ImageEvent imageEvent = new ImageEvent();
            imageEvent.setImageLink((String) result.get("url"));
            imageEvent.setIdCloud((String) result.get("public_id"));
            // ... set các field khác
            return ResponseEntity.ok().body(imageEventService.save(imageEvent));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Upload failed: " + e.getMessage());
        }
    }
} 