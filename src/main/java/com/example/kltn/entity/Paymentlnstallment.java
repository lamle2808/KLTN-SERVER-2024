package com.example.kltn.entity;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "paymentlnstallments")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Paymentlnstallment implements Serializable{

    @Id
    private Long id;
    private double amount;
    private Date dueDate;
    private Date paidDate;
    private String status;
    @ManyToOne
    @JoinColumn(name = "payment_id")
    private Payment payment;
}
