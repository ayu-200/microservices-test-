package com.example.demo.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.dto.DriverDto;

@FeignClient(name="DriverService")
public interface DriverClient {
@GetMapping("/drivers/{id}")
DriverDto findDriverById(@PathVariable("id")int id);
}
