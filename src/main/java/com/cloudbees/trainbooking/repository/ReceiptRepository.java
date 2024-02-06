package com.cloudbees.trainbooking.repository;

import com.cloudbees.trainbooking.model.Receipt;
import com.cloudbees.trainbooking.model.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReceiptRepository extends JpaRepository<Receipt, Long> {
    List<Receipt> findBySeatIn(List<Seat> seats);

    Optional<Receipt> findByUser_Id(Long userId);
}
