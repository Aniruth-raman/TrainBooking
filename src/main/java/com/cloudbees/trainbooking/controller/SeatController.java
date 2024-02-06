package com.cloudbees.trainbooking.controller;

import com.cloudbees.trainbooking.exception.ResourceNotFoundException;
import com.cloudbees.trainbooking.model.Receipt;
import com.cloudbees.trainbooking.model.Seat;
import com.cloudbees.trainbooking.model.Section;
import com.cloudbees.trainbooking.model.User;
import com.cloudbees.trainbooking.request.SeatRequest;
import com.cloudbees.trainbooking.service.ReceiptService;
import com.cloudbees.trainbooking.service.SeatService;
import com.cloudbees.trainbooking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/seats")
public class SeatController {

    private final SeatService seatService;
    private final ReceiptService receiptService;
    private final UserService userService;

    @Autowired
    public SeatController(SeatService seatService, ReceiptService receiptService, UserService userService) {
        this.seatService = seatService;
        this.receiptService = receiptService;
        this.userService = userService;
    }


    @PostMapping
    public ResponseEntity<Seat> addSeat(@RequestBody SeatRequest request) {
        Seat seat = seatService.createSeat(request.getSection(), request.getSeatNumber());
        return ResponseEntity.ok(seat);
    }

    @GetMapping("/{section}")
    public ResponseEntity<Map<User, Seat>> getUsersandSeatsBySection(@PathVariable Section section) {
        List<Seat> seats = seatService.getSeatsBySection(section);
        Map<User, Seat> userSeatMap = receiptService.getUsersBySeats(seats);
        return ResponseEntity.ok(userSeatMap);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeUserFromSeat(@PathVariable Long id) {
        Receipt receipt = receiptService.findByUser(id).orElse(null);

        if (receipt != null) {
            Seat seat = receipt.getSeat();
            if (seat != null) {
                seat.setAvailable(true);
                seatService.updateSeat(seat);
            }
            receiptService.deleteReceipt(receipt);
            userService.deleteUser(id);
            return ResponseEntity.ok().build();
        } else {
            throw new ResourceNotFoundException("Given User not found");
        }
    }


    @PutMapping("/{userId}")
    public ResponseEntity<Seat> modifyUserSeat(@PathVariable Long userId, @RequestBody Section section) {
        Seat updatedSeat = seatService.modifySeat(userId, section);
        return ResponseEntity.ok(updatedSeat);
    }
}
