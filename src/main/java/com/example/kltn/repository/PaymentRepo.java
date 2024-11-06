package com.example.kltn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.kltn.entity.Payment;

@Repository
public interface PaymentRepo extends JpaRepository<Payment, Long> {

}
