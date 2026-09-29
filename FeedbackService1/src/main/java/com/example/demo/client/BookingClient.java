package com.example.demo.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.dto.BookingDto;

@FeignClient(name="BookingService")
public interface BookingClient {
@GetMapping("/bookings/{id}")
BookingDto findBookingById(@PathVariable("id")int id);
}
