package com.thoughworks.capability;

import com.thoughworks.capability.Exception.InvalidTicketException;
import com.thoughworks.capability.Exception.ParkingIsFullException;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class ParkingLotTest {

    private ParkingLot parkingLot = new ParkingLot("A1", 100);

    @Test
    public void should_parking_one_car_when_parkingLot_capacity_has_spare_position() throws Exception {
        Car car = new Car("00001");
        assertEquals("ticket-00001", parkingLot.parking(car).getId());
    }

    @Test
    public void should_parking_a_list_of_car_when_parkingLot_capacity_has_spare_position() throws Exception {
        List<Car> cars = Arrays.asList(new Car("00001"), new Car("00002"), new Car("00003"));
        assertEquals(3, parkingLot.parking(cars).size());
    }

    @Test(expected = ParkingIsFullException.class)
    public void should_throw_exception_when_parkingLot_capacity_has_no_spare_position() throws Exception {
        ParkingLot parkingLot = new ParkingLot("A1", 0);
        Car car = new Car("00001");
        parkingLot.parking(car);
    }

    @Test
    public void should_get_one_car_when_my_car_is_in_parkingLot() throws Exception {
        var car1 = new Car("00001");
        Car car2 = new Car("00002");
        Car car3 = new Car("00003");
        List<Car> cars = Arrays.asList(car1, car2, car3);

        List<Ticket> tickets =  parkingLot.parking(cars);
        Car returnCar1 = parkingLot.getCar(tickets.get(0));
        Car returnCar3 = parkingLot.getCar(tickets.get(2));

        assertEquals(car1, returnCar1);
        assertEquals(car3, returnCar3);
    }

    @Test
    public void should_get_a_list_of_car_when_my_cars_are_in_parkingLot() throws Exception {
        var car1 = new Car("00001");
        Car car2 = new Car("00002");
        Car car3 = new Car("00003");
        List<Car> cars = Arrays.asList(car1, car2, car3);

        List<Ticket> tickets =  parkingLot.parking(cars);

        assertEquals(3, parkingLot.getCars(tickets).size());
    }

    @Test(expected = ParkingIsFullException.class)
    public void should_throw_exception_when_parkingLot_capacity_don_not_have_enough_spare_position() throws Exception {
        ParkingLot parkingLot = new ParkingLot("A1", 2);

        List<Car> cars = Arrays.asList(new Car("00001"), new Car("00002"), new Car("00003"));

        parkingLot.parking(cars);
    }

    @Test(expected = InvalidTicketException.class)
    public void should_throw_exception_when_ticket_is_invalid() throws Exception {
        Car car = new Car("00001");
        parkingLot.parking(car);

        parkingLot.getCar(new Ticket(parkingLot.getId(), new Car("00002")));
    }

    @Test(expected = InvalidTicketException.class)
    public void should_throw_exception_when_tickets_are_invalid() throws Exception {
        var car1 = new Car("00001");
        Car car2 = new Car("00002");
        Car car3 = new Car("00003");
        List<Car> cars = Arrays.asList(car1, car2, car3);

        parkingLot.parking(cars);

        parkingLot.getCars(Arrays.asList(new Ticket(parkingLot.getId(), car1)));
    }
}