package com.db.retail.exception;

public enum ErrorCodes {
	
	UNKNOWN_ERROR("system error"),
	NO_SHOP_IN_THE_SYSTEM("no shop is available in the system"),
	INSUFFICIENT_SHOP_INFO("mandatory shop info missing in input request"),
	INSUFFICIENT_GEO_INFO("latitude/longitude missing"),
	INVALID_ADDRESS("address not found"),
	GEOCODE_TEMP_ERROR("unable to fetch geocode, please try later"),
	GEOCODE_FETCH_ERROR("unable to fetch geocode");
	
	
	private String errorMsg;

	private ErrorCodes(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getErrorMsg() {
		return errorMsg;
	}
	

}
