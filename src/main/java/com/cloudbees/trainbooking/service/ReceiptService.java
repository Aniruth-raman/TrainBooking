package com.cloudbees.trainbooking.service;

import com.cloudbees.trainbooking.model.Receipt;
import com.cloudbees.trainbooking.model.Seat;
import com.cloudbees.trainbooking.model.User;
import com.cloudbees.trainbooking.repository.ReceiptRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ReceiptService {

    private final ReceiptRepository receiptRepository;

    public ReceiptService(ReceiptRepository receiptRepository) {
        this.receiptRepository = receiptRepository;
    }


    public Receipt getReceiptById(Long id) {
        return receiptRepository.findById(id).orElse(null);
    }

    public Map<User, Seat> getUsersBySeats(List<Seat> seats) {
        Map<User, Seat> userSeatMap = new HashMap<>();

        // Query receipts associated with the given list of seats
        List<Receipt> receipts = receiptRepository.findBySeatIn(seats);

        // Extract user and seat information from the receipts
        for (Receipt receipt : receipts) {
            User user = receipt.getUser();
            Seat seat = receipt.getSeat();
            if (user != null && seat != null) {
                userSeatMap.put(user, seat);
            }
        }

        return userSeatMap;
    }


    public Optional<Receipt> findByUser(Long id) {
        return receiptRepository.findByUser_Id(id);
    }

    public void deleteReceipt(Receipt receipt) {
        receiptRepository.delete(receipt);
    }
}
