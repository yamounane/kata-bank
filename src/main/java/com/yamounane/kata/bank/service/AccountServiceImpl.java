package com.yamounane.kata.bank.service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.yamounane.kata.bank.AccountService;
import com.yamounane.kata.bank.exception.AccountException;
import com.yamounane.kata.bank.model.Account;
import com.yamounane.kata.bank.model.Customer;

/**
 * Account Service implementation
 * 
 * @author Yassine Amounane
 */
public class AccountServiceImpl implements AccountService {
	
	private OperationService operationService;

	public AccountServiceImpl(OperationService operationService) {
		this.operationService = operationService;
	}
	
	@Override
	public BigDecimal getBalance(Customer customer, Account account) throws AccountException {
		if(customer != null && account != null) {
			return operationService.computeBalance(account);
		}
		
		throw new AccountException(String.format("Unable to get because account %s or customer %s are null", account, customer));
	}

	@Override
	public String getStatement(Customer customer, Account account) throws AccountException {
		if(customer != null && account != null) {
			List<String> statements = Arrays.asList(operationService.getStatement(account).split(";"));
			return statements.stream()
								.map(statement -> statement + "\n")
									.collect(Collectors.joining());
		}
		
		throw new AccountException(String.format("Unable to retrieve a statement because account %s or customer %s are null", account, customer));
	}

	@Override
	public void withdraw(Customer customer, Account account, BigDecimal amount) throws AccountException {
		if(customer != null && account != null) {
			operationService.addWithdrawOperation(account, amount, UUID.randomUUID().toString());
			return;
		}
		
		throw new AccountException(String.format("Unable to make a deposit because account %s or customer %s are null", account, customer));
	}

	@Override
	public void deposit(Customer customer, Account account, BigDecimal amount) throws AccountException {
		if(customer != null && account != null) {
			operationService.addDepositOperation(account, amount, UUID.randomUUID().toString());
			return;
		}
		
		throw new AccountException(String.format("Unable to make a deposit because account %s or customer %s are null", account, customer));
	}

}
