package com.thoughworks.capability;

import java.util.Random;

public class Ticket {
    private String id;
    private String parkingLotId;
    private Car car;

    public Ticket(String parkingLot, Car car) {
        this.id = "ticket-" + car.getId();
        this.parkingLotId = parkingLot;
        this.car = car;
    }

    public String getId() {
        return id;
    }


    public Car getCar() {
        return car;
    }

    public String getParkingLotId() {
        return parkingLotId;
    }
}
