package com.db.retail.domain;

public class Shop {

	private String shopName;
	
	private Address shopAddress;
	
	private GeoDetails storeGeoDetails;

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public Address getShopAddress() {
		return shopAddress;
	}

	public void setShopAddress(Address shopAddress) {
		this.shopAddress = shopAddress;
	}

	public GeoDetails getStoreGeoDetails() {
		return storeGeoDetails;
	}

	public void setStoreGeoDetails(GeoDetails storeGeoDetails) {
		this.storeGeoDetails = storeGeoDetails;
	}
	
}
