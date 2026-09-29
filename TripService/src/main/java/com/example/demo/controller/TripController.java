
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

import com.example.demo.client.DriverClient;
import com.example.demo.client.VehicleClient;
import com.example.demo.dao.TripRepository;
import com.example.demo.dto.DriverDto;
import com.example.demo.dto.VehicleDto;
import com.example.demo.model.Trip;

import feign.FeignException;

@RequestMapping("/trips")
@RestController
public class TripController {

    private final TripRepository tripRepository;
    private final VehicleClient vehicleClient;
    private final DriverClient driverClient;

    public TripController(TripRepository tripRepository,
            VehicleClient vehicleClient,
            DriverClient driverClient) {

        super();
        this.tripRepository = tripRepository;
        this.vehicleClient = vehicleClient;
        this.driverClient = driverClient;
    }

    
    
    @PostMapping
    public ResponseEntity<?> createTrip(
            @RequestParam("title") String title,
            @RequestParam("routeDetails") String routeDetails,
            @RequestParam("vehicleId") int vehicleId,
            @RequestParam("driverId") int driverId,
            @RequestParam("dueDate") String dueDate) {

        
        try {
            vehicleClient.findVehicleById(vehicleId);
        } catch (FeignException.NotFound e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Vehicle Record not found");
        }

        
        try {
            driverClient.findDriverById(driverId);
        } catch (FeignException.NotFound e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Driver Record not found");
        }

        Trip trip = new Trip();

        trip.setTitle(title);
        trip.setRouteDetails(routeDetails);
        trip.setVehicleId(vehicleId);
        trip.setDriverId(driverId);
        trip.setDueDate(LocalDate.now());

        tripRepository.save(trip);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(trip);
    }
  

   

    @GetMapping("/{id}")
    public ResponseEntity<Trip> findTripById(
            @PathVariable("id") int id) {

        Trip trip = tripRepository.findById(id).orElse(null);

        if (trip == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }

        return ResponseEntity.ok(trip);
    }

    @GetMapping
    public ResponseEntity<List<Trip>> findAllTrip() {

        List<Trip> t = tripRepository.findAll();

        return ResponseEntity.ok(t);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Trip> updateTrip(
            @PathVariable("id") int id,
            @RequestBody Trip trip) {

        Trip trip1 = tripRepository.findById(id).orElse(null);

        if (trip1 == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }

        trip1.setTitle(trip.getTitle());
        trip1.setRouteDetails(trip.getRouteDetails());
        trip1.setVehicleId(trip.getVehicleId());
        trip1.setDriverId(trip.getDriverId());
        trip1.setDueDate(trip.getDueDate());

        tripRepository.save(trip1);

        return ResponseEntity.ok(trip1);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTrip(
            @PathVariable("id") int id) {

        if (!tripRepository.existsById(id)) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Trip not found");
        }

        tripRepository.deleteById(id);

        return ResponseEntity.ok("Record deleted");
    }
}
