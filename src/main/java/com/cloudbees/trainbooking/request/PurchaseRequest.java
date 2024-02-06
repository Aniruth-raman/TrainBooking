package com.cloudbees.trainbooking.request;

import com.cloudbees.trainbooking.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class PurchaseRequest {
    private User user;
    private String from;
    private String to;
    private BigDecimal cost;

}
