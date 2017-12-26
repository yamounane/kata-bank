package com.yamounane.kata.bank.service;

import java.math.BigDecimal;
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
		if(value.compareTo(BigDecimal.ZERO) != 0) {
			account.getOperations().add(new Operation(operationId, OperationType.CREDIT, Instant.now(), value.abs()));
			computeBalance(account);
		}
	}
	
	@Override
	public void addWithdrawOperation(Account account, BigDecimal value, String operationId) throws AccountException {
		if(value.compareTo(BigDecimal.ZERO) != 0) {
			account.getOperations().add(new Operation(operationId, OperationType.DEBIT, Instant.now(), value.abs()));
			computeBalance(account);
		}
	}
	
	@Override
	public String getStatement(Account account) throws AccountException {
		String statement = account.getOperations()
									.stream()
										.map(operation -> operation.toString())
											.collect(Collectors.joining(";"));

		return statement;
	}
	
	@Override
	public BigDecimal computeBalance(Account account){
		double debit = getSumOperationFromType(account, OperationType.DEBIT);
		double credit = getSumOperationFromType(account, OperationType.CREDIT);
		
		return new BigDecimal(credit - debit);
	}
	
	private double getSumOperationFromType(Account account, OperationType type) {
		return account.getOperations()
							.stream()
								.filter(operation -> type.equals(operation.getType()))
									.mapToDouble(operation -> operation.getAmount().doubleValue())
										.sum();
	}
	
}
