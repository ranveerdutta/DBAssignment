package com.db.retail.dao;

import java.util.Set;

import com.db.retail.domain.Shop;

/**
 * Interface having DB operations for the Shop entity
 * @author ranveer
 *
 */
public interface ShopDao {

	/**
	 * Create new shop into the database or overrides the existing shop 
	 * with the new one
	 * @param shop
	 * @return Shop - old version of shop in case of overriding
	 */
	Shop createNewOrOverrideExistingShop(Shop shop);
	
	/**
	 * Returns the Shop details corresponding to the shop name
	 * @param shopName
	 * @return Shop
	 */
	Shop getShop(String shopName);
	
	/**
	 * Returns all the shops from the system
	 * @return Set<Shop>
	 */
	Set<Shop> getAllShops();
	
	
	/**
	 * Deletes all the shops from the system
	 */
	void deleteAll();
}
