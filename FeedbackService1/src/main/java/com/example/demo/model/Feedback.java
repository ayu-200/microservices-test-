package com.example.demo.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Feedback {
@Id
@GeneratedValue(strategy=GenerationType.IDENTITY)
private int id;
private int customerId;
private int tripId;
private int bookingId;
private String ratingScore;
private String commnets;
private LocalDate feedbackDate;
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public int getCustomerId() {
	return customerId;
}
public void setCustomerId(int customerId) {
	this.customerId = customerId;
}
public int getTripId() {
	return tripId;
}
public void setTripId(int tripId) {
	this.tripId = tripId;
}
public int getBookingId() {
	return bookingId;
}
public void setBookingId(int bookingId) {
	this.bookingId = bookingId;
}
public String getRatingScore() {
	return ratingScore;
}
public void setRatingScore(String ratingScore) {
	this.ratingScore = ratingScore;
}
public String getCommnets() {
	return commnets;
}
public void setCommnets(String commnets) {
	this.commnets = commnets;
}
public LocalDate getFeedbackDate() {
	return feedbackDate;
}
public void setFeedbackDate(LocalDate feedbackDate) {
	this.feedbackDate = feedbackDate;
}

}
