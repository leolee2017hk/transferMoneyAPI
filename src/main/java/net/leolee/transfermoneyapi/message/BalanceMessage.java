package net.leolee.transfermoneyapi.message;

import java.math.BigDecimal;

public class BalanceMessage {
	
	private String accountNo;
	private BigDecimal balanceAmt;
	private String retrievalDateTime;
	private String message;
	private String errorCode;
	
	public String getAccountNo() {
		return accountNo;
	}
	
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	
	public BigDecimal getBalanceAmt() {
		return balanceAmt;
	}
	
	public void setBalanceAmt(BigDecimal balanceAmt) {
		this.balanceAmt = balanceAmt;
	}
	
	public String getRetrievalDateTime() {
		return retrievalDateTime;
	}
	
	public void setRetrievalDateTime(String retrievalDateTime) {
		this.retrievalDateTime = retrievalDateTime;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public String getErrorCode() {
		return errorCode;
	}
	
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	

}
