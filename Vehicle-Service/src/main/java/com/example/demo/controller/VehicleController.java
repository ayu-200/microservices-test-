package com.example.demo.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dao.VehicleRepository;
import com.example.demo.model.Vehicle;

@RequestMapping("/vehicles")
@RestController
public class VehicleController {
private VehicleRepository vehicleRepository;

public VehicleController(VehicleRepository vehicleRepository) {
	super();
	this.vehicleRepository = vehicleRepository;
}
@PostMapping
public ResponseEntity<Vehicle> addVehicle(@RequestBody Vehicle vehicle){
	Vehicle saveVehicle=vehicleRepository.save(vehicle);
return new ResponseEntity<>(saveVehicle,org.springframework.http.HttpStatus.CREATED);
}
@GetMapping
public ResponseEntity<List<Vehicle>> findAllVehicle(){
	List<Vehicle> vehicle=vehicleRepository.findAll();
	return ResponseEntity.ok(vehicle);
}
@GetMapping("/{id}")
public ResponseEntity<Vehicle> findVehicleById(@PathVariable("id")int id){
	Vehicle vehicle=vehicleRepository.findById(id).orElse(null);
	if(vehicle==null) {
	return ResponseEntity.status(org.springframework.http.HttpStatus.NOT_FOUND).build();
	}
	return ResponseEntity.ok(vehicle);
	
}
@DeleteMapping("/{id}")
public ResponseEntity<String> deleteId(@PathVariable("id") int id){

    if(!vehicleRepository.existsById(id)) {
        return ResponseEntity
                .status(org.springframework.http.HttpStatus.NOT_FOUND)
                .body("Vehicle not found");
    }

    vehicleRepository.deleteById(id);

    return ResponseEntity
            .status(org.springframework.http.HttpStatus.OK)
            .body("Record deleted");
}
@PutMapping("/{id}")
public ResponseEntity<Vehicle> updateVehicle(
        @PathVariable("id") int id,
        @RequestBody Vehicle vehicle) {

    Vehicle vehicle1 = vehicleRepository.findById(id).orElse(null);

    if (vehicle1 == null) {
        return ResponseEntity
                .status(org.springframework.http.HttpStatus.NOT_FOUND)
                .build();
    }

    vehicle1.setDailyfee(vehicle.getDailyfee());
    vehicle1.setModel(vehicle.getModel());
    vehicle1.setVehicleName(vehicle.getVehicleName());
    vehicle1.setType(vehicle.getType());

    Vehicle updateVehicle1 = vehicleRepository.save(vehicle1);

    return ResponseEntity.ok(updateVehicle1);
}}
