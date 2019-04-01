package com.thoughworks.capability;

import com.thoughworks.capability.Exception.InvalidTicketException;
import com.thoughworks.capability.Exception.ParkingIsFullException;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class SmartParkingBoyTest {

    @Test
    public void should_return_one_ticket_when_park_one_car() throws Exception {
        ParkingLot parkingLot = new ParkingLot("A1", 1);
        SmartParkingBoy smartParkingBoy = new SmartParkingBoy(Arrays.asList(parkingLot));
        Car car = new Car("0001");
        Ticket ticket = smartParkingBoy.parking(car);

        assertNotNull(ticket);
    }

    @Test
    public void should_park_in_the_parking_lot_with_more_capacity_when_first_parking_lot_has_more_lots() throws Exception {
        ParkingLot parkingLot1 = new ParkingLot("A1", 2);
        ParkingLot parkingLot2 = new ParkingLot("B1", 1);
        SmartParkingBoy smartParkingBoy = new SmartParkingBoy(Arrays.asList(parkingLot1, parkingLot2));
        Car car = new Car("0001");
        Ticket ticket = smartParkingBoy.parking(car);

        assertEquals("A1", ticket.getParkingLotId());
    }

    @Test
    public void should_park_in_the_parking_lot_with_more_capacity_when_second_parking_lot_has_more_lots() throws Exception {
        ParkingLot parkingLot1 = new ParkingLot("A1", 1);
        ParkingLot parkingLot2 = new ParkingLot("B1", 2);
        SmartParkingBoy smartParkingBoy = new SmartParkingBoy(Arrays.asList(parkingLot1, parkingLot2));
        Car car = new Car("0001");
        Ticket ticket = smartParkingBoy.parking(car);

        assertEquals("B1", ticket.getParkingLotId());
    }

    @Test
    public void should_park_the_cars_one_by_one_into_the_parking_lots_when_parking_lots_have_same_capacity() throws Exception {
        ParkingLot parkingLot1 = new ParkingLot("A1", 2);
        ParkingLot parkingLot2 = new ParkingLot("B1", 2);

        SmartParkingBoy smartParkingBoy = new SmartParkingBoy(Arrays.asList(parkingLot1, parkingLot2));
        Car car1 = new Car("0001");
        Car car2 = new Car("0002");
        List<Ticket> tickets = smartParkingBoy.parking(Arrays.asList(car1, car2));

        assertEquals("A1", tickets.get(0).getParkingLotId());
        assertEquals("0001", tickets.get(0).getCarId());

        assertEquals("B1", tickets.get(1).getParkingLotId());
        assertEquals("0002", tickets.get(1).getCarId());
    }



    @Test(expected = ParkingIsFullException.class)
    public void should_throw_exception_when_all_parking_lots_are_full() throws Exception {
        ParkingLot parkingLot1 = new ParkingLot("A1", 0);
        ParkingLot parkingLot2 = new ParkingLot("B1", 0);

        SmartParkingBoy smartParkingBoy = new SmartParkingBoy(Arrays.asList(parkingLot1, parkingLot2));
        Car car1 = new Car("0001");
        Car car2 = new Car("0002");
        List<Ticket> tickets = smartParkingBoy.parking(Arrays.asList(car1, car2));

        assertEquals("A1", tickets.get(0).getParkingLotId());
        assertEquals("0001", tickets.get(0).getCarId());

        assertEquals("B1", tickets.get(1).getParkingLotId());
        assertEquals("0002", tickets.get(1).getCarId());
    }

    @Test
    public void should_get_my_car_when_my_car_has_parked_in_parking_lot() throws Exception {
        ParkingBoy parkingBoy = new SmartParkingBoy(Arrays.asList(new ParkingLot("A1", 3), new ParkingLot("A2", 2)));
        var car1 = new Car("00001");
        var car2 = new Car("00002");
        var car3 = new Car("00003");

        List<Ticket> tickets = parkingBoy.parking(Arrays.asList(car1, car2, car3));

        assertEquals("00001", parkingBoy.getCar(tickets.get(0)).getId());
    }

    @Test
    public void should_get_my_cars_when_my_car_has_parked_in_parking_lot() throws Exception {
        ParkingBoy parkingBoy = new SmartParkingBoy(Arrays.asList(new ParkingLot("A1", 3), new ParkingLot("A2", 2)));
        var car1 = new Car("00001");
        var car2 = new Car("00002");
        var car3 = new Car("00003");

        List<Ticket> tickets = parkingBoy.parking(Arrays.asList(car1, car2, car3));

        List<Car> resultCars = parkingBoy.getCars(tickets);

        assertEquals("00001", resultCars.get(0).getId());
        assertEquals("00002", resultCars.get(1).getId());
        assertEquals("00003", resultCars.get(2).getId());
    }

    @Test(expected = InvalidTicketException.class)
    public void should_throw_exception_when_all_parking_lots_can_not_find_car() throws Exception {
        ParkingBoy parkingBoy = new SmartParkingBoy(Arrays.asList(new ParkingLot("A1", 3), new ParkingLot("A2", 2)));
        var car1 = new Car("00001");
        var car2 = new Car("00002");
        var car3 = new Car("00003");

        parkingBoy.parking(Arrays.asList(car1, car2, car3));

        parkingBoy.getCar(new Ticket("A1", "00004")).getId();
    }

    @Test(expected = InvalidTicketException.class)
    public void should_throw_exception_when_get_car_with_used_ticket() throws Exception {
        ParkingBoy parkingBoy = new SmartParkingBoy(Arrays.asList(new ParkingLot("A1", 3), new ParkingLot("A2", 2)));
        var car1 = new Car("00001");

        Ticket ticket = parkingBoy.parking(car1);

        parkingBoy.getCar(ticket);

        parkingBoy.getCar(ticket);
    }

    @Test
    public void should_get_my_cars_when_my_cars_has_parked_in_parking_lots() throws Exception {
        ParkingBoy parkingBoy = new SmartParkingBoy(Arrays.asList(new ParkingLot("A1", 2), new ParkingLot("A2", 3)));
        var car1 = new Car("00001");
        var car2 = new Car("00002");
        var car3 = new Car("00003");

        List<Ticket> tickets = parkingBoy.parking(Arrays.asList(car1, car2, car3));

        List<Car> cars = parkingBoy.getCars(tickets);

        assertEquals(3, cars.size());
        assertEquals("00001", cars.get(0).getId());
        assertEquals("00002", cars.get(1).getId());
        assertEquals("00003", cars.get(2).getId());
    }

    @Test(expected = InvalidTicketException.class)
    public void should_throw_exception_when_all_parking_lots_can_not_find_cars() throws Exception {
        ParkingBoy parkingBoy = new SmartParkingBoy(Arrays.asList(new ParkingLot("A1", 2), new ParkingLot("A2", 3)));
        var car1 = new Car("00001");
        var car2 = new Car("00002");
        var car3 = new Car("00003");

        List<Ticket> tickets = parkingBoy.parking(Arrays.asList(car1, car2, car3));

        parkingBoy.getCars(Arrays.asList(tickets.get(0), new Ticket("A1", "00004")));
    }

    @Test(expected = InvalidTicketException.class)
    public void should_throw_exception_when_get_cars_with_used_ticket() throws Exception {
        ParkingBoy parkingBoy = new SmartParkingBoy(Arrays.asList(new ParkingLot("A1", 3), new ParkingLot("A2", 2)));
        var car1 = new Car("00001");
        var car2 = new Car("00002");
        var car3 = new Car("00003");

        List<Ticket> tickets = parkingBoy.parking(Arrays.asList(car1, car2, car3));

        parkingBoy.getCars(tickets);

        parkingBoy.getCars(tickets);
    }
}