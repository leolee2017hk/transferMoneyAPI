package net.leolee.transfermoneyapi.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.TimeZone;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import java.text.SimpleDateFormat;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.MessageFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import net.leolee.transfermoneyapi.entity.CustomerAccount;
import net.leolee.transfermoneyapi.exception.AccountNotFoundException;
import net.leolee.transfermoneyapi.exception.InsufficientBalanceException;
import net.leolee.transfermoneyapi.exception.SameAccountTransferException;
import net.leolee.transfermoneyapi.exception.TransferAmountInvalidException;
import net.leolee.transfermoneyapi.message.BalanceMessage;
import net.leolee.transfermoneyapi.message.MoneyTransferObject;
import net.leolee.transfermoneyapi.service.AccountService;
import net.leolee.transfermoneyapi.service.TransactionService;

@RestController
public class TransferMoneyApiController {
	
	@Value("${account.retrieve.balance.url}")
    private String accountBalanceURL;
	
	@Value("${system.base64Credential}")
    private String base64Credentials;
	
	@Autowired
    AccountService accountService;
	
	@Autowired
	TransactionService transactionService;
	
	@GetMapping(value="v1/account/{accountNo}/balance")
    public BalanceMessage retrieveBalance(@PathVariable String accountNo, HttpServletResponse response) {
			
		TimeZone tz = TimeZone.getDefault();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		df.setTimeZone(tz);
		String nowAsISO = df.format(new Date());
		
		BalanceMessage msg = new BalanceMessage();
		msg.setRetrievalDateTime(nowAsISO);
		
		try{
			CustomerAccount account = accountService.findAccount(accountNo);
			msg.setAccountNo(account.getAccountNo());
			msg.setBalanceAmt(account.getBalanceAmt());
				
		}catch (AccountNotFoundException e) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			msg.setMessage(e.getMessage());
			msg.setErrorCode(e.getErrorCode().toString());
		} 
		
		return msg;
    }
	
	@PostMapping(value="v1/account/{fromAccountNo}/transferMoney")
    public BalanceMessage transferMoney(@PathVariable String fromAccountNo, 
    										@Valid @RequestBody MoneyTransferObject moneyTransfer, 
    											HttpServletResponse response) {
		BalanceMessage failedMessage = new BalanceMessage();
		boolean isSuccess = false;
		
		try {
			BigDecimal amount = moneyTransfer.getAmount();
			
			String toAccountNo = moneyTransfer.getToAccountNumber();
			
			isSuccess = transactionService.transferMoney(fromAccountNo, toAccountNo, amount);
			response.setStatus(HttpServletResponse.SC_ACCEPTED);
		} catch (AccountNotFoundException e) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			failedMessage.setMessage(e.getMessage());
			failedMessage.setErrorCode(e.getErrorCode().toString());
		} catch (InsufficientBalanceException 
				| SameAccountTransferException 
				| TransferAmountInvalidException e) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			failedMessage.setMessage(e.getMessage());
			failedMessage.setErrorCode(e.getErrorCode().toString());
		} 
		
		//retrieve the resulting balance after Money transfer
		if (isSuccess){
			RestTemplate restTemplate = new RestTemplate();
			
			HttpHeaders headers = new HttpHeaders();
			headers.add("Authorization", "Basic " + base64Credentials);
	        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
			
			
			HttpEntity entity = new HttpEntity(headers);
			
			String getBalanceURL = MessageFormat.format(accountBalanceURL, fromAccountNo);

			ResponseEntity<BalanceMessage> serverResponse = restTemplate.exchange(
					getBalanceURL, HttpMethod.GET, entity, BalanceMessage.class);
			
			return serverResponse.getBody();
		}else{
			return failedMessage;
		}
		
		
		
    }
	
}
