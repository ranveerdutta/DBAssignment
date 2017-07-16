package com.retail.dao;

import java.util.List;

import com.retail.domain.Shop;

public interface ShopDao {

	void addShop(Shop shop);
	
	Shop getStore(String shopName);
	
	List<Shop> getAllShops();
}
