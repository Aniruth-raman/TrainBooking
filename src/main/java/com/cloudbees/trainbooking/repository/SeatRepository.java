package com.cloudbees.trainbooking.repository;

import com.cloudbees.trainbooking.model.Seat;
import com.cloudbees.trainbooking.model.Section;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SeatRepository extends JpaRepository<Seat, Long> {
    List<Seat> findBySection(Section section);

    Seat findFirstByAvailableIsTrue();

    boolean existsBySectionAndSeatNumber(Section section, String seatNumber);

    Seat findFirstBySectionAndAvailableIsTrue(Section section);
}
