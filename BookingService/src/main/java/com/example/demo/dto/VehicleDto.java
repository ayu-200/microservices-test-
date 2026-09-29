package com.example.demo.dto;

public class VehicleDto {
private int id;
private String vehicleName;
private String model;
private String type;
private double dailyfee;
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getVehicleName() {
	return vehicleName;
}
public void setVehicleName(String vehicleName) {
	this.vehicleName = vehicleName;
}
public String getModel() {
	return model;
}
public void setModel(String model) {
	this.model = model;
}
public String getType() {
	return type;
}
public void setType(String type) {
	this.type = type;
}
public double getDailyfee() {
	return dailyfee;
}
public void setDailyfee(double dailyfee) {
	this.dailyfee = dailyfee;
}

}
