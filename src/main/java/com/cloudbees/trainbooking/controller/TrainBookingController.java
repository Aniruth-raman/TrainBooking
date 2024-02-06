package com.cloudbees.trainbooking.controller;

import com.cloudbees.trainbooking.model.Receipt;
import com.cloudbees.trainbooking.request.PurchaseRequest;
import com.cloudbees.trainbooking.service.PurchaseService;
import com.cloudbees.trainbooking.service.ReceiptService;
import com.cloudbees.trainbooking.service.SeatService;
import com.cloudbees.trainbooking.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tickets")
public class TrainBookingController {

    private final PurchaseService purchaseService;
    private final ReceiptService receiptService;
    private final SeatService seatService;
    private final UserService userService;

    @Autowired
    public TrainBookingController(PurchaseService purchaseService, ReceiptService receiptService, SeatService seatService, UserService userService) {
        this.purchaseService = purchaseService;
        this.receiptService = receiptService;
        this.seatService = seatService;
        this.userService = userService;
    }

    @PostMapping("/purchase")
    public ResponseEntity<Receipt> purchaseTicket(@Valid @RequestBody PurchaseRequest request) {
        try {
            Receipt receipt = purchaseService.purchaseTicket(request.getUser(), request.getFrom(), request.getTo(), request.getCost());
            return ResponseEntity.ok(receipt);
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }
}