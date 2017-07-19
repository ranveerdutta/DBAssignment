package com.db.retail.utils;

import com.db.retail.domain.GeoDetails;

/**
 * Util class for all the Geo related utilities
 * @author ranveer
 *
 */
public class GeoUtils {
	
	private static final double DEGREE_TO_MILE_CONVERTER = 69; 
	
	/**
	 * Static method to calculate distance between two Geo points
	 * @param geoDetails1
	 * @param geoDetails2
	 * @return distance
	 */
	public static double calculateDistanceInMiles(GeoDetails geoDetails1, GeoDetails geoDetails2) {
		double theta = geoDetails1.getLongitude() - geoDetails2.getLongitude();
		double dist = Math.sin(deg2rad(geoDetails1.getLatitude())) * Math.sin(deg2rad(geoDetails2.getLatitude())) 
				+ Math.cos(deg2rad(geoDetails1.getLatitude())) * Math.cos(deg2rad(geoDetails2.getLatitude())) * Math.cos(deg2rad(theta));
		dist = Math.acos(dist);
		dist = rad2deg(dist);
		return dist * DEGREE_TO_MILE_CONVERTER;
	}
	
	public static double deg2rad(double deg) {
		return (deg * Math.PI / 180.0);
	}
	
	private static double rad2deg(double rad) {
		return (rad * 180 / Math.PI);
	}
}
