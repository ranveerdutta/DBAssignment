package com.db.retail.exception;

public enum ErrorCodes {
	
	UNKNOWN_ERROR("system error"),
	INSUFFICIENT_SHOP_INFO("mandatory shop info missing in input request"),
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
