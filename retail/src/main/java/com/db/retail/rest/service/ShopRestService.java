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

@RestController
@RequestMapping("/shop")
public class ShopRestService {
	
	@Autowired
	private IShopService storeService;

	@RequestMapping(value="", method = RequestMethod.POST)
	@ResponseStatus( HttpStatus.CREATED)
	public Shop addShop(@RequestBody Shop shop) {
		return storeService.addShop(shop);
	}
	
	@RequestMapping(value="/nearest", method = RequestMethod.GET)
	@ResponseStatus( HttpStatus.OK)
	public Shop getNearByStore(@PathParam("latitude") String latitude, @PathParam("longitude") String longitude) {
		if(StringUtils.isEmpty(latitude) || StringUtils.isEmpty(longitude)) {
			throw new RetailSystemException(ErrorCodes.INSUFFICIENT_GEO_INFO);
		}
		GeoDetails geoDetails = new GeoDetails(Double.parseDouble(latitude), Double.parseDouble(longitude));
		return storeService.getNearestShop(geoDetails);
	}
}
