package com.cloudbees.trainbooking.controller;

import com.cloudbees.trainbooking.model.Receipt;
import com.cloudbees.trainbooking.service.ReceiptService;
import com.cloudbees.trainbooking.service.SeatService;
import com.cloudbees.trainbooking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/receipts")
public class ReceiptController {

    private final ReceiptService receiptService;

    @Autowired
    public ReceiptController(SeatService seatService, ReceiptService receiptService, UserService userService) {
        this.receiptService = receiptService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Receipt> getReceiptDetails(@PathVariable Long id) {
        Receipt receipt = receiptService.getReceiptById(id);
        if (receipt != null) {
            return ResponseEntity.ok(receipt);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
