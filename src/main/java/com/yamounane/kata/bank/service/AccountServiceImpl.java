package com.yamounane.kata.bank.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

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

	public AccountServiceImpl() {
		this.operationService = new OperationServiceImpl();
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
		if(customer != null && accountId != null && !accountId.isEmpty()) {
			Account customerAccount = getAccount(customer, accountId);
			operationService.addDepositOperation(customerAccount, amount, operationId);
			return;
		}
		throw new AccountException(String.format("Unable to make a deposit because account %s or customer %s are null", accountId, customer));
	}

	private Account getAccount(Customer customer, String accountId) throws AccountException {
		Account customerAccount = getSelectedAccountFromCustomerAccounts(customer.getAccounts(), accountId);
		
		if(customerAccount != null) {
			return customerAccount;
		}
		throw new AccountException(String.format("Unable to get Account because account %s doesn't exist for customer %s", accountId, customer));
	}

	private Account getSelectedAccountFromCustomerAccounts(List<Account> accounts, String accountId) {
		Optional<Account> optAccount = accounts.
											stream()
												.filter(account -> account.getId().equals(accountId))
													.findFirst();
		if(optAccount.isPresent()) {
			return optAccount.get();
		}
		
		return null;
	}
	
}
