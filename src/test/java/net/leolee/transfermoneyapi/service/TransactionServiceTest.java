/*
 * Author: Leo Lee
 */
package net.leolee.transfermoneyapi.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import net.leolee.transfermoneyapi.entity.CustomerAccount;
import net.leolee.transfermoneyapi.exception.AccountNotFoundException;
import net.leolee.transfermoneyapi.exception.InsufficientBalanceException;
import net.leolee.transfermoneyapi.exception.SameAccountTransferException;
import net.leolee.transfermoneyapi.exception.TransferAmountInvalidException;
import net.leolee.transfermoneyapi.respository.AccountRepository;

@RunWith(MockitoJUnitRunner.Silent.class)
public class TransactionServiceTest {
	
	@Rule
	public final ExpectedException thrown = ExpectedException.none();
	
	@Mock
	AccountRepository accountRepository;
	
	@InjectMocks
	TransactionService transactionService = new TransactionService();
	
	/*
	 * Valid case: 
	 */
	
	@Test
	public void testTransferMoneyValid() throws Exception {
		
		String fromAccountNo = "X100001";
		String toAccountNo = "X100002";
		BigDecimal amount = new BigDecimal("2000");
		
		BigDecimal fromAccountAmount = new BigDecimal("10000");
		BigDecimal toAccountAmount = new BigDecimal("1000");
		
		CustomerAccount fromAccount = new CustomerAccount();
		fromAccount.setAccountNo(fromAccountNo);
		fromAccount.setBalanceAmt(fromAccountAmount);
		
		CustomerAccount toAccount = new CustomerAccount();
		toAccount.setAccountNo(toAccountNo);
		toAccount.setBalanceAmt(toAccountAmount);
		
		when(accountRepository.findById(toAccountNo)).thenReturn(Optional.of(toAccount));
		
		when(accountRepository.findById(fromAccountNo)).thenReturn(Optional.of(fromAccount));
		
		transactionService.transferMoney(fromAccountNo, toAccountNo, amount);
		
		assertEquals(new BigDecimal("8000"), fromAccount.getBalanceAmt());
		assertEquals(new BigDecimal("3000"), toAccount.getBalanceAmt());
		
	}
	
	
	/*
	 * Invalid case: 
	 * fromAccount not found
	 */
	
	@Test
	public void testTransferMoneyFromAccountNotFound() throws Exception {
		
		thrown.expect(AccountNotFoundException.class);
		thrown.expectMessage("Account <X100001> does not exist !");

		String fromAccountNo = "X100001";
		String toAccountNo = "X100002";
		BigDecimal amount = new BigDecimal("10000");
		
		when(accountRepository.findById(fromAccountNo)).thenReturn(Optional.empty());
		
		CustomerAccount toAccount = new CustomerAccount();
		toAccount.setAccountNo(toAccountNo);
		
		when(accountRepository.findById(toAccountNo)).thenReturn(Optional.of(toAccount));
		
		transactionService.transferMoney(fromAccountNo, toAccountNo, amount);
	}
	
	/*
	 * Invalid case: 
	 * ToAccount not found
	 */
	
	@Test
	public void testTransferMoneyToAccountNotFound() throws Exception {
		
		thrown.expect(AccountNotFoundException.class);
		thrown.expectMessage("Account <X100002> does not exist !");

		String fromAccountNo = "X100001";
		String toAccountNo = "X100002";
		BigDecimal amount = new BigDecimal("10000");
		
		CustomerAccount fromAccount = new CustomerAccount();
		fromAccount.setAccountNo(fromAccountNo);
		
		when(accountRepository.findById(toAccountNo)).thenReturn(Optional.empty());
		
		when(accountRepository.findById(fromAccountNo)).thenReturn(Optional.of(fromAccount));
		
		transactionService.transferMoney(fromAccountNo, toAccountNo, amount);
	}
	
	/*
	 * Invalid case: 
	 * Sender account is the same as recipient account
	 */
	
	@Test
	public void testTransferMoneySameAccounts() throws Exception {
		
		thrown.expect(SameAccountTransferException.class);
		thrown.expectMessage("Recipient account <X100001> cannot be the same as sender account!");
		
		String fromAccountNo = "X100001";
		String toAccountNo = "X100001";
		BigDecimal amount = new BigDecimal("10000");
		
		transactionService.transferMoney(fromAccountNo, toAccountNo, amount);
	}
	
	/*
	 * Invalid case: 
	 * FromAccount Balance insufficent
	 */
	
	@Test
	public void testTransferMoneyInsufficentBalance() throws Exception {
		
		thrown.expect(InsufficientBalanceException.class);
		thrown.expectMessage("Account <X100001> does not have sufficient balance to transfer !");

		String fromAccountNo = "X100001";
		String toAccountNo = "X100002";
		BigDecimal amount = new BigDecimal("10000");
		
		BigDecimal fromAccountAmount = new BigDecimal("5000");
		
		CustomerAccount fromAccount = new CustomerAccount();
		fromAccount.setAccountNo(fromAccountNo);
		fromAccount.setBalanceAmt(fromAccountAmount);
		
		CustomerAccount toAccount = new CustomerAccount();
		toAccount.setAccountNo(toAccountNo);
		
		when(accountRepository.findById(toAccountNo)).thenReturn(Optional.of(toAccount));
		
		when(accountRepository.findById(fromAccountNo)).thenReturn(Optional.of(fromAccount));
		
		transactionService.transferMoney(fromAccountNo, toAccountNo, amount);
	}
	
	/*
	 * Invalid case: 
	 * Transferred Amount is not valid
	 */
	
	@Test
	public void testTransferMoneyAmountInvalid() throws Exception {
		
		thrown.expect(TransferAmountInvalidException.class);
		thrown.expectMessage("Transferred Amount <0> is invalid !");

		String fromAccountNo = "X100001";
		String toAccountNo = "X100002";
		BigDecimal amount = new BigDecimal("0");
		
		BigDecimal fromAccountAmount = new BigDecimal("5000");
		
		CustomerAccount fromAccount = new CustomerAccount();
		fromAccount.setAccountNo(fromAccountNo);
		fromAccount.setBalanceAmt(fromAccountAmount);
		
		CustomerAccount toAccount = new CustomerAccount();
		toAccount.setAccountNo(toAccountNo);
		
		when(accountRepository.findById(toAccountNo)).thenReturn(Optional.of(toAccount));
		
		when(accountRepository.findById(fromAccountNo)).thenReturn(Optional.of(fromAccount));
		
		transactionService.transferMoney(fromAccountNo, toAccountNo, amount);
	}

}
