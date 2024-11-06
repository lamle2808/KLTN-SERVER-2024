package com.example.kltn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.kltn.entity.Location;

@Repository
public interface LocationRepo extends JpaRepository<Location, Long>{

}
