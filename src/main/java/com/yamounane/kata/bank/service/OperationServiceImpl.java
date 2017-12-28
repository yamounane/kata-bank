package com.yamounane.kata.bank.service;

import java.math.BigDecimal;
import java.time.Instant;

import com.yamounane.kata.bank.exception.AccountException;
import com.yamounane.kata.bank.model.Operation;
import com.yamounane.kata.bank.model.OperationType;

/**
 * Operation Service implementation
 * 
 * @author Yassine Amounane
 */
public class OperationServiceImpl implements OperationService {

	@Override
	public Operation addDepositOperation(BigDecimal value, String operationId) throws AccountException {
		if (value.compareTo(BigDecimal.ZERO) != 0) {
			return new Operation(operationId, OperationType.CREDIT, Instant.now(), value.abs());
		}
		return null;
	}

	@Override
	public Operation addWithdrawOperation(BigDecimal value, String operationId) throws AccountException {
		if (value.compareTo(BigDecimal.ZERO) != 0) {
			return new Operation(operationId, OperationType.DEBIT, Instant.now(), value.abs());
		}
		return null;
	}

}
