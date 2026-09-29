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

import com.example.demo.dao.DriverRepository;
import com.example.demo.model.Driver;


@RequestMapping("/drivers")
@RestController
public class DriverController {
private DriverRepository driverRepository;

public DriverController(DriverRepository driverRepository) {
	super();
	this.driverRepository = driverRepository;
}
@PostMapping
public ResponseEntity<Driver> addDriver(@RequestBody Driver driver){
	Driver saveDriver=driverRepository.save(driver);
	return new ResponseEntity<>(saveDriver,org.springframework.http.HttpStatus.CREATED);
}
@GetMapping
public ResponseEntity<List<Driver>> findAllDriver(){
	List<Driver> driver=driverRepository.findAll();
	return ResponseEntity.ok(driver);
}
@GetMapping("/{id}")
public ResponseEntity<Driver> findDriverById(@PathVariable("id")int id){
	Driver driver=driverRepository.findById(id).orElse(null);
	if(driver==null) {
		return ResponseEntity.status(org.springframework.http.HttpStatus.NOT_FOUND).build();
	}
	return ResponseEntity.ok(driver);
}
@DeleteMapping("/{id}")
public ResponseEntity<String> deleteDriverId(@PathVariable("id") int id){

    if(!driverRepository.existsById(id)) {
        return ResponseEntity
                .status(org.springframework.http.HttpStatus.NOT_FOUND)
                .body("Driver not found");
    }

    driverRepository.deleteById(id);

    return ResponseEntity
            .status(org.springframework.http.HttpStatus.OK)
            .body("Record deleted");
}
@PutMapping("/{id}")
public ResponseEntity<Driver> updateDriver(@PathVariable("id")int id,@RequestBody Driver driver){
	Driver driver1=driverRepository.findById(id).orElse(null);
			if(driver1==null) {
				return ResponseEntity.status(org.springframework.http.HttpStatus.NOT_FOUND).build();
			}
	driver1.setEmail(driver.getEmail());
	driver1.setLicenseNumber(driver.getLicenseNumber());
	driver1.setName(driver.getName());
	driver1.setSpecialization(driver.getSpecialization());
	Driver updateDriver=driverRepository.save(driver1);
	return ResponseEntity.ok(updateDriver);
}
}
