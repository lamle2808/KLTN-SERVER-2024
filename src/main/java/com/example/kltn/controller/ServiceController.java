package com.example.kltn.controller;

import com.example.kltn.entity.ServiceEvent;
import com.example.kltn.service.ServiceEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/services")
@RequiredArgsConstructor
public class ServiceController {

    private final ServiceEventService serviceEventService;

    @GetMapping
    public ResponseEntity<List<ServiceEvent>> getAllServices() {
        List<ServiceEvent> services = serviceEventService.getAll();
        return ResponseEntity.ok(services);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceEvent> getServiceById(@PathVariable Long id) {
        ServiceEvent service = serviceEventService.getById(id);
        return ResponseEntity.ok(service);
    }

    @PostMapping
    public ResponseEntity<ServiceEvent> createService(@RequestBody ServiceEvent serviceEvent) {
        ServiceEvent createdService = serviceEventService.saveOrUpdate(serviceEvent);
        return ResponseEntity.ok(createdService);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServiceEvent> updateService(@PathVariable Long id, @RequestBody ServiceEvent serviceEvent) {
        serviceEvent.setId(id);
        ServiceEvent updatedService = serviceEventService.saveOrUpdate(serviceEvent);
        return ResponseEntity.ok(updatedService);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteService(@PathVariable Long id) {
        serviceEventService.deleteService(id);
        return ResponseEntity.noContent().build();
    }
} 