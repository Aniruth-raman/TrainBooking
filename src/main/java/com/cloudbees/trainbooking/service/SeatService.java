package com.cloudbees.trainbooking.service;

import com.cloudbees.trainbooking.exception.ConflictException;
import com.cloudbees.trainbooking.model.Receipt;
import com.cloudbees.trainbooking.model.Seat;
import com.cloudbees.trainbooking.model.Section;
import com.cloudbees.trainbooking.repository.ReceiptRepository;
import com.cloudbees.trainbooking.repository.SeatRepository;
import lombok.SneakyThrows;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SeatService {
    private final SeatRepository seatRepository;
    private final ReceiptRepository receiptRepository;

    @Autowired
    public SeatService(SeatRepository seatRepository, ReceiptRepository receiptRepository) {
        this.seatRepository = seatRepository;
        this.receiptRepository = receiptRepository;
    }


    public List<Seat> getSeatsBySection(Section section) {
        return seatRepository.findBySection(section);
    }


    public Seat modifySeat(Long userId, Section section) {
        Receipt receipt = receiptRepository.findByUser_Id(userId).orElseThrow(() -> new RuntimeException("Receipt not found for user ID: " + userId));

        // Find the current seat
        Seat currentSeat = receipt.getSeat();

        // Find the next available seat in the requested section
        Seat nextAvailableSeat = findNextAvailableSeat(section);

        if (nextAvailableSeat != null) {
            // Update the receipt with the new seat
            receipt.setSeat(nextAvailableSeat);

            // Set the current seat as available
            currentSeat.setAvailable(true);
            seatRepository.save(currentSeat);

            // Mark the next available seat as unavailable
            nextAvailableSeat.setAvailable(false);
            seatRepository.save(nextAvailableSeat);

            // Save the updated receipt
            receiptRepository.save(receipt);

            return nextAvailableSeat;
        } else {
            return null;
        }
    }

    private Seat findNextAvailableSeat(Section section) {
        return seatRepository.findFirstBySectionAndAvailableIsTrue(section);
    }

    @SneakyThrows
    public Seat createSeat(Section section, String seatNumber) {
        // Validate section and seat number (example using String.format):
        String errorMessage = validateSeatRequest(section, seatNumber);
        if (errorMessage != null) {
            throw new BadRequestException(errorMessage);
        }

        // Check if seat already exists:
        if (seatRepository.existsBySectionAndSeatNumber(section, seatNumber)) {
            throw new ConflictException("Seat already exists");
        }

        // Create and persist a new Seat entity:
        Seat seat = new Seat(section, seatNumber);
        seatRepository.save(seat);
        return seat;
    }

    private String validateSeatRequest(Section section, String seatNumber) {
        if (section == null) {
            return "Section is required";
        }
        if (seatNumber == null || seatNumber.isBlank()) {
            return "Seat number is required";
        }
        return null;
    }

    public Seat getSeatById(Long id) {
        Optional<Seat> availableSeat = seatRepository.findById(id);
        return availableSeat.orElse(null);
    }

    public void updateSeat(Seat seat) {
        seatRepository.save(seat);
    }
}
