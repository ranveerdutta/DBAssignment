package com.db.retail.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.db.retail.domain.Shop;

@Component
public class ShopDaoImpl extends BaseInMemoryDao<Shop> implements ShopDao{

	@Override
	public void addShop(Shop shop) {
		super.addEntity(shop);
	}
	
	@Override
	public List<Shop> getAllShops() {
		return super.getAll();
	}
	

	@Override
	public Shop getStore(String shopName) {
		List<Shop> shopList = super.getAll();
		if(null == shopList || shopList.isEmpty()) {
			return null;
		}
		for(Shop shop: shopList) {
			if(shopName.equalsIgnoreCase(shop.getShopName())) {
				return shop;
			}
		}
		return null;
	}

	@Override
	public void deleteAll() {
		super.cleanAll();
	}


}
