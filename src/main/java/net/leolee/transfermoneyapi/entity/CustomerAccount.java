package net.leolee.transfermoneyapi.entity;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class CustomerAccount {
	
	@Id
	private String accountNo;
	
	private BigDecimal balanceAmt;
	
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


}
