package com.example.kltn.service.implement;

import com.example.kltn.entity.ServiceEvent;
import com.example.kltn.repository.ServiceRepo;
import com.example.kltn.service.ServiceEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ServiceEventServiceImpl implements ServiceEventService {

    private final ServiceRepo serviceRepo;

    @Override
    public ServiceEvent saveOrUpdate(ServiceEvent serviceEvent) {
        return serviceRepo.save(serviceEvent);
    }

    @Override
    public List<ServiceEvent> getAll() {
        return serviceRepo.findAll();
    }

    @Override
    public ServiceEvent getById(Long id) {
        return serviceRepo.findById(id).orElse(null);
    }

    @Override
    public void deleteService(Long id) {
        serviceRepo.deleteById(id);
    }
} 