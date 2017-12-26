package com.yamounane.kata.bank;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import com.yamounane.kata.bank.exception.AccountException;
import com.yamounane.kata.bank.model.Account;
import com.yamounane.kata.bank.model.Customer;
import com.yamounane.kata.bank.service.AccountServiceImpl;
import com.yamounane.kata.bank.service.OperationService;
import com.yamounane.kata.bank.service.OperationServiceImpl;

/**
 * Test class for {@link AccountService}
 * 
 * @author Yassine Amounane
 */
public class AccountServiceTest {

	private OperationService operationServiceMock = new OperationServiceImpl();
	private AccountService service = new AccountServiceImpl(operationServiceMock);

	private Customer johnDoe;
	private Account firstAccount;
	private Account secondAccount;

	private final BigDecimal _1000 = new BigDecimal(1000);
	private final BigDecimal _5000 = new BigDecimal(5000);

	@Before
	public void setUp() throws Exception {
		johnDoe = new Customer("John", "Doe");
		firstAccount = johnDoe.getAccounts().iterator().next();
		secondAccount = new Account("A002", johnDoe);
	}

	@Test
	public void should_account_balance_increase_when_deposit_occur() throws AccountException {
		service.deposit(johnDoe, secondAccount, _1000);

		assertThat(secondAccount.getBalance()).isEqualTo(_1000);
	}

	@Test
	public void should_deposit_increase_account_specified_without_impacting_other_accounts() throws AccountException {
		BigDecimal secondAccountBalance = secondAccount.getBalance();
		
		service.deposit(johnDoe, firstAccount, _1000);

		assertThat(secondAccount.getBalance()).isEqualTo(secondAccountBalance);
	}

	@Test
	public void should_withdrawal_be_allowed_even_if_balance_becomes_negative() throws AccountException {
		service.withdraw(johnDoe, secondAccount, _1000);

		assertThat(secondAccount.getBalance()).isEqualTo(_1000.negate());
	}

	@Test
	public void should_deposit_increase_when_multiple_deposit() throws AccountException {
		service.deposit(johnDoe, secondAccount, _1000);
		service.deposit(johnDoe, secondAccount, _1000);
		service.deposit(johnDoe, secondAccount, _1000);
		service.deposit(johnDoe, secondAccount, _1000);
		service.deposit(johnDoe, secondAccount, _1000);

		assertThat(secondAccount.getBalance()).isEqualTo(_5000);
	}

	@Test
	public void should_statement_not_be_empty_when_printing_it() throws AccountException {
		service.deposit(johnDoe, secondAccount, _1000);
		service.deposit(johnDoe, secondAccount, _1000);
		service.withdraw(johnDoe, secondAccount, _1000);
		service.deposit(johnDoe, secondAccount, _1000);

		String statement = service.getStatement(johnDoe, secondAccount);

		assertNotNull(statement);
	}

}
