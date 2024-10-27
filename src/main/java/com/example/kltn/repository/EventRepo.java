package com.example.kltn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.kltn.entity.Event;

@Repository
public interface EventRepo extends JpaRepository<Event, Long>{

}
