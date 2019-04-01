package com.thoughworks.capability;

import com.thoughworks.capability.Exception.InvalidTicketException;

import java.util.ArrayList;
import java.util.List;

public abstract class ParkingBoy {
    protected List<ParkingLot> parkingLots;

    public ParkingBoy(List<ParkingLot> parkingLot) {
        parkingLots = parkingLot;
    }

    public abstract Ticket parking(Car car) throws Exception;

    public abstract List<Ticket> parking(List<Car> cars) throws Exception;

    public Car getCar(Ticket ticket) throws Exception {
        Car car = null;

        for (ParkingLot parkingLot : parkingLots) {
            try {
                car = parkingLot.getCar(ticket);
            }catch (Exception ex){

            }
        }

        parkingLots.forEach(item -> {

        });

        if(car == null){
            throw  new InvalidTicketException("Invalid ticket!");
        }

        return car;
    }

    public List<Car> getCars(List<Ticket> tickets) throws Exception {
        ArrayList<Car> cars = new ArrayList<>();
        for (Ticket ticket: tickets) {
            cars.add(getCar(ticket));
        }
        return cars;
    }
}
