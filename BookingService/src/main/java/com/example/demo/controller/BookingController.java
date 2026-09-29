package com.example.demo.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.client.CustomerClient;
import com.example.demo.client.VehicleClient;
import com.example.demo.dao.BookingRepository;
import com.example.demo.dto.CustomerDto;
import com.example.demo.dto.VehicleDto;
import com.example.demo.model.Booking;

import feign.FeignException;

@RequestMapping("/bookings")
@RestController
public class BookingController {

    private final BookingRepository bookingRepository;
    private final CustomerClient customerClient;
    private final VehicleClient vehicleClient;

    public BookingController(BookingRepository bookingRepository,
            CustomerClient customerClient,
            VehicleClient vehicleClient) {

        super();
        this.bookingRepository = bookingRepository;
        this.customerClient = customerClient;
        this.vehicleClient = vehicleClient;
    }

    @PostMapping
    public ResponseEntity<?> createBooking(
            @RequestParam("customerId") int customerId,
            @RequestParam("vehicleId") int vehicleId,
            @RequestParam("durationDays") int durationDays) {

        CustomerDto customerDto;

        try {
            customerDto = customerClient.findCustomerById(customerId);
        } catch (FeignException.NotFound e) {

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Customer record not found");
        }

        VehicleDto vehicleDto;

        try {
            vehicleDto = vehicleClient.findVehicleById(vehicleId);
        } catch (FeignException.NotFound e) {

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Vehicle not found");
        }

        Booking booking = new Booking();

        booking.setCustomerId(customerId);
        booking.setVehicleId(vehicleId);
        booking.setDurationDays(durationDays);
        booking.setBookingDate(LocalDate.now());
        booking.setStatus("Active");

        bookingRepository.save(booking);

        return ResponseEntity.ok(booking);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Booking> findParticularBookingById(
            @PathVariable("id") int id) {

        Booking booking = bookingRepository.findById(id).orElse(null);

        if (booking == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }

        return ResponseEntity.ok(booking);
    }

    @GetMapping
    public ResponseEntity<List<Booking>> findAllBooking() {

        List<Booking> b = bookingRepository.findAll();

        return ResponseEntity.ok(b);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Booking> updateBooking(
            @PathVariable("id") int id,
            @RequestBody Booking booking) {

        Booking booking1 = bookingRepository.findById(id).orElse(null);

        if (booking1 == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }

        booking1.setBookingDate(booking.getBookingDate());
        booking1.setCustomerId(booking.getCustomerId());
        booking1.setDurationDays(booking.getDurationDays());
        booking1.setVehicleId(booking.getVehicleId());

        // Status tabhi update hoga jab request body mein status diya ho
        if (booking.getStatus() != null) {
            booking1.setStatus(booking.getStatus());
        }

        bookingRepository.save(booking1);

        return ResponseEntity.ok(booking1);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBooking(
            @PathVariable("id") int id) {

        if (!bookingRepository.existsById(id)) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Booking not found");
        }

        bookingRepository.deleteById(id);

        return ResponseEntity.ok("Record deleted");
    }
}
