package net.leolee.transfermoneyapi.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.leolee.transfermoneyapi.entity.CustomerAccount;
import net.leolee.transfermoneyapi.exception.AccountNotFoundException;
import net.leolee.transfermoneyapi.respository.AccountRepository;

@Service
public class AccountService {
	
	@Autowired
    AccountRepository accountRepository;
	
	public CustomerAccount findAccount(String accountNo) throws AccountNotFoundException{
		
		Optional<CustomerAccount> accountOpt = accountRepository.findById(accountNo);
		if (accountOpt.isPresent()){
			return accountOpt.get();
		}else{
			throw new AccountNotFoundException(accountNo);
		}
	}
}
