package com.db.retail.domain;

public class Address {

	private String number;
	
	private String postCode;

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}
	
	public String createAddressString() {
		return this.getNumber() + " " + this.getPostCode();
	}
	
	
}
