package com.yamounane.kata.bank;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.yamounane.kata.bank.exception.AccountException;
import com.yamounane.kata.bank.model.Account;
import com.yamounane.kata.bank.model.Customer;
import com.yamounane.kata.bank.service.AccountServiceImpl;

/**
 * Test class for {@link AccountService}
 *  
 * @author Yassine Amounane
 */
public class AccountServiceTest {
	
	AccountService service = new AccountServiceImpl();
	
	private Customer customerDoe;
	
	private Account accountDoe;
	
	private final String DOE_ACCOUNT_ID = "A001";
	
	private final BigDecimal ONE_THOUSAND = new BigDecimal(1000);
	
	private final BigDecimal MINUS_ONE_THOUSAND = new BigDecimal(-1000);
	
	private final BigDecimal FIVE_THOUSAND = new BigDecimal(5000);
	
	@Before
	public void setUp() throws Exception {  
    	//John Doe customer & account initilization
    	customerDoe = new Customer("C001", "John", "Doe");
    	accountDoe = new Account(DOE_ACCOUNT_ID);
    	List<Account> doeAccounts = new ArrayList<>();
    	doeAccounts.add(accountDoe);
    	customerDoe.setAccounts(doeAccounts);
     }	
	
	@Test
	public void checkDepositOneThousandOperationForDoeAccount() {
        try {
			service.deposit(customerDoe, DOE_ACCOUNT_ID, ONE_THOUSAND, "OP001");
		} catch (AccountException e) {
			fail();
		}
        assertEquals(ONE_THOUSAND, accountDoe.getBalance());
	}
	
	@Test
	public void checkWithdrawOperationOneThousandForDoeAccount() {
        try {
			service.withdraw(customerDoe, DOE_ACCOUNT_ID, ONE_THOUSAND, "OP002");
		} catch (AccountException e) {
			fail();
		}
        assertEquals(MINUS_ONE_THOUSAND, accountDoe.getBalance());	}
	
	@Test
	public void checkBalanceForDoeAccountWithFiveHundred() {
		try {
			service.deposit(customerDoe, DOE_ACCOUNT_ID, ONE_THOUSAND, "OP001");
			service.deposit(customerDoe, DOE_ACCOUNT_ID, ONE_THOUSAND, "OP002");
			service.deposit(customerDoe, DOE_ACCOUNT_ID, ONE_THOUSAND, "OP003");
			service.deposit(customerDoe, DOE_ACCOUNT_ID, ONE_THOUSAND, "OP004");
			service.deposit(customerDoe, DOE_ACCOUNT_ID, ONE_THOUSAND, "OP005");
			assertEquals(FIVE_THOUSAND, service.getBalance(customerDoe, DOE_ACCOUNT_ID));
		} catch (AccountException e) {
			fail();
		}
	}
	
	@Test
	public void checkStatementForOneThousandDoeAccountIsNotNull() {
		try {
			service.deposit(customerDoe, DOE_ACCOUNT_ID, ONE_THOUSAND, "OP001");
			service.deposit(customerDoe, DOE_ACCOUNT_ID, ONE_THOUSAND, "OP002");
			service.withdraw(customerDoe, DOE_ACCOUNT_ID, ONE_THOUSAND, "OP003");
			service.deposit(customerDoe, DOE_ACCOUNT_ID, ONE_THOUSAND, "OP004");

			String statement = service.getStatement(customerDoe, DOE_ACCOUNT_ID);
			assertNotNull(statement);
		} catch (AccountException e) {
			fail();
		}
	}

}
