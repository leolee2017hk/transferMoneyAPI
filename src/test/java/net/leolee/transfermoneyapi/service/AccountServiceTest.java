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
import net.leolee.transfermoneyapi.respository.AccountRepository;

@RunWith(MockitoJUnitRunner.Silent.class)
public class AccountServiceTest {
	
	@Rule
	public final ExpectedException thrown = ExpectedException.none();
	
	@Mock
	AccountRepository accountRepository;
	
	@InjectMocks
	AccountService accountService = new AccountService();
	
	/*
	 * Valid case: 
	 */
	
	@Test
	public void testFindAccountSuccess() throws Exception {
		
		String accountNo = "X100001";
		BigDecimal amount = new BigDecimal("2000");
		
		CustomerAccount expectedAccount = new CustomerAccount();
		expectedAccount.setAccountNo(accountNo);
		expectedAccount.setBalanceAmt(amount);
		
		when(accountRepository.findById(accountNo)).thenReturn(Optional.of(expectedAccount));
		
		CustomerAccount actualAccount = accountService.findAccount(accountNo);
		
		assertEquals(amount, actualAccount.getBalanceAmt());
		assertEquals(accountNo, actualAccount.getAccountNo());
		
	}
	
	
	/*
	 * Invalid case: 
	 * Account not found
	 */
	
	@Test
	public void testFindAccountFailed() throws Exception {
		
		thrown.expect(AccountNotFoundException.class);
		thrown.expectMessage("Account <X100001> does not exist !");

		String accountNo = "X100001";
		
		when(accountRepository.findById(accountNo)).thenReturn(Optional.empty());
		
		accountService.findAccount(accountNo);
	}

}
