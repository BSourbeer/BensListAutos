package com.BensListAutos.vo;

import java.sql.Date;

public class Listing {
	
	private int listingID;
	
	private String title;
	private String url;
	private String location;
	
	private String yearMakeModel;
	private int odometer;
	private String condition;
	private String cylinders;
	private String drive;
	private String fuel;
	private String color;
	private String titleStatus;
	private String size;
	private String transmission;
	private String type;
	
	private String description;
	private int price;
	private Date postedDate;
	private String validateStatus;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getYearMakeModel() {
		return yearMakeModel;
	}
	public void setYearMakeModel(String yearMakeModel) {
		this.yearMakeModel = yearMakeModel;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int getOdometer() {
		return odometer;
	}
	public void setOdometer(int odometer) {
		this.odometer = odometer;
	}
	public String getCondition() {
		return condition;
	}
	public void setCondition(String condition) {
		this.condition = condition;
	}
	public String getCylinders() {
		return cylinders;
	}
	public void setCylinders(String cylinders) {
		this.cylinders = cylinders;
	}
	public String getDrive() {
		return drive;
	}
	public void setDrive(String drive) {
		this.drive = drive;
	}
	public String getFuel() {
		return fuel;
	}
	public void setFuel(String fuel) {
		this.fuel = fuel;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getTitleStatus() {
		return titleStatus;
	}
	public void setTitleStatus(String titleStatus) {
		this.titleStatus = titleStatus;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getTransmission() {
		return transmission;
	}
	public void setTransmission(String transmission) {
		this.transmission = transmission;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public Date getPostedDate() {
		return postedDate;
	}
	public void setPostedDate(Date postedDate) {
		this.postedDate = postedDate;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getValidateStatus() {
		return validateStatus;
	}
	public void setValidateStatus(String validateStatus) {
		this.validateStatus = validateStatus;
	}
	public int getListingID() {
		return listingID;
	}
	public void setListingID(int listingID) {
		this.listingID = listingID;
	}
	
	
	
}
