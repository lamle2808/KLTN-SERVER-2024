package com.example.kltn.service.implement;

import com.example.kltn.entity.Event;
import com.example.kltn.repository.EventRepo;
import com.example.kltn.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepo eventRepo;

    @Override
    public Event saveOrUpdate(Event event) {
        return eventRepo.save(event);
    }

    @Override
    public List<Event> getAll() {
        return eventRepo.findAll();
    }

    @Override
    public Event getById(Long id) {
        return eventRepo.findById(id).orElse(null);
    }

    @Override
    public void deleteEvent(Long id) {
        eventRepo.deleteById(id);
    }
} 