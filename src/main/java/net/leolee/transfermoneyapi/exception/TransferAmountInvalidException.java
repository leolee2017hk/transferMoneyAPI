/*
 * Author: Leo Lee
 */
package net.leolee.transfermoneyapi.exception;

import java.math.BigDecimal;

public class TransferAmountInvalidException extends TransferMoneyBaseException {
	
	public TransferAmountInvalidException(BigDecimal amount){
		super("Transferred Amount <" + amount.toString() + "> is invalid !");
	}
	
	@Override
	public ErrorCode getErrorCode() {
		return ErrorCode.E002;
	}
}
