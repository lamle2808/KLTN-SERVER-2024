package com.example.kltn.service.implement;

import com.example.kltn.entity.Location;
import com.example.kltn.entity.Event;
import com.example.kltn.repository.LocationRepo;
import com.example.kltn.repository.EventRepo;
import com.example.kltn.service.LocationService;
import com.example.kltn.service.EventService;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.annotation.SessionScope;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
@SessionScope
public class LocationServiceImpl implements LocationService {
    
    private final LocationRepo locationRepo;
    private final EventService eventService;

    @Override
    public Location saveOrUpdate(Location location) {
        return locationRepo.save(location);
    }

    @Override
    public Location getById(Long id) {
        return locationRepo.findById(id)
            .orElseThrow(() -> new RuntimeException("Không tìm thấy location"));
    }

    @Override
    public Location addEventToLocation(Long locationId, Event event) {
        Location location = getById(locationId);
        event.setEventLocation(location);
        location.getEvents().add(event);
        return locationRepo.save(location);
    }

    @Override
    public Location removeEventFromLocation(Long locationId, Long eventId) {
        Location location = getById(locationId);
        location.getEvents().removeIf(event -> event.getId().equals(eventId));
        return locationRepo.save(location);
    }

    @Override
    public List<Location> getAll() {
        return locationRepo.findAll();
    }

    @Override
    public void deleteLocation(Long id) {
        Location location = getById(id);
        locationRepo.delete(location);
    }
} 