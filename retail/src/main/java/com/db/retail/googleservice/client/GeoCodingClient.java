package com.db.retail.googleservice.client;

import org.springframework.stereotype.Component;

import com.db.retail.domain.GeoDetails;
import com.db.retail.exception.ErrorCodes;
import com.db.retail.exception.RetailSystemException;
import com.google.code.geocoder.Geocoder;
import com.google.code.geocoder.GeocoderRequestBuilder;
import com.google.code.geocoder.model.GeocodeResponse;
import com.google.code.geocoder.model.GeocoderRequest;
import com.google.code.geocoder.model.GeocoderResult;
import com.google.code.geocoder.model.GeocoderStatus;

/**
 * Http client for making Geocode API call to google service
 * @author ranveer
 *
 */
@Component
public class GeoCodingClient{
	
	private Geocoder geocoder;
	
	
	public GeoDetails getGeoDetails(String addressStr) {
		if(null == geocoder) {
			geocoder = new Geocoder(); 
		}
	    GeocoderRequest geocoderRequest = new GeocoderRequestBuilder() 
	      .setAddress(addressStr) 
	      .setLanguage("en") 
	      .getGeocoderRequest(); 
	    GeocodeResponse geocoderResponse = geocoder.geocode(geocoderRequest); 
	    
	    if(null == geocoderResponse) {
	    	throw new RetailSystemException(ErrorCodes.GEOCODE_FETCH_ERROR);
	    }
	 
	    if (geocoderResponse.getStatus() == GeocoderStatus.OK) { 
	      GeocoderResult firstResult = geocoderResponse.getResults().get(0); 
	      return new GeoDetails( 
	        firstResult.getGeometry().getLocation().getLat().doubleValue(), 
	        firstResult.getGeometry().getLocation().getLng().doubleValue()); 
	    }else if (geocoderResponse.getStatus() == GeocoderStatus.ZERO_RESULTS) {
	    	throw new RetailSystemException(ErrorCodes.INVALID_ADDRESS);
	    	
	    }else if (geocoderResponse.getStatus() == GeocoderStatus.REQUEST_DENIED) {
	    	throw new RetailSystemException(ErrorCodes.GEOCODE_FETCH_ERROR);
		    	
		} else if (geocoderResponse.getStatus() == GeocoderStatus.INVALID_REQUEST) {
			throw new RetailSystemException(ErrorCodes.GEOCODE_FETCH_ERROR);
	    	
		}else if (geocoderResponse.getStatus() == GeocoderStatus.ERROR) {
			throw new RetailSystemException(ErrorCodes.GEOCODE_TEMP_ERROR);
	    	
		}else if (geocoderResponse.getStatus() == GeocoderStatus.UNKNOWN_ERROR) {
			throw new RetailSystemException(ErrorCodes.GEOCODE_TEMP_ERROR);
	    	
		}else if (geocoderResponse.getStatus() == GeocoderStatus.OVER_QUERY_LIMIT) {
			throw new RetailSystemException(ErrorCodes.GEOCODE_TEMP_ERROR);
	    	
		}else {
			throw new RetailSystemException(ErrorCodes.GEOCODE_FETCH_ERROR);
		}
	    
	  }


	public void setGeocoder(Geocoder geocoder) {
		this.geocoder = geocoder;
	} 
	
	
	
}
