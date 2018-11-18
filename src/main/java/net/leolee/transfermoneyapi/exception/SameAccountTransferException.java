/*
 * Author: Leo Lee
 */
package net.leolee.transfermoneyapi.exception;

public class SameAccountTransferException extends TransferMoneyBaseException {
	private String toAccountNo;
	
	public SameAccountTransferException(String toAccountNo){
		super("Recipient account <" + toAccountNo + "> cannot be the same as sender account!");
		this.toAccountNo = toAccountNo;
	}
	
	@Override
	public ErrorCode getErrorCode() {
		return ErrorCode.E004;
	}
}
