package com.thoughworks.capability;

import com.thoughworks.capability.Exception.InvalidTicketException;
import com.thoughworks.capability.Exception.ParkingIsFullException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ParkingLot {
    private String id;
    private int capacity;
    private List<Car> storedCars = new ArrayList<>();
    private List<Ticket> tickets = new ArrayList<>();

    public ParkingLot(String id, int capacity) {
        this.id = id;
        this.capacity = capacity;
    }

    public Ticket parking(Car car) throws Exception {
        if (getCapacity() <= 0) {
            throw new ParkingIsFullException("Parking lot is full");
        }

        storedCars.add(car);

        Ticket ticket =  new Ticket(id, car);

        tickets.add(ticket);

        return ticket;
    }

    public List<Ticket> parking(List<Car> cars) throws Exception {
        if (getCapacity() < cars.size()) {
            throw new ParkingIsFullException("Parking lot is full");
        }
        return cars.stream()
                .map(it -> {
                    try {
                        return parking(it);
                    } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                    }
                })
                .collect(Collectors.toList());
    }

    public Car getCar(Ticket ticket) throws Exception {
        if(!tickets.contains(ticket)){
            throw new InvalidTicketException("Ticket is invalid");
        }

        Car car =  storedCars.stream().filter(it -> it.getId().equals(ticket.getCar().getId())).findFirst().orElseThrow(() -> new InvalidTicketException("Ticket is invalid"));
        storedCars.remove(car);
        tickets.remove(ticket);

        return car;
    }

    public List<Car> getCars(List<Ticket> tickets) throws Exception {
        ArrayList<Car> cars = new ArrayList<>();
        for (Ticket ticket : tickets) {
            Car car = getCar(ticket);
            cars.add(car);
        }
        return cars;
    }

    public String getId() {
        return id;
    }

    public int getCapacity(){
        return capacity - storedCars.size();
    }


}
