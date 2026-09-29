package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Driver {
@Id
@GeneratedValue(strategy=GenerationType.IDENTITY)
private int id;
private String email;
private String name;
private String licenseNumber;
private String Specialization;
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getLicenseNumber() {
	return licenseNumber;
}
public void setLicenseNumber(String licenseNumber) {
	this.licenseNumber = licenseNumber;
}
public String getSpecialization() {
	return Specialization;
}
public void setSpecialization(String specialization) {
	Specialization = specialization;
}


}
