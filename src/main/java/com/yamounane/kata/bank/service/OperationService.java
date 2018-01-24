package com.yamounane.kata.bank.service;

import java.math.BigDecimal;

import com.yamounane.kata.bank.model.Operation;

/**
 * Operation Service allowing handle banking operations
 * 
 * @author Yassine Amounane
 */
public interface OperationService {

	/**
	 * Create CREDIT operation to given account
	 * 
	 * @param account
	 *            account to add the CREDIT operation
	 * @param value
	 *            value of the operation
	 * @param operationId
	 *            id of the operation
	 * @return credit operation created
	 */
	Operation addDepositOperation(BigDecimal value, String operationId) ;

	/**
	 * Create DEBIT operation to given account
	 * 
	 * @param account
	 *            account to add the DEBIT operation
	 * @param value
	 *            value of the operation
	 * @param operationId
	 *            id of the operation
	 * @return debit operation created
	 */
	Operation addWithdrawOperation(BigDecimal value, String operationId);

}
