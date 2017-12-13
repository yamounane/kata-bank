package com.yamounane.kata.bank;

import static org.junit.Assert.assertEquals;
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
	
	private String doeAccountStatementForOneThousand;

	@Before
	public void setUp() throws Exception {  
    	//John Doe customer & account initilization
    	customerDoe = new Customer("C001", "John", "Doe");
    	accountDoe = new Account(DOE_ACCOUNT_ID);
    	List<Account> doeAccounts = new ArrayList<>();
    	doeAccounts.add(accountDoe);
    	customerDoe.setAccounts(doeAccounts);
    	
    	doeAccountStatementForOneThousand = "OP001 CREDIT 1 000,00 \n";
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
		fail();
	}
	
	@Test
	public void checkStatementForOneThousandDoeAccount() {
		try {
			service.deposit(customerDoe, DOE_ACCOUNT_ID, ONE_THOUSAND, "OP001");
			String statement = service.getStatement(customerDoe, DOE_ACCOUNT_ID);
			assertEquals(doeAccountStatementForOneThousand, statement);
		} catch (AccountException e) {
			fail();
		}
	}

}
