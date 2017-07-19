package com.db.retail.dao;

import java.util.Set;

import com.db.retail.domain.Shop;

/**
 * Interface having DB operations for Shop entity
 * @author ranveer
 *
 */
public interface ShopDao {

	void addShop(Shop shop);
	
	Shop getStore(String shopName);
	
	Set<Shop> getAllShops();
	
	void deleteAll();
}
