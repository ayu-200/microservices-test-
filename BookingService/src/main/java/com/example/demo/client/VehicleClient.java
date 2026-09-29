package com.example.demo.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.dto.VehicleDto;

@FeignClient(name="Vehicle-Service")
public interface VehicleClient {
@GetMapping("/vehicles/{id}")
VehicleDto findVehicleById(@PathVariable("id")int id);
}