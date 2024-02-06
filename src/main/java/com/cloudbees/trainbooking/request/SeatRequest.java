package com.cloudbees.trainbooking.request;

import com.cloudbees.trainbooking.model.Section;
import lombok.Getter;

@Getter
public class SeatRequest {

    private Section section;
    private String seatNumber;
}
