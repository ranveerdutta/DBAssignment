package com.db.retail.service;

import com.db.retail.domain.GeoDetails;
import com.db.retail.domain.Shop;

public interface IShopService {
	
	Shop addShop(Shop shop);
	
	Shop getNearestShop(GeoDetails geoDetails);

}
