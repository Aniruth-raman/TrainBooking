package com.cloudbees.trainbooking.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "seats")
@NoArgsConstructor
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Section section;

    @Column(nullable = false)
    private String seatNumber;

    @Column(nullable = false)
    private Boolean available = Boolean.TRUE;

    public Seat(Section section, String seatNumber) {
        this.section = section;
        this.seatNumber = seatNumber;
    }
}