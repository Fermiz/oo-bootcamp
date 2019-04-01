package com.thoughworks.capability;

import com.thoughworks.capability.Exception.InvalidTicketException;
import com.thoughworks.capability.Exception.ParkingIsFullException;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class GraduateParkingBoyTest {

    @Test
    public void should_parking_car_in_only_parking_lot_when_only_one_parking_lot() throws Exception {
        ParkingBoy parkingBoy = new GraduateParkingBoy(Arrays.asList(new ParkingLot("A1", 3)));
        var car = new Car("00001");
        Assert.assertNotNull(parkingBoy.parking(car));
    }

    @Test
    public void should_parking_car_in_first_parking_lot_when_first_parking_lot_has_enough_spare_position() throws Exception {
        ParkingBoy parkingBoy = new GraduateParkingBoy(Arrays.asList(new ParkingLot("A1", 1), new ParkingLot("A2", 2)));
        var car = new Car("00001");
        assertEquals("A1", parkingBoy.parking(car).getParkingLotId());
    }

    @Test
    public void should_parking_cars_in_both_parking_lots() throws Exception {
        ParkingBoy parkingBoy = new GraduateParkingBoy(Arrays.asList(new ParkingLot("A1", 2), new ParkingLot("A2", 3)));
        var car1 = new Car("00001");
        var car2 = new Car("00002");
        var car3 = new Car("00003");

        List<Ticket> tickets = parkingBoy.parking(Arrays.asList(car1, car2, car3));
        assertEquals(3, tickets.size());
        assertEquals("A1", tickets.get(0).getParkingLotId());
        assertEquals("A1", tickets.get(1).getParkingLotId());
        assertEquals("A2", tickets.get(2).getParkingLotId());
    }

    @Test(expected = ParkingIsFullException.class)
    public void should_throw_exception_when_both_parking_lots_is_full() throws Exception {
        ParkingBoy parkingBoy = new GraduateParkingBoy(Arrays.asList(new ParkingLot("A1", 0), new ParkingLot("A2", 0)));
        var car1 = new Car("00001");
        var car2 = new Car("00002");
        var car3 = new Car("00003");

        parkingBoy.parking(Arrays.asList(car1, car2, car3));
    }

    @Test
    public void should_get_my_car_when_my_car_has_parked_in_parking_lot() throws Exception {
        ParkingBoy parkingBoy = new GraduateParkingBoy(Arrays.asList(new ParkingLot("A1", 3), new ParkingLot("A2", 2)));
        var car1 = new Car("00001");
        var car2 = new Car("00002");
        var car3 = new Car("00003");

        List<Ticket> tickets = parkingBoy.parking(Arrays.asList(car1, car2, car3));

        assertEquals("00001", parkingBoy.getCar(tickets.get(0)).getId());
    }

    @Test
    public void should_get_my_cars_when_my_car_has_parked_in_parking_lot() throws Exception {
        ParkingBoy parkingBoy = new GraduateParkingBoy(Arrays.asList(new ParkingLot("A1", 3), new ParkingLot("A2", 2)));
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
        ParkingBoy parkingBoy = new GraduateParkingBoy(Arrays.asList(new ParkingLot("A1", 3), new ParkingLot("A2", 2)));
        var car1 = new Car("00001");
        var car2 = new Car("00002");
        var car3 = new Car("00003");

        parkingBoy.parking(Arrays.asList(car1, car2, car3));

        parkingBoy.getCar(new Ticket("A1", "00004")).getId();
    }

    @Test(expected = InvalidTicketException.class)
    public void should_throw_exception_when_get_car_with_used_ticket() throws Exception {
        ParkingBoy parkingBoy = new GraduateParkingBoy(Arrays.asList(new ParkingLot("A1", 3), new ParkingLot("A2", 2)));
        var car1 = new Car("00001");

        Ticket ticket = parkingBoy.parking(car1);

        parkingBoy.getCar(ticket);

        parkingBoy.getCar(ticket);
    }

    @Test
    public void should_get_my_cars_when_my_cars_has_parked_in_parking_lots() throws Exception {
        ParkingBoy parkingBoy = new GraduateParkingBoy(Arrays.asList(new ParkingLot("A1", 2), new ParkingLot("A2", 3)));
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
        ParkingBoy parkingBoy = new GraduateParkingBoy(Arrays.asList(new ParkingLot("A1", 2), new ParkingLot("A2", 3)));
        var car1 = new Car("00001");
        var car2 = new Car("00002");
        var car3 = new Car("00003");

        List<Ticket> tickets = parkingBoy.parking(Arrays.asList(car1, car2, car3));

        parkingBoy.getCars(Arrays.asList(tickets.get(0), new Ticket("A1", "00004")));
    }

    @Test(expected = InvalidTicketException.class)
    public void should_throw_exception_when_get_cars_with_used_ticket() throws Exception {
        ParkingBoy parkingBoy = new GraduateParkingBoy(Arrays.asList(new ParkingLot("A1", 3), new ParkingLot("A2", 2)));
        var car1 = new Car("00001");
        var car2 = new Car("00002");
        var car3 = new Car("00003");

        List<Ticket> tickets = parkingBoy.parking(Arrays.asList(car1, car2, car3));

        parkingBoy.getCars(tickets);

        parkingBoy.getCars(tickets);
    }
}
