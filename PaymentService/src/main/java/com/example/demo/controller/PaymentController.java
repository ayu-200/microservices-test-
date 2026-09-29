
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

import com.example.demo.client.BookingClient;
import com.example.demo.client.CustomerClient;
import com.example.demo.dao.PaymentRepository;
import com.example.demo.dto.BookingDto;
import com.example.demo.dto.CustomerDto;
import com.example.demo.model.Payment;

import feign.FeignException;

@RequestMapping("/payments")
@RestController
public class PaymentController {

    private final PaymentRepository paymentRepository;
    private final CustomerClient customerClient;
    private final BookingClient bookingClient;

    public PaymentController(PaymentRepository paymentRepository,
            CustomerClient customerClient,
            BookingClient bookingClient) {

        super();
        this.paymentRepository = paymentRepository;
        this.customerClient = customerClient;
        this.bookingClient = bookingClient;
    }


    @PostMapping
    public ResponseEntity<?> createPayment(
            @RequestParam("customerId") int customerId,
            @RequestParam("bookingId") int bookingId,
            @RequestParam("amount") double amount) {

        CustomerDto customerDto;

        try {
            customerDto = customerClient.findCustomerById(customerId);
        } catch (FeignException.NotFound e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Customer Record not found");
        }

        BookingDto bookingDto;

        try {
            bookingDto = bookingClient.findBookingById(bookingId);
        } catch (FeignException.NotFound e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Booking not found");
        }

        Payment payment = new Payment();

        payment.setAmount(amount);
        payment.setBookingId(bookingId);
        payment.setCustomerId(customerId);
        payment.setPaymentDate(LocalDate.now());
        payment.setStatus("Active");

        paymentRepository.save(payment);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(payment);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Payment> findParticularPaymentById(
            @PathVariable("id") int id) {

        Payment payment = paymentRepository.findById(id).orElse(null);

        if (payment == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }

        return ResponseEntity.ok(payment);
    }

  
    @GetMapping
    public ResponseEntity<List<Payment>> findAllPayment() {

        List<Payment> p = paymentRepository.findAll();

        return ResponseEntity.ok(p);
    }

   
    @PutMapping("/{id}")
    public ResponseEntity<Payment> updatePayment(
            @PathVariable("id") int id,
            @RequestBody Payment payment) {

        Payment payment1 = paymentRepository.findById(id).orElse(null);

        if (payment1 == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }

        payment1.setAmount(payment.getAmount());
        payment1.setBookingId(payment.getBookingId());
        payment1.setCustomerId(payment.getCustomerId());
        payment1.setPaymentDate(payment.getPaymentDate());

     
        if (payment.getStatus() != null) {
            payment1.setStatus(payment.getStatus());
        }

        paymentRepository.save(payment1);

        return ResponseEntity.ok(payment1);
    }

    
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePayment(
            @PathVariable("id") int id) {

        if (!paymentRepository.existsById(id)) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Payment not found");
        }

        paymentRepository.deleteById(id);

        return ResponseEntity.ok("Record Deleted");
    }
}

