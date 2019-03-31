package com.thoughworks.capability;

import java.util.Random;

public class Ticket {
    private String id;
    private String parkingLotId;
    private String carId;

    public Ticket(String parkingLot, String carId) {
        this.id = "ticket-" + carId;
        this.parkingLotId = parkingLot;
        this.carId = carId;
    }

    public String getId() {
        return id;
    }

    public String getParkingLotId() {
        return parkingLotId;
    }

    public String getCarId() {
        return carId;
    }

}
