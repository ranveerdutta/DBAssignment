package com.db.retail.service;

import com.db.retail.domain.GeoDetails;
import com.db.retail.domain.Shop;

/**
 * Interface having all the service layer operations for Shop
 * @author ranveer
 *
 */
public interface IShopService {
	
	/**
	 * Method to add a new shop into the system
	 * @param shop
	 * @return shop - returns old version of shop if present
	 */
	Shop addOrReplaceShop(Shop shop);
	
	/**
	 * Method to fetch the nearest shop
	 * @param geoDetails - latitude and longitude
	 * @return Shop - nearest shop
	 */
	Shop getNearestShop(GeoDetails geoDetails);

}
