package com.cloudbees.trainbooking.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "receipts")
public class Receipt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "\"from\"")
    private String from;

    @Column(nullable = false, name = "\"to\"")
    private String to;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private BigDecimal pricePaid;

    @ManyToOne
    @JoinColumn(name = "seat_id")
    private Seat seat;

}