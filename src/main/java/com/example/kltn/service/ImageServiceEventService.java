package com.example.kltn.service;

import com.example.kltn.entity.ImageService;
import java.util.List;

public interface ImageServiceEventService {
    ImageService save(ImageService imageService);
    List<ImageService> findByServiceEventId(Long serviceEventId);
    void deleteById(Long id);
} 