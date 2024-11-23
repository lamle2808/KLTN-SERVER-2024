package com.example.kltn.service;

import com.example.kltn.entity.ImageEvent;
import java.util.List;

public interface ImageEventService {
    ImageEvent save(ImageEvent imageEvent);
    void delete(Long id);
    ImageEvent getById(Long id);
    List<ImageEvent> getAllByEventId(Long eventId);
} 