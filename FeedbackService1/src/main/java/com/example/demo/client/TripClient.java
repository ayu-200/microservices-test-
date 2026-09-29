package com.example.demo.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.dto.TripDto;

@FeignClient(name="TripService")
public interface TripClient {
@GetMapping("/trips/{id}")
TripDto findTripById(@PathVariable("id")int id);
}
