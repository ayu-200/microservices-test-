
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
import com.example.demo.client.TripClient;
import com.example.demo.dao.FeedbackRepository;
import com.example.demo.dto.BookingDto;
import com.example.demo.dto.CustomerDto;
import com.example.demo.dto.TripDto;
import com.example.demo.model.Feedback;

import feign.FeignException;

@RequestMapping("/feedbacks")
@RestController
public class FeedbackController {

    private final FeedbackRepository feedbackRepository;
    private final CustomerClient customerClient;
    private final TripClient tripClient;
    private final BookingClient bookingClient;

    public FeedbackController(FeedbackRepository feedbackRepository,
            CustomerClient customerClient,
            TripClient tripClient,
            BookingClient bookingClient) {

        super();
        this.feedbackRepository = feedbackRepository;
        this.customerClient = customerClient;
        this.tripClient = tripClient;
        this.bookingClient = bookingClient;
    }

    @PostMapping
    public ResponseEntity<?> createFeedback(
            @RequestParam("customerId") int customerId,
            @RequestParam("tripId") int tripId,
            @RequestParam("bookingId") int bookingId,
            @RequestParam("commnets") String commnets,
            @RequestParam("ratingScore") String ratingScore) {

        
        try {
            customerClient.findCustomerById(customerId);
        } catch (FeignException.NotFound e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Customer not found");
        }

       
        try {
            tripClient.findTripById(tripId);
        } catch (FeignException.NotFound e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Trip not found");
        }

        
        try {
            bookingClient.findBookingById(bookingId);
        } catch (FeignException.NotFound e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Booking record not found");
        }

        Feedback feedback = new Feedback();

        feedback.setBookingId(bookingId);
        feedback.setCommnets(commnets);
        feedback.setCustomerId(customerId);
        feedback.setRatingScore(ratingScore);
        feedback.setTripId(tripId);
        feedback.setFeedbackDate(LocalDate.now());

        feedbackRepository.save(feedback);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(feedback);
    }

    @GetMapping
    public ResponseEntity<List<Feedback>> findAllFeedback() {

        List<Feedback> f = feedbackRepository.findAll();

        return ResponseEntity.ok(f);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Feedback> findParticularFeedbackById(
            @PathVariable("id") int id) {

        Feedback feedback = feedbackRepository.findById(id).orElse(null);

        if (feedback == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }

        return ResponseEntity.ok(feedback);
    }

   
    @PutMapping("/{id}")
    public ResponseEntity<Feedback> updateFeedback(
            @PathVariable("id") int id,
            @RequestBody Feedback feedback) {

        Feedback feedback1 = feedbackRepository.findById(id).orElse(null);

        if (feedback1 == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }

        feedback1.setBookingId(feedback.getBookingId());
        feedback1.setCommnets(feedback.getCommnets());
        feedback1.setRatingScore(feedback.getRatingScore());
        feedback1.setTripId(feedback.getTripId());
        feedback1.setCustomerId(feedback.getCustomerId());
        feedback1.setFeedbackDate(feedback.getFeedbackDate());

        feedbackRepository.save(feedback1);

        return ResponseEntity.ok(feedback1);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFeedback(
            @PathVariable("id") int id) {

        if (!feedbackRepository.existsById(id)) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Feedback not found");
        }

        feedbackRepository.deleteById(id);

        return ResponseEntity.ok("Record deleted");
    }
}

