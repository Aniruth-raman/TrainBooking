package com.cloudbees.trainbooking.exception;

public class ConflictException extends Exception {
    public ConflictException(String seatAlreadyExists) {
        super(seatAlreadyExists);
    }
}
