package com.BensListAutos.vo;

public class ValidListingSearchRequest {
	
	private int limit;
	private int offset;
	private String make;
	private String model;
	private int minYear;
	private int maxYear;
	private int minOdometer;
	private int maxOdometer;
	private int minPrice;
	private int maxPrice;
	private String transmission;
	private String titleStatus;
	private String state;
	
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	public int getOffset() {
		return offset;
	}
	public void setOffset(int offset) {
		this.offset = offset;
	}
	public String getMake() {
		return make;
	}
	public void setMake(String make) {
		this.make = make;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	
	public String getTransmission() {
		return transmission;
	}
	public void setTransmission(String transmission) {
		this.transmission = transmission;
	}
	public String getTitleStatus() {
		return titleStatus;
	}
	public void setTitleStatus(String titleStatus) {
		this.titleStatus = titleStatus;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public int getMinYear() {
		return minYear;
	}
	public void setMinYear(int minYear) {
		this.minYear = minYear;
	}
	public int getMaxYear() {
		return maxYear;
	}
	public void setMaxYear(int maxYear) {
		this.maxYear = maxYear;
	}
	public int getMinOdometer() {
		return minOdometer;
	}
	public void setMinOdometer(int minOdometer) {
		this.minOdometer = minOdometer;
	}
	public int getMaxOdometer() {
		return maxOdometer;
	}
	public void setMaxOdometer(int maxOdometer) {
		this.maxOdometer = maxOdometer;
	}
	public int getMinPrice() {
		return minPrice;
	}
	public void setMinPrice(int minPrice) {
		this.minPrice = minPrice;
	}
	public int getMaxPrice() {
		return maxPrice;
	}
	public void setMaxPrice(int maxPrice) {
		this.maxPrice = maxPrice;
	}
}
