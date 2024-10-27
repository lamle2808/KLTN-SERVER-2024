package com.example.kltn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.kltn.entity.Order;

@Repository
public interface OderRepo extends JpaRepository<Order, Long> {

}
