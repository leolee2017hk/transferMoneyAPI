package net.leolee.transfermoneyapi.exception;

public abstract class TransferMoneyBaseException extends Exception {
	
	private final String message;
	
	public TransferMoneyBaseException(String msg){
		super(msg);
		this.message = msg;
	}
	
	public abstract ErrorCode getErrorCode();
}
