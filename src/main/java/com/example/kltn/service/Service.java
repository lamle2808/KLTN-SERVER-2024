package com.example.kltn.service;

import com.example.kltn.entity.ServiceEvent;
import java.util.*;

public interface Service {
    ServiceEvent saveOrUpdate(ServiceEvent service);

    List<ServiceEvent> getAll();

    ServiceEvent getById(Long id);

    void deleteService(Long id);

    List<ServiceEvent> getByName(String name);
}
