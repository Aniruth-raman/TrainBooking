package com.cloudbees.trainbooking.service;

import com.cloudbees.trainbooking.exception.NoSeatFoundException;
import com.cloudbees.trainbooking.model.Receipt;
import com.cloudbees.trainbooking.model.Seat;
import com.cloudbees.trainbooking.model.User;
import com.cloudbees.trainbooking.repository.ReceiptRepository;
import com.cloudbees.trainbooking.repository.SeatRepository;
import com.cloudbees.trainbooking.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PurchaseService {

    private final ReceiptRepository receiptRepository;
    private final UserRepository userRepository;
    private final SeatRepository seatRepository;

    @Autowired
    public PurchaseService(ReceiptRepository receiptRepository, UserRepository userRepository, SeatRepository seatRepository) {
        this.receiptRepository = receiptRepository;
        this.userRepository = userRepository;
        this.seatRepository = seatRepository;
    }

    public Receipt purchaseTicket(User user, String from, String to, BigDecimal cost) {
        Seat availableSeat = findAvailableSeat();
        if (availableSeat == null) {
            throw new NoSeatFoundException("No seats available");
        }
        Receipt receipt = new Receipt();
        receipt.setFrom(from);
        receipt.setTo(to);
        receipt.setUser(user);
        receipt.setPricePaid(cost);
        receipt.setSeat(availableSeat);
        userRepository.save(user);
        availableSeat.setAvailable(Boolean.FALSE);
        seatRepository.save(availableSeat);
        receiptRepository.save(receipt);
        return receipt;
    }

    private Seat findAvailableSeat() {
        return seatRepository.findFirstByAvailableIsTrue();
    }
}