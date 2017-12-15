package com.yamounane.kata.bank.service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
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
	public BigDecimal getBalance(Customer customer, String accountId) throws AccountException {
		if(customer != null && accountId != null && !accountId.isEmpty()) {
			return operationService.computeBalance(getAccount(customer, accountId));
		}
		throw new AccountException(String.format("Unable to get because account %s or customer %s are null", accountId, customer));
	}

	@Override
	public String getStatement(Customer customer, String accountId) throws AccountException {
		if(customer != null && accountId != null && !accountId.isEmpty()) {
			List<String> statements = Arrays.asList(operationService.getStatement(getAccount(customer, accountId)).split(";"));
			return statements.stream()
								.map(statement -> statement + "\n")
									.collect(Collectors.joining());
		}
		throw new AccountException(String.format("Unable to retrieve a statement because account %s or customer %s are null", accountId, customer));
	}

	@Override
	public void withdraw(Customer customer, String accountId, BigDecimal amount, String operationId)
			throws AccountException {
		if(customer != null && accountId != null && !accountId.isEmpty()) {
			Account customerAccount = getAccount(customer, accountId);
			operationService.addWithdrawOperation(customerAccount, amount, operationId);
			return;
		}
		throw new AccountException(String.format("Unable to make a deposit because account %s or customer %s are null", accountId, customer));
	}

	@Override
	public void deposit(Customer customer, Account account, BigDecimal amount, String operationId)
			throws AccountException {
		if(customer != null && account != null) {
			operationService.addDepositOperation(account, amount, operationId);
			return;
		}
		throw new AccountException(String.format("Unable to make a deposit because account %s or customer %s are null", account, customer));
	}

	private Account getAccount(Customer customer, String accountId) throws AccountException {
		Account customerAccount = getSelectedAccountFromCustomerAccounts(customer.getAccounts(), accountId);
		
		if(customerAccount != null) {
			return customerAccount;
		}
		throw new AccountException(String.format("Unable to get Account because account %s doesn't exist for customer %s", accountId, customer));
	}

	private Account getSelectedAccountFromCustomerAccounts(Set<Account> accounts, String accountId) {
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
