package com.db.retail.exception;

public class RetailSystemException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ErrorCodes errorCode;

	public RetailSystemException(ErrorCodes errorCode) {
		super();
		this.errorCode = errorCode;
	}

	public ErrorCodes getErrorCode() {
		return errorCode;
	}
	

}
