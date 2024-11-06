package com.example.kltn.service.implement;

import java.util.List;

import org.springframework.web.context.annotation.SessionScope;

import com.example.kltn.entity.ServiceEvent;
import com.example.kltn.repository.ServiceRepo;
import com.example.kltn.service.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
@Transactional
@Slf4j
@SessionScope
public class ServiceImpl implements Service{

    private final ServiceRepo serviceRepo;

    @Override
    public ServiceEvent saveOrUpdate(ServiceEvent service) {
        return serviceRepo.save(service);
    }

    @Override
    public List<ServiceEvent> getAll() {
        return serviceRepo.findAll();
    }

    @Override
    public ServiceEvent getById(Long id) {
        return serviceRepo.findServiceEventById(id);
    }

    @Override
    public void deleteService(Long id) {
        serviceRepo.delete(serviceRepo.findServiceEventById(id));
    }

    @Override
    public List<ServiceEvent> getByName(String name) {
        return serviceRepo.findServiceEventByServiceName(name);
    }

}
