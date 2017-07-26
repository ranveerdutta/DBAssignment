package com.db.retail.domain;


/**
 * POJO class  representing shop entity
 * @author ranveer
 *
 */
public class Shop {

	private String shopName;
	
	private Address shopAddress;
	
	private GeoDetails shopGeoDetails;

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

	public GeoDetails getShopGeoDetails() {
		return shopGeoDetails;
	}

	public void setShopGeoDetails(GeoDetails shopGeoDetails) {
		this.shopGeoDetails = shopGeoDetails;
	}
	
	public String createShopAddressString() {
		StringBuilder str = new StringBuilder(this.getShopName());
		str.append(" ");
		str.append(this.getShopAddress().createAddressString());
		return str.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((shopName == null) ? 0 : shopName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Shop other = (Shop) obj;
		if (shopName == null) {
			if (other.shopName != null)
				return false;
		} else if (!shopName.equals(other.shopName))
			return false;
		return true;
	}

	
	
}
