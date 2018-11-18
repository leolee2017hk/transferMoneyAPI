package net.leolee.transfermoneyapi.message;

import org.springframework.http.HttpStatus;

public class ApiError {

	private String message;
    private String errorCode;
 
    public ApiError(String message, String errorCode) {
        super();
        this.message = message;
        this.errorCode = errorCode;
    }

	public String getMessage() {
		return message;
	}

	public String getErrorCode() {
		return errorCode;
	}
 
}
