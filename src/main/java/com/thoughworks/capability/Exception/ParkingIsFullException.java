package com.thoughworks.capability.Exception;

public class ParkingIsFullException extends RuntimeException {

    public ParkingIsFullException(String message) {
        super(message);
    }
}
