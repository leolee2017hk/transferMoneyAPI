package net.leolee.transfermoneyapi.exception;

public enum ErrorCode {
	E000("Bad Request"),
	E001("Account not found"), 
	E002("Transferred Amount is not valid"), 
	E003("Insufficient Balance"), 
	E004("Same Account Transfer"),
	E101("Access denied"),
	E901("Media type is not supported"),
	E902("No handler is found to support the request"),
	E999("System Exception");
	
	private String errorDesc;
	
	ErrorCode(String errorDesc){
		this.errorDesc = errorDesc;
	}
}
