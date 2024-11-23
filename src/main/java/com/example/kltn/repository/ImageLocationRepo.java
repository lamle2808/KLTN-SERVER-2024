package com.example.kltn.repository;

import com.example.kltn.entity.ImageLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageLocationRepo extends JpaRepository<ImageLocation, Long> {
} 