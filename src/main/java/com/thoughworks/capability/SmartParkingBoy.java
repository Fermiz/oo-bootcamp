package com.thoughworks.capability;

import java.util.ArrayList;
import java.util.List;

public class SmartParkingBoy extends ParkingBoy {

    public SmartParkingBoy(List<ParkingLot> parkingLot) {
        super(parkingLot);
    }

    public Ticket parking(Car car) throws Exception {
        parkingLots.sort((a, b) -> Integer.compare(b.getCapacity(), a.getCapacity()));
        return parkingLots.get(0).parking(car);
    }

    public List<Ticket> parking(List<Car> cars) throws Exception {
        ArrayList<Ticket> tickets = new ArrayList<>();
        for (Car car : cars) {
            tickets.add(parking(car));
        }
        return tickets;
    }
}
