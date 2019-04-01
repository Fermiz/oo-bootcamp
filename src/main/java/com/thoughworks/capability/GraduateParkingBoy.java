package com.thoughworks.capability;

import com.thoughworks.capability.Exception.ParkingIsFullException;

import java.util.ArrayList;
import java.util.List;

public class GraduateParkingBoy extends ParkingBoy {

    public GraduateParkingBoy(List<ParkingLot> parkingLot) {
        super(parkingLot);
    }

    public Ticket parking(Car car) throws Exception {
        Ticket ticket = null;

        int totalCapacity = this.parkingLots.stream().map(it -> it.getCapacity()).reduce((acc, cur) -> acc + cur).get();

        if (totalCapacity <= 0) {
            throw new ParkingIsFullException("All parking are gull");
        }

        for (ParkingLot parkingLot : parkingLots) {
            if (parkingLot.getCapacity() > 0) {
                ticket = parkingLot.parking(car);
                break;
            }
        }

        return ticket;
    }

    public List<Ticket> parking(List<Car> cars) throws Exception {
        ArrayList<Ticket> tickets = new ArrayList<>();

        int totalCapacity = parkingLots.stream().map(it -> it.getCapacity()).reduce((acc, cur) -> acc + cur).get();

        if (totalCapacity < cars.size()) {
            throw new ParkingIsFullException("All parking are gull");
        }

        List<Car> remainCars = new ArrayList<>(cars);
        for (ParkingLot parkingLot : parkingLots) {
            if (parkingLot.getCapacity() >= remainCars.size()) {
                tickets.addAll(parkingLot.parking(remainCars));
                remainCars.clear();
            } else {
                int capacity = parkingLot.getCapacity();
                if(capacity == 0){
                    continue;
                }
                List<Car> parkedCars = remainCars.subList(0, capacity);
                tickets.addAll(parkingLot.parking(parkedCars));
                remainCars = remainCars.subList(capacity, remainCars.size());
            }
        }

        return tickets;
    }
}
