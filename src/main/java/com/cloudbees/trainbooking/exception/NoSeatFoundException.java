package com.cloudbees.trainbooking.exception;

public class NoSeatFoundException extends RuntimeException {
    public NoSeatFoundException(String errorMessage) {
        super(errorMessage);
    }
}
