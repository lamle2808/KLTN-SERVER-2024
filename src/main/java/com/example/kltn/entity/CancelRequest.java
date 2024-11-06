package com.example.kltn.entity;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "cancelRequests")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CancelRequest {

    @Id
    private Long id;
    private String reasion;
    private Date requestDate;
    private String status;
    private double refundAmount;
    private Date processedDate;
    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;
}
