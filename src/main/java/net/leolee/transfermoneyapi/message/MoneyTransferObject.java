/*
 * Author: Leo Lee
 */
package net.leolee.transfermoneyapi.message;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class MoneyTransferObject {
	

	@NotNull(message="ToAccount Number field must be presented.")
	@NotBlank(message="Credit Account cannot be blank.")
	public String toAccountNumber;
	
	@NotNull(message="Transfer Amount field must be presented")
	@Positive(message="Transfering Amount Must be positive")
	public BigDecimal amount;
	
	public String getToAccountNumber() {
		return toAccountNumber;
	}

	public void setToAccountNumber(String toAccountNumber) {
		this.toAccountNumber = toAccountNumber;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}


}
