package com.BensListAutos.vo;

import java.sql.Date;

public class Location {
	
	private String siteName;
	private String city;
	private String state;
	private Date lastScanDate;
	
	public String getSiteName() {
		return siteName;
	}
	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Date getLastScanDate() {
		return lastScanDate;
	}
	public void setLastScanDate(Date lastScanDate) {
		this.lastScanDate = lastScanDate;
	}
}
