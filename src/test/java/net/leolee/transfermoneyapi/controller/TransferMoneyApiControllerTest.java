package net.leolee.transfermoneyapi.controller;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import net.leolee.transfermoneyapi.entity.CustomerAccount;
import net.leolee.transfermoneyapi.service.AccountService;
import net.leolee.transfermoneyapi.service.TransactionService;



@RunWith(SpringRunner.class)
@WebMvcTest(value = TransferMoneyApiController.class, secure = false)
public class TransferMoneyApiControllerTest {
	
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private AccountService accountService;
	
	@MockBean
	private TransactionService transactionService;
	
	@Test
	public void retrieveBalanceTest() throws Exception {

		CustomerAccount customerAccount = new CustomerAccount();
		customerAccount.setAccountNo("A10005");
		customerAccount.setBalanceAmt(new BigDecimal("10000"));
		
		Mockito.when(
				accountService.findAccount(Mockito.anyString())).thenReturn(customerAccount);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
				"/v1/account/A10005/balance").accept(
				MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		System.out.println(result.getResponse());
		
		assertEquals(HttpServletResponse.SC_OK, result.getResponse().getStatus());
		
	}
	
	@Test
	public void transferMoneyTest() throws Exception{
		
		String transferMoneyJson = "{\"toAccountNumber\":\"A10002\",\"amount\": 100.0}";
		
		Mockito.when(Mockito.spy(transactionService).transferMoney(Mockito.anyString(), 
				Mockito.anyString(), Mockito.any(BigDecimal.class))).thenReturn(true);;
				
		String fromAccountNo = "A10001";
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/v1/account/" + fromAccountNo + "/transferMoney")
				.accept(MediaType.APPLICATION_JSON).content(transferMoneyJson)
				.contentType(MediaType.APPLICATION_JSON);
		

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		System.out.println(result.getResponse());
		
		assertEquals(HttpServletResponse.SC_ACCEPTED, result.getResponse().getStatus());
		
	}
	
}
