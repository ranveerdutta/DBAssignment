package com.db.retail.exception;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Generic exception handler for Rest layer
 * @author ranveer
 *
 */
@ControllerAdvice
public class DBRetailExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(Exception.class)
    @ResponseBody
    ResponseEntity<Object> handleControllerException(HttpServletRequest req, Exception ex) {
		Map<String,String> responseBody = new HashMap<>();
    	responseBody.put("status", "fail");
        if(ex instanceof RetailSystemException) {
        	responseBody.put("message", ((RetailSystemException)ex).getErrorCode().getErrorMsg());
        }else {
        	responseBody.put("message", ErrorCodes.UNKNOWN_ERROR.getErrorMsg());
        }
        
        return new ResponseEntity<Object>(responseBody,HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
