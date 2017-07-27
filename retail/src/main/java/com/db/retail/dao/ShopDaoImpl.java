package com.db.retail.dao;

import java.util.Set;

import org.springframework.stereotype.Component;

import com.db.retail.domain.Shop;

/**
 * Implementation class having operations for Shop entity
 * @author Ranveer
 *
 */
@Component
public class ShopDaoImpl extends BaseInMemoryDao<Shop> implements ShopDao{

	@Override
	public Shop createNewOrOverrideExistingShop(Shop shop){
		return super.saveNewEntityAndReturnOldEntityIfAny(shop);
	}
	
	@Override
	public Set<Shop> getAllShops() {
		return super.getAll();
	}
	

	@Override
	public Shop getShop(String shopName) {
		Set<Shop> shopList = super.getAll();
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
