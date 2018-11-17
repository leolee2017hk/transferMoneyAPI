package net.leolee.transfermoneyapi.exception;

public enum ErrorCode {
	E001("Account not found"), 
	E002("Transferred Amount is not valid"), 
	E003("Insufficient Balance"), 
	E004("Same Account Transfer");
	
	private String errorDesc;
	
	ErrorCode(String errorDesc){
		this.errorDesc = errorDesc;
	}
}
