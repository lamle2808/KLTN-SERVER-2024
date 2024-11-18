package com.example.kltn.service;

import com.example.kltn.entity.Location;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface LocationService {
    Location saveOrUpdate(Location location);
    Location getById(Long id);
    void deleteLocation(Long id);
    List<Location> getAll();
}