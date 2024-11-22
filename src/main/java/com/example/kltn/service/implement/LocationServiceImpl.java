package com.example.kltn.service.implement;

import com.example.kltn.entity.Location;
import com.example.kltn.repository.LocationRepo;
import com.example.kltn.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {

    private final LocationRepo locationRepo;

    @Override
    public Location saveOrUpdate(Location location) {
        return locationRepo.save(location);
    }

    @Override
    public List<Location> getAll() {
        return locationRepo.findAll();
    }

    @Override
    public Location getById(Long id) {
        return locationRepo.findById(id).orElse(null);
    }

    @Override
    public void deleteLocation(Long id) {
        locationRepo.deleteById(id);
    }
} 