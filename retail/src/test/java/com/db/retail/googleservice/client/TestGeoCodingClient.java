package com.db.retail.googleservice.client;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.db.retail.domain.GeoDetails;
import com.db.retail.exception.RetailSystemException;
import com.google.code.geocoder.Geocoder;
import com.google.code.geocoder.model.GeocodeResponse;
import com.google.code.geocoder.model.GeocoderGeometry;
import com.google.code.geocoder.model.GeocoderResult;
import com.google.code.geocoder.model.GeocoderStatus;
import com.google.code.geocoder.model.LatLng;

public class TestGeoCodingClient {
	
	@Test
	public void testGetGeoDetailsWithValidAddress() {
		//mock the google api call
		Geocoder geocoderMock = mock(Geocoder.class);
		GeocodeResponse response = new GeocodeResponse();
		response.setStatus(GeocoderStatus.OK);
		GeocoderResult result = new GeocoderResult();
		LatLng latLng = new LatLng(new BigDecimal(1.234), new BigDecimal(2.345));
		GeocoderGeometry geometry = new GeocoderGeometry();
		geometry.setLocation(latLng);
		result.setGeometry(geometry);
		List<GeocoderResult> resultList = new LinkedList<>();
		resultList.add(result);
		response.setResults(resultList);
		when(geocoderMock.geocode(any())).thenReturn(response);
		
		GeoCodingClient client = new GeoCodingClient();
		client.setGeocoder(geocoderMock);
		GeoDetails details = client.getGeoDetails("valid address");
		Assert.assertEquals(1.234, details.getLatitude(), 0);
		Assert.assertEquals(2.345, details.getLongitude(), 0);
	}
	
	@Test(expected = RetailSystemException.class)
	public void testGetGeoDetailsWithInValidAddress() {
		//mock the google api call
		Geocoder geocoderMock = mock(Geocoder.class);
		GeocodeResponse response = new GeocodeResponse();
		response.setStatus(GeocoderStatus.ZERO_RESULTS);
		when(geocoderMock.geocode(any())).thenReturn(response);
		GeoCodingClient client = new GeoCodingClient();
		client.setGeocoder(geocoderMock);
		client.getGeoDetails("Invalid address");
	}

}
