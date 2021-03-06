package com.yamounane.kata.bank;

import java.math.BigDecimal;

import com.yamounane.kata.bank.exception.AccountException;
import com.yamounane.kata.bank.model.Account;
import com.yamounane.kata.bank.model.Customer;

/**
 * Account Service allowing to retrieve and save money. Provides also accounts
 * statement.
 * 
 * @author Yassine Amounane
 */
public interface AccountService {

	/**
	 * Get the balance for an account
	 * 
	 * @param customer
	 *            customer of the account
	 * @param account
	 *            account to print the statement for
	 * @throws AccountException
	 *             if customer or account does not exists.
	 * @return balance for the given customer and account
	 */
	BigDecimal getBalance(Customer customer, Account account) throws AccountException;

	/**
	 * Prints the statement of an account
	 * 
	 * @param customer
	 *            customer of the account
	 * @param account
	 *            account to print the statement for
	 * @throws AccountException
	 *             if customer or account does not exists.
	 * @return statement for account
	 */
	String getStatement(Customer customer, Account account) throws AccountException;

	/**
	 * Add an DEBIT operation on account
	 * 
	 * @param customer
	 *            customer of the account
	 * @param account
	 *            account to debit
	 * @param amount
	 *            amount of the operation
	 * @throws AccountException
	 *             if customer or account does not exists or if the operation is not
	 *             possible
	 */
	void withdraw(Customer customer, Account account, BigDecimal amount) throws AccountException;

	/**
	 * Add an CREDIT operation on account
	 * 
	 * @param customer
	 *            customer of the account
	 * @param accountId
	 *            account id to debit
	 * @param amount
	 *            amount of the operation
	 * @throws AccountException
	 *             if customer or account does not exists or if the operation is not
	 *             possible
	 */
	void deposit(Customer customer, Account account, BigDecimal amount) throws AccountException;

}
