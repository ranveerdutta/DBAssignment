package com.db.retail.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.db.retail.dao.ShopDao;
import com.db.retail.domain.GeoDetails;
import com.db.retail.domain.Shop;
import com.db.retail.exception.ErrorCodes;
import com.db.retail.exception.RetailSystemException;
import com.db.retail.googleservice.client.GeoCodingClient;
import com.db.retail.utils.GeoUtils;

/**
 * Service layer implementation of all the shop operations
 * @author ranveer
 *
 */
@Service
public class ShopServiceImpl implements IShopService{
	
	@Autowired
	private GeoCodingClient geoCodingClient;
	
	@Autowired
	private ShopDao shopDao;

	@Override
	public Shop addShop(Shop shop) {
		
		//check if all the fields are present in shop object
		if(null == shop || StringUtils.isEmpty(shop.getShopName()) || null == shop.getShopAddress()
				|| StringUtils.isEmpty(shop.getShopAddress().getNumber()) || StringUtils.isEmpty(shop.getShopAddress().getPostCode())) {
			throw new RetailSystemException(ErrorCodes.INSUFFICIENT_SHOP_INFO);
		}
		
		// fetch lat and long of shop address
		GeoDetails geoDetails = geoCodingClient.getGeoDetails(shop.createShopAddressString());
		shop.setShopGeoDetails(geoDetails);
		
		//add the shop details
		shopDao.addShop(shop);
		//return added shop along with the geo details
		return shop;
	}
	
	@Override
	public Shop getNearestShop(GeoDetails geoDetails) {
		Set<Shop> shopList = shopDao.getAllShops();
		if(null == shopList || shopList.isEmpty()) {
			throw new RetailSystemException(ErrorCodes.NO_SHOP_IN_THE_SYSTEM);
		}
		
		double minDistanceOfShop = -1;
		Shop nearestShop = null;
		for(Shop shop : shopList) {
			double distance = GeoUtils.calculateDistanceInMiles(geoDetails, shop.getShopGeoDetails());
			if(null == nearestShop || distance < minDistanceOfShop) {
				nearestShop = shop;
				minDistanceOfShop = distance;
			}
		}
		
		return nearestShop;
	}

}
