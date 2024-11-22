package com.example.kltn.entity;

import java.io.Serializable;
import java.util.*;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "locations")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Location implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToMany(mappedBy = "locations", cascade = CascadeType.ALL)
    private Set<ServiceEvent> services = new HashSet<>();
    private String venueName;
    private String location;
    private Double latitude;
    private Double longitude;
    private String capacity;
    private double price;
    private String image;
    private String address;
    private String description;
    private String status;
    @OneToMany(mappedBy = "location")
    private List<ImageLocation> imageLocation;
}
