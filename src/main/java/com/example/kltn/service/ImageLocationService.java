package com.example.kltn.service;

import com.example.kltn.entity.ImageLocation;

public interface ImageLocationService {
    ImageLocation save(ImageLocation imageLocation);
    void delete(Long id);
    ImageLocation getById(Long id);
} 