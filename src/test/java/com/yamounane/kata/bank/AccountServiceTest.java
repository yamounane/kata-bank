package com.yamounane.kata.bank;

import static java.util.stream.IntStream.range;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Matchers.any;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;
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

	private Integer operationIdIncrement;

	@Before
	public void setUp() throws Exception {
		johnDoe = new Customer("John", "Doe");
		firstAccount = johnDoe.getAccounts().iterator().next();
		secondAccount = new Account("A002", johnDoe);
		operationIdIncrement = 0;
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
	public void should_raise_exception_when_deposit_for_null_account_occurs() {
		Mockito.when(operationServiceMock.addDepositOperation(any(BigDecimal.class), any(String.class)))
				.thenReturn(null);

		assertThatThrownBy(() -> service.deposit(johnDoe, null, _1000)).isInstanceOf(AccountException.class);
	}
	
	@Test
	public void should_raise_exception_when_deposit_for_null_customer_occurs() {
		Mockito.when(operationServiceMock.addDepositOperation(any(BigDecimal.class), any(String.class)))
				.thenReturn(null);

		assertThatThrownBy(() -> service.deposit(null, secondAccount, _1000)).isInstanceOf(AccountException.class);
	}
	
	@Test
	public void should_account_balance_increase_account_specified_without_impacting_other_accounts()
			throws AccountException {
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
	public void should_account_balance_stay_unchanged_when_withdrawal_of_zero_occur() throws AccountException {
		Mockito.when(operationServiceMock.addWithdrawOperation(any(BigDecimal.class), any(String.class)))
				.thenReturn(null);

		service.withdraw(johnDoe, secondAccount, _0);

		assertThat(secondAccount.getBalance()).isEqualTo(_0);
	}
	
	@Test
	public void should_raise_exception_when_withdrawal_for_null_account_occurs() {
		Mockito.when(operationServiceMock.addWithdrawOperation(any(BigDecimal.class), any(String.class)))
				.thenReturn(null);

		assertThatThrownBy(() -> service.deposit(johnDoe, null, _1000)).isInstanceOf(AccountException.class);
	}
	
	@Test
	public void should_raise_exception_when_withdrawal_for_null_customer_occurs() {
		Mockito.when(operationServiceMock.addWithdrawOperation(any(BigDecimal.class), any(String.class)))
				.thenReturn(null);

		assertThatThrownBy(() -> service.deposit(null, secondAccount, _1000)).isInstanceOf(AccountException.class);
	}

	@Test
	public void should_account_balance_increase_when_multiple_deposit() throws AccountException {
		repeat(5, () -> doDepositOperationMockFor(johnDoe, secondAccount, _1000, Instant.now()));

		assertThat(secondAccount.getBalance()).isEqualTo(_5000);
	}

	@Test
	public void should_statement_not_be_empty_when_printing_it() throws AccountException {
		Instant depositInstant = Instant.now();
		Instant withdrawInstant = Instant.now();

		repeat(3, () -> doDepositOperationMockWithIdFor(johnDoe, secondAccount, _1000, depositInstant));
		repeat(2, () -> doWithdrawalOperationMockWithIdFor(johnDoe, secondAccount, _1000, withdrawInstant));
		String statement = service.getStatement(johnDoe, secondAccount);

		assertThat(statement).isEqualTo(printStatement(depositInstant, 3, _1000, withdrawInstant, 2, _1000));
	}

	private String printStatement(Instant depositInstant, int depositNumber, BigDecimal depositAmount,
			Instant withdrawInstant, int withdrawNumber, BigDecimal withdrawAmount) {
		String statement = "";
		DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM).withLocale(Locale.UK)
				.withZone(ZoneId.systemDefault());
		DecimalFormat df = new DecimalFormat("#,###.00");

		int operationIdInit = 0;
		for (; operationIdInit < depositNumber; operationIdInit++) {
			statement += formatter.format(depositInstant) + " " + operationIdInit + " " + OperationType.CREDIT.name()
					+ " " + df.format(depositAmount) + "\n";
		}

		for (int i = 0; i < withdrawNumber; i++) {
			statement += formatter.format(withdrawInstant) + " " + operationIdInit + " " + OperationType.DEBIT.name()
					+ " " + df.format(withdrawAmount) + "\n";
			operationIdInit++;
		}

		return statement;
	}

	private void doDepositOperationMockFor(Customer customer, Account account, BigDecimal amount,
			Instant operationTime) {
		Mockito.when(operationServiceMock.addDepositOperation(any(BigDecimal.class), any(String.class)))
				.thenReturn(new Operation(UUID.randomUUID().toString(), OperationType.CREDIT, operationTime, amount));
		try {
			service.deposit(customer, account, amount);
		} catch (Exception e) {
		}
	}

	private void doDepositOperationMockWithIdFor(Customer customer, Account account, BigDecimal amount,
			Instant operationTime) {
		Mockito.when(operationServiceMock.addDepositOperation(any(BigDecimal.class), any(String.class))).thenReturn(
				new Operation(String.valueOf(operationIdIncrement++), OperationType.CREDIT, operationTime, amount));
		try {
			service.deposit(customer, account, amount);
		} catch (AccountException e) {
		}
	}

	private void doWithdrawalOperationMockWithIdFor(Customer customer, Account account, BigDecimal amount,
			Instant operationTime) {
		Mockito.when(operationServiceMock.addWithdrawOperation(any(BigDecimal.class), any(String.class))).thenReturn(
				new Operation(String.valueOf(operationIdIncrement++), OperationType.DEBIT, operationTime, amount));
		try {
			service.withdraw(customer, account, amount);
		} catch (Exception e) {
		}
	}

	private void repeat(int times, Runnable runnable) {
		range(0, times).forEach(i -> runnable.run());
	}

}
