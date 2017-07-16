package com.retail.domain;

public class GeoDetails {

	private double latitude;
	
	private double logitude;

	public GeoDetails(double latitude, double logitude) {
		super();
		this.latitude = latitude;
		this.logitude = logitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLogitude() {
		return logitude;
	}

	public void setLogitude(double logitude) {
		this.logitude = logitude;
	}
	
	
}

