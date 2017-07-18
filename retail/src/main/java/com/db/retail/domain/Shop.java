package com.db.retail.domain;

public class Shop {

	//assuming shop name is unique
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
	
	public String createShopAddressString() {
		StringBuilder str = new StringBuilder(this.getShopName());
		str.append(" ");
		str.append(this.getShopAddress().createAddressString());
		return str.toString();
	}
	
}
