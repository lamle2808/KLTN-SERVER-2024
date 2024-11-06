package com.example.kltn.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "imageEvent")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ImageEvent extends Image {
    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;
}
