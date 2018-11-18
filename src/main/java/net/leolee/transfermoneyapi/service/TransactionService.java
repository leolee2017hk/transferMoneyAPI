/*
 * Author: Leo Lee
 */
package net.leolee.transfermoneyapi.service;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.leolee.transfermoneyapi.entity.CustomerAccount;
import net.leolee.transfermoneyapi.exception.AccountNotFoundException;
import net.leolee.transfermoneyapi.exception.InsufficientBalanceException;
import net.leolee.transfermoneyapi.exception.SameAccountTransferException;
import net.leolee.transfermoneyapi.exception.TransferAmountInvalidException;
import net.leolee.transfermoneyapi.respository.AccountRepository;

@Service
public class TransactionService {
	
	@Autowired
    AccountRepository accountRepository;
	
	@Transactional
	public boolean transferMoney(String fromAccountNo, String toAccountNo, BigDecimal amount) 
									throws AccountNotFoundException, 
											InsufficientBalanceException, 
												SameAccountTransferException, 
													TransferAmountInvalidException{
		
		if (fromAccountNo.equals(toAccountNo)){
			throw new SameAccountTransferException(toAccountNo);
		}
		
		if (amount.floatValue() <= 0){
			throw new TransferAmountInvalidException(amount);
		}
		
		
		Optional<CustomerAccount> fromAccountOpt = accountRepository.findById(fromAccountNo);
		CustomerAccount fromAccount = null;
		if (fromAccountOpt.isPresent()){
			fromAccount = fromAccountOpt.get();
		}else{
			throw new AccountNotFoundException(fromAccountNo);
		}
		
		Optional<CustomerAccount> toAccountOpt = accountRepository.findById(toAccountNo);
		CustomerAccount toAccount = null;
		if (toAccountOpt.isPresent()){
			toAccount = toAccountOpt.get();
		}else{
			throw new AccountNotFoundException(toAccountNo);
		}
		
		BigDecimal newAmount = fromAccount.getBalanceAmt().subtract(amount);
		if (newAmount.floatValue() < 0){
			throw new InsufficientBalanceException(fromAccount.getAccountNo());
		}
		
		fromAccount.setBalanceAmt(newAmount);
		toAccount.setBalanceAmt(toAccount.getBalanceAmt().add(amount));
		
		return true;
	}
}
