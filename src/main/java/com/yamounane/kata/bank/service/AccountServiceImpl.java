package com.yamounane.kata.bank.service;

import java.math.BigDecimal;

import com.yamounane.kata.bank.AccountService;
import com.yamounane.kata.bank.exception.AccountException;
import com.yamounane.kata.bank.model.Customer;

/**
 * Account Service implementation
 * 
 * @author Yassine Amounane
 */
public class AccountServiceImpl implements AccountService {

	public AccountServiceImpl() {
	}
	
	@Override
	public BigDecimal getBalance(Customer customer, String accountId) throws AccountException {
		return null;
	}

	@Override
	public void printStatement(Customer customer, String accountId) throws AccountException {

	}

	@Override
	public void withdraw(Customer customer, String accountId, BigDecimal amount, String operationId)
			throws AccountException {

	}

	@Override
	public void deposit(Customer customer, String accountId, BigDecimal amount, String operationId)
			throws AccountException {

	}

}
