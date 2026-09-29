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

import com.example.demo.dao.CustomerRepository;
import com.example.demo.model.Customer;

@RequestMapping("/customers")
@RestController
public class CustomerController {
private CustomerRepository customerRepository;

public CustomerController(CustomerRepository customerRepository) {
	super();
	this.customerRepository = customerRepository;
}
@PostMapping
public ResponseEntity<Customer> addCustomer(@RequestBody Customer customer){
	Customer saveCustomer=customerRepository.save(customer);
  return new ResponseEntity<>(saveCustomer,org.springframework.http.HttpStatus.CREATED);
}
@GetMapping
public ResponseEntity<List<Customer>> findAllCustomer() {
	List<Customer> customer=customerRepository.findAll();
	return ResponseEntity.ok(customer);
	
}
@GetMapping("/{id}")
public ResponseEntity<Customer> findCustomerById(@PathVariable("id")int id){
	Customer customer=customerRepository.findById(id).orElse(null);
	if(customer==null) {
		return ResponseEntity.status(org.springframework.http.HttpStatus.NOT_FOUND).build();
	}
	return ResponseEntity.ok(customer);
	
}
@DeleteMapping("/{id}")
public ResponseEntity<String> deleteId(@PathVariable("id") int id) {

    if (!customerRepository.existsById(id)) {
        return ResponseEntity
                .status(org.springframework.http.HttpStatus.NOT_FOUND)
                .body("Customer not found");
    }

    customerRepository.deleteById(id);

    return ResponseEntity.ok("Record deleted");
}
@PutMapping("/{id}")
public ResponseEntity<Customer> updateCustomer(@PathVariable("id")int id,@RequestBody Customer customer){
	Customer customer1=customerRepository.findById(id).orElse(null);
	if(customer1==null) {
		return ResponseEntity.status(org.springframework.http.HttpStatus.NOT_FOUND).build();
	}																															
		customer1.setEmail(customer.getEmail());
		customer1.setMobile(customer.getMobile());
		customer1.setName(customer.getName());
		Customer updateCustomer1=customerRepository.save(customer1);
		return ResponseEntity.ok(updateCustomer1);



}
}
