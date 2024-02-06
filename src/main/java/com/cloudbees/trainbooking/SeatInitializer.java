package com.cloudbees.trainbooking;

import com.cloudbees.trainbooking.model.Seat;
import com.cloudbees.trainbooking.model.Section;
import com.cloudbees.trainbooking.repository.SeatRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SeatInitializer {

    private final SeatRepository seatRepository;

    @Autowired
    public SeatInitializer(SeatRepository seatRepository) {
        this.seatRepository = seatRepository;
    }

    @PostConstruct
    public void initializeSeats() {
        if (seatRepository.count() == 0) {
            List<Seat> seats = new ArrayList<>();
            for (int i = 1; i <= 10; i++) {
                seats.add(new Seat(Section.A, String.valueOf(i)));
                seats.add(new Seat(Section.B, String.valueOf(i)));
            }
            seatRepository.saveAll(seats);
        }
    }
}