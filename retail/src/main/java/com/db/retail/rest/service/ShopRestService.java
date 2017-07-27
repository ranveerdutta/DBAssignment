package com.db.retail.rest.service;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.db.retail.domain.GeoDetails;
import com.db.retail.domain.Shop;
import com.db.retail.exception.ErrorCodes;
import com.db.retail.exception.RetailSystemException;
import com.db.retail.service.IShopService;


/**
 * Rest layer class having all the Rest API for Shop resource
 * @author ranveer
 *
 */
@RestController
@RequestMapping("/shop")
public class ShopRestService {
	
	@Autowired
	private IShopService storeService;
	
	/**
	 * Rest API to add or replace shop
	 * if shop is already present then it returns the old version of the same shop
	 * @param shop
	 * @return
	 */
	@RequestMapping(value="", method = RequestMethod.POST)
	@ResponseStatus( HttpStatus.CREATED)
	public Shop addOrReplaceShop(@RequestBody Shop shop) {
		return storeService.addOrReplaceShop(shop);
	}
	
	/**
	 * Returns the details of nearest shop from the passed location(lat/long)
	 * @param latitude
	 * @param longitude
	 * @return
	 */
	@RequestMapping(value="", method = RequestMethod.GET)
	@ResponseStatus( HttpStatus.OK)
	public Shop getNearByStore(@PathParam("latitude") String latitude, @PathParam("longitude") String longitude) {
		if(StringUtils.isEmpty(latitude) || StringUtils.isEmpty(longitude)) {
			throw new RetailSystemException(ErrorCodes.INSUFFICIENT_GEO_INFO);
		}
		GeoDetails geoDetails = new GeoDetails(Double.parseDouble(latitude), Double.parseDouble(longitude));
		return storeService.getNearestShop(geoDetails);
	}
}
