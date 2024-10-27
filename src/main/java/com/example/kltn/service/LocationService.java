package com.example.kltn.service;

import com.example.kltn.entity.Location;

public interface LocationService {
    Location saveOrUpdate(Location location);
    Location updatePrice(Long id, double priceNew);
}