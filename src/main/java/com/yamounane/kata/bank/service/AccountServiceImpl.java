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
import com.yamounane.kata.bank.model.Operation;
import com.yamounane.kata.bank.model.OperationType;

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
		if (customer != null && account != null) {
			computeBalance(account);
			return account.getBalance();
		}

		throw new AccountException(
				String.format("Unable to get because account %s or customer %s are null", account, customer));
	}

	@Override
	public String getStatement(Customer customer, Account account) throws AccountException {
		if (customer != null && account != null) {
			List<String> statements = Arrays.asList(getJoinedStatementFromAccount(account).split(";"));
			return statements.stream().map(statement -> statement + "\n").collect(Collectors.joining());
		}

		throw new AccountException(String.format(
				"Unable to retrieve a statement because account %s or customer %s are null", account, customer));
	}

	@Override
	public void withdraw(Customer customer, Account account, BigDecimal amount) throws AccountException {
		if (customer != null && account != null) {
			Operation withdrawOperation = operationService.addWithdrawOperation(amount, UUID.randomUUID().toString());
			account.getOperations().add(withdrawOperation);
			computeBalance(account);
			return;
		}

		throw new AccountException(String.format("Unable to make a deposit because account %s or customer %s are null",
				account, customer));
	}

	@Override
	public void deposit(Customer customer, Account account, BigDecimal amount) throws AccountException {
		if (customer != null && account != null) {
			Operation depositOperation = operationService.addDepositOperation(amount, UUID.randomUUID().toString());
			account.getOperations().add(depositOperation);
			computeBalance(account);
			return;
		}

		throw new AccountException(String.format("Unable to make a deposit because account %s or customer %s are null",
				account, customer));
	}

	private String getJoinedStatementFromAccount(Account account) throws AccountException {
		String statement = account.getOperations().stream().map(operation -> operation.toString())
				.collect(Collectors.joining(";"));

		return statement;
	}

	private void computeBalance(Account account) {
		double debit = getSumOperationFromType(account, OperationType.DEBIT);
		double credit = getSumOperationFromType(account, OperationType.CREDIT);
		account.setBalance(new BigDecimal(credit - debit));
	}

	private double getSumOperationFromType(Account account, OperationType type) {
		return account.getOperations().stream().filter(operation -> type.equals(operation.getType()))
				.mapToDouble(operation -> operation.getAmount().doubleValue()).sum();
	}

}
