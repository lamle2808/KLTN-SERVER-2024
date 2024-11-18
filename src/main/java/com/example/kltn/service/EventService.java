package com.example.kltn.service;

import com.example.kltn.entity.Event;
import java.util.List;

public interface EventService {
    Event saveOrUpdate(Event event);
    List<Event> getAll();
    Event getById(Long id);
    void deleteEvent(Long id);
} 