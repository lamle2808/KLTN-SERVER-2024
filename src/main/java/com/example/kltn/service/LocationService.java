package com.example.kltn.service;

import com.example.kltn.entity.Location;
import java.util.List;

public interface LocationService {
    Location saveLocation(Location location, Long authorId);
    Location getById(Long id);
    List<Location> getAll();
    void deleteLocation(Long id);
    Location updateLocation(Location location);
    // Thêm các phương thức khác nếu cần
}