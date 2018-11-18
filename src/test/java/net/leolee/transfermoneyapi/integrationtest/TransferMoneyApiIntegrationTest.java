/*
 * Author: Leo Lee
 */
package net.leolee.transfermoneyapi.integrationtest;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Base64;

import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import net.leolee.transfermoneyapi.entity.CustomerAccount;
import net.leolee.transfermoneyapi.message.BalanceMessage;
import net.leolee.transfermoneyapi.respository.AccountRepository;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
public class TransferMoneyApiIntegrationTest {
	
	@Autowired
	private AccountRepository accountRepo;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Before
	public void setUp() {
		CustomerAccount acct1 = new CustomerAccount();
		acct1.setAccountNo("T10001");
		acct1.setBalanceAmt(new BigDecimal("10000"));
		
		CustomerAccount acct2 = new CustomerAccount();
		acct2.setAccountNo("T10002");
		acct2.setBalanceAmt(new BigDecimal("20000"));
				
		accountRepo.save(acct1);
		accountRepo.save(acct2);
	}
	
	@Test
	public void retrieveBalanceTest() throws Exception {
		
		String accountNo = "T10001";
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
				"/transferMoneyApi/v1/account/" + accountNo + "/balance")
				.headers(getHeaders())
				.accept(MediaType.APPLICATION_JSON).contextPath("/transferMoneyApi");

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		System.out.println(result.getResponse());
	
		BalanceMessage actualBalanceMessage = new ObjectMapper().readValue(result.getResponse().getContentAsString(), BalanceMessage.class);
	
		assertEquals(HttpServletResponse.SC_OK, result.getResponse().getStatus());
		assertEquals(new BigDecimal("10000.00"), actualBalanceMessage.getBalanceAmt());
		assertEquals(accountNo, actualBalanceMessage.getAccountNo());
	}
	
	@Test
	public void transferMoneyTest() throws Exception{
		
		String transferMoneyJson = "{\"toAccountNumber\":\"T10002\",\"amount\": 100.0}";
		
		String fromAccountNo = "T10001";
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/transferMoneyApi/v1/account/" + fromAccountNo + "/transferMoney")
				.headers(getHeaders())
				.accept(MediaType.APPLICATION_JSON).content(transferMoneyJson)
				.contentType(MediaType.APPLICATION_JSON).contextPath("/transferMoneyApi");

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		System.out.println(result.getResponse());
		
		assertEquals(HttpServletResponse.SC_ACCEPTED, result.getResponse().getStatus());
		
	}
	
	private static HttpHeaders getHeaders(){
        String plainCredentials="system:abc123";
        String base64Credentials = Base64.getEncoder().encodeToString(plainCredentials.getBytes());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + base64Credentials);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        return headers;
    }
	
}
