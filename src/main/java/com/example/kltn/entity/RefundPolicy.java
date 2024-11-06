package com.example.kltn.entity;

import java.io.Serializable;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "refundPolicys")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RefundPolicy implements Serializable{
    @Id
    private Long id;
    private String name;
    private String description;
    private int cancelBeforeDays;
    private double refundPercentage;
    private String status;
    @OneToOne(mappedBy = "refundPolicy", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Event event;
}
