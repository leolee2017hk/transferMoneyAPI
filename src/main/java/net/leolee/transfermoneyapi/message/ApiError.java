/*
 * Author: Leo Lee
 */
package net.leolee.transfermoneyapi.message;

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
