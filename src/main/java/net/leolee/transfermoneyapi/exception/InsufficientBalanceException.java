/*
 * Author: Leo Lee
 */
package net.leolee.transfermoneyapi.exception;

public class InsufficientBalanceException extends TransferMoneyBaseException {
	
	private String accountNo;
	
	public InsufficientBalanceException(String accountNo){
		super("Account <" + accountNo + "> does not have sufficient balance to transfer !");
		this.accountNo = accountNo;
	}
	
	@Override
	public ErrorCode getErrorCode() {
		return ErrorCode.E003;
	}
}
