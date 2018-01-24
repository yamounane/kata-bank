package com.yamounane.kata.bank;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.yamounane.kata.bank.exception.AccountException;
import com.yamounane.kata.bank.model.Account;
import com.yamounane.kata.bank.model.Customer;
import com.yamounane.kata.bank.model.Operation;
import com.yamounane.kata.bank.model.OperationType;
import com.yamounane.kata.bank.service.AccountServiceImpl;
import com.yamounane.kata.bank.service.OperationService;

/**
 * Test class for {@link AccountService}
 * 
 * @author Yassine Amounane
 */
@RunWith(MockitoJUnitRunner.class)
public class AccountServiceTest {

	@Mock
	private OperationService operationServiceMock;
	@InjectMocks
	private AccountService service = new AccountServiceImpl(operationServiceMock);

	private Customer johnDoe;
	private Account firstAccount;
	private Account secondAccount;

	private final BigDecimal _0 = new BigDecimal(0);
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
		Mockito.when(operationServiceMock.addDepositOperation(any(BigDecimal.class), any(String.class)))
				.thenReturn(new Operation(UUID.randomUUID().toString(), OperationType.CREDIT, Instant.now(), _1000));

		service.deposit(johnDoe, secondAccount, _1000);

		assertThat(secondAccount.getBalance()).isEqualTo(_1000);
	}
	
	@Test
	public void should_account_balance_stay_unchanged_when_deposit_of_zero_occurs() throws AccountException {
		Mockito.when(operationServiceMock.addDepositOperation(any(BigDecimal.class), any(String.class)))
				.thenReturn(null);

		service.deposit(johnDoe, secondAccount, _0);

		assertThat(secondAccount.getBalance()).isEqualTo(_0);
	}
	
	@Test
	public void should_deposit_increase_account_specified_without_impacting_other_accounts() throws AccountException {
		Mockito.when(operationServiceMock.addDepositOperation(any(BigDecimal.class), any(String.class)))
				.thenReturn(new Operation(UUID.randomUUID().toString(), OperationType.CREDIT, Instant.now(), _1000));
		BigDecimal secondAccountBalance = secondAccount.getBalance();

		service.deposit(johnDoe, firstAccount, _1000);

		assertThat(secondAccount.getBalance()).isEqualTo(secondAccountBalance);
	}

	@Test
	public void should_withdrawal_be_allowed_even_if_balance_becomes_negative() throws AccountException {
		Mockito.when(operationServiceMock.addWithdrawOperation(any(BigDecimal.class), any(String.class)))
				.thenReturn(new Operation(UUID.randomUUID().toString(), OperationType.DEBIT, Instant.now(), _1000));

		service.withdraw(johnDoe, secondAccount, _1000);

		assertThat(secondAccount.getBalance()).isEqualTo(_1000.negate());
	}
	
	@Test
	public void should_balance_stay_unchanged_when_withdrawal_of_zero_occur() throws AccountException {
		Mockito.when(operationServiceMock.addWithdrawOperation(any(BigDecimal.class), any(String.class)))
				.thenReturn(null);

		service.withdraw(johnDoe, secondAccount, _0);

		assertThat(secondAccount.getBalance()).isEqualTo(_0);
	}

	@Test
	public void should_balance_increase_when_multiple_deposit() throws AccountException {
		Mockito.when(operationServiceMock.addDepositOperation(any(BigDecimal.class), any(String.class)))
				.thenReturn(new Operation(UUID.randomUUID().toString(), OperationType.CREDIT, Instant.now(), _1000));

		service.deposit(johnDoe, secondAccount, _1000);

		Mockito.when(operationServiceMock.addDepositOperation(any(BigDecimal.class), any(String.class)))
				.thenReturn(new Operation(UUID.randomUUID().toString(), OperationType.CREDIT, Instant.now(), _1000));
		service.deposit(johnDoe, secondAccount, _1000);

		Mockito.when(operationServiceMock.addDepositOperation(any(BigDecimal.class), any(String.class)))
				.thenReturn(new Operation(UUID.randomUUID().toString(), OperationType.CREDIT, Instant.now(), _1000));
		service.deposit(johnDoe, secondAccount, _1000);

		Mockito.when(operationServiceMock.addDepositOperation(any(BigDecimal.class), any(String.class)))
				.thenReturn(new Operation(UUID.randomUUID().toString(), OperationType.CREDIT, Instant.now(), _1000));
		service.deposit(johnDoe, secondAccount, _1000);

		Mockito.when(operationServiceMock.addDepositOperation(any(BigDecimal.class), any(String.class)))
				.thenReturn(new Operation(UUID.randomUUID().toString(), OperationType.CREDIT, Instant.now(), _1000));
		service.deposit(johnDoe, secondAccount, _1000);

		assertThat(secondAccount.getBalance()).isEqualTo(_5000);
	}

	@Test
	public void should_statement_not_be_empty_when_printing_it() throws AccountException {
		Mockito.when(operationServiceMock.addDepositOperation(any(BigDecimal.class), any(String.class)))
				.thenReturn(new Operation(OperationType.CREDIT, _1000));
		service.deposit(johnDoe, secondAccount, _1000);

		Mockito.when(operationServiceMock.addDepositOperation(any(BigDecimal.class), any(String.class)))
				.thenReturn(new Operation(OperationType.CREDIT, _1000));
		service.deposit(johnDoe, secondAccount, _1000);

		Mockito.when(operationServiceMock.addWithdrawOperation(any(BigDecimal.class), any(String.class)))
				.thenReturn(new Operation(OperationType.DEBIT, _1000));
		service.withdraw(johnDoe, secondAccount, _1000);

		Mockito.when(operationServiceMock.addDepositOperation(any(BigDecimal.class), any(String.class)))
				.thenReturn(new Operation(OperationType.CREDIT, _1000));
		service.deposit(johnDoe, secondAccount, _1000);

		String statement = service.getStatement(johnDoe, secondAccount);
		
		System.out.println(statement);
		
		assertNotNull(statement);
	}

}
