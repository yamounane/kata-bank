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
	
	void addDepositOperation(Account account, BigDecimal value, String operationId)throws AccountException;

}
