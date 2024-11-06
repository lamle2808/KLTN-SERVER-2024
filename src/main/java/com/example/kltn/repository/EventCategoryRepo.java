package com.example.kltn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.kltn.entity.EventCategory;

@Repository
public interface EventCategoryRepo extends JpaRepository<EventCategory, Long>{

    
}
