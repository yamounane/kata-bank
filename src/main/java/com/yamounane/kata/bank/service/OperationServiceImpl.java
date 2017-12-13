package com.yamounane.kata.bank.service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.Instant;
import java.util.stream.Collectors;

import com.yamounane.kata.bank.exception.AccountException;
import com.yamounane.kata.bank.model.Account;
import com.yamounane.kata.bank.model.Operation;
import com.yamounane.kata.bank.model.OperationType;

/**
 * Operation Service implementation
 * 
 * @author Yassine Amounane
 */
public class OperationServiceImpl implements OperationService {

	@Override
	public void addDepositOperation(Account account, BigDecimal value, String operationId) throws AccountException {
		// Ignore operation with amount = 0
		if(value.compareTo(BigDecimal.ZERO) != 0) {
			Operation operation = new Operation(operationId, OperationType.CREDIT, Instant.now(), value.abs());
			account.getOperations().add(operation);
			computeBalance(account);
		}
	}
	
	@Override
	public void addWithdrawOperation(Account account, BigDecimal value, String operationId) throws AccountException {
		// Ignore operation with amount = 0
		if(value.compareTo(BigDecimal.ZERO) != 0) {
			Operation operation = new Operation(operationId, OperationType.DEBIT, Instant.now(), value.abs());
			account.getOperations().add(operation);
			computeBalance(account);
		}
	}
	
	@Override
	public String getStatement(Account account) throws AccountException {
	    DecimalFormat amountFormater = new DecimalFormat("#,###.00");
		String statement = account.getOperations()
									.stream()
										.map(operation -> String.format("%s %s %s \n", operation.getId(), operation.getType().name(), amountFormater.format(operation.getAmount())))
											.collect(Collectors.joining());

		return statement;
	}
	
	@Override
	public BigDecimal computeBalance(Account account){
		double debit = getSumOperationFromType(account, OperationType.DEBIT);
		double credit = getSumOperationFromType(account, OperationType.CREDIT);
		account.setBalance(new BigDecimal(credit - debit));
		return account.getBalance(); 
	}
	
	private double getSumOperationFromType(Account account, OperationType type) {
		return account.getOperations()
							.stream()
								.filter(operation -> type.equals(operation.getType()))
									.mapToDouble(operation -> operation.getAmount().doubleValue())
										.sum();
	}
	
}
