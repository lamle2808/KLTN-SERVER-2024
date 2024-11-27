package com.example.kltn.service.implement;

import com.example.kltn.entity.Location;
import com.example.kltn.entity.Employee;
import com.example.kltn.repository.LocationRepo;
import com.example.kltn.repository.EmployeeRepo;
import com.example.kltn.service.LocationService;
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
    private final EmployeeRepo employeeRepo;

    @Override
    public Location saveLocation(Location location, Long authorId) {
        Employee author = employeeRepo.findById(authorId)
            .orElseThrow(() -> new RuntimeException("Không tìm thấy nhân viên với ID: " + authorId));
        location.setAuthor(author.getAccount());
        return locationRepo.save(location);
    }

    @Override
    public Location getById(Long id) {
        return locationRepo.findById(id)
            .orElseThrow(() -> new RuntimeException("Không tìm thấy địa điểm với ID: " + id));
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

    @Override
    public Location updateLocation(Location location) {
        // Kiểm tra xem location có tồn tại không
        if (location.getId() == null || !locationRepo.existsById(location.getId())) {
            throw new RuntimeException("Không tìm thấy địa điểm để cập nhật");
        }
        return locationRepo.save(location);
    }
} 