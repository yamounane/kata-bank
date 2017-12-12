package com.yamounane.kata.bank.service;

import java.math.BigDecimal;

import com.yamounane.kata.bank.exception.AccountException;
import com.yamounane.kata.bank.model.Account;

/**
 * Operation Service allowing handle banking operations
 * 
 * @author Yassine Amounane
 */
public interface OperationService {
	
	/**
	 * Add CREDIT operation to given account  
	 * @param account account to add the CREDIT operation
	 * @param value value of the operation
	 * @param operationId id of the operation
	 * @throws AccountException if account does not exists.
	 */
	void addDepositOperation(Account account, BigDecimal value, String operationId) throws AccountException;

	/**
	 * Compute balance from given account operations  
	 * @param account account to compute balance
	 * @return computed balance
	 */
	BigDecimal computeBalance(Account account);

}
