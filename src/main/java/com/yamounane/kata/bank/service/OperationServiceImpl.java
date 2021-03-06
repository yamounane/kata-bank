package com.yamounane.kata.bank.service;

import java.math.BigDecimal;
import java.time.Instant;

import com.yamounane.kata.bank.model.Operation;
import com.yamounane.kata.bank.model.OperationType;

/**
 * Operation Service implementation
 * 
 * @author Yassine Amounane
 */
public class OperationServiceImpl implements OperationService {

	@Override
	public Operation addDepositOperation(BigDecimal value, String operationId) {
		if (value == null || value.compareTo(BigDecimal.ZERO) == 0) {
			return null;
		}

		return new Operation(operationId, OperationType.CREDIT, Instant.now(), value.abs());

	}

	@Override
	public Operation addWithdrawOperation(BigDecimal value, String operationId) {
		if (value == null || value.compareTo(BigDecimal.ZERO) == 0) {
			return null;
		}

		return new Operation(operationId, OperationType.DEBIT, Instant.now(), value.abs());
	}

}
