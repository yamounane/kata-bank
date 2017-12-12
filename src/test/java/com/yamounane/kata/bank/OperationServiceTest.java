package com.yamounane.kata.bank;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import com.yamounane.kata.bank.exception.AccountException;
import com.yamounane.kata.bank.model.Account;
import com.yamounane.kata.bank.service.OperationService;
import com.yamounane.kata.bank.service.OperationServiceImpl;

/**
 * Test class for {@link OperationService}
 *  
 * @author Yassine Amounane
 */
public class OperationServiceTest {
	
	OperationService service = new OperationServiceImpl();
		
	private Account accountDoe;
	
	private final String DOE_ACCOUNT_ID = "A001";
	
	private final BigDecimal ONE_THOUSAND = new BigDecimal(1000);
	
	private final BigDecimal MINUS_ONE_THOUSAND = new BigDecimal(-1000);

    @Before
    public void setUp() throws Exception {  
    	//Account initilization
    	accountDoe = new Account(DOE_ACCOUNT_ID);
    }	

	@Test
	public void checkAddDepositOperationOfOneThousandForDoeAccount() {
		try {
			service.addDepositOperation(accountDoe, ONE_THOUSAND, "TESTOP001");
		} catch (AccountException e) {
			fail();
		}
		assertEquals(ONE_THOUSAND, accountDoe.getBalance());
	}
	
	@Test
	public void checkAddWithdrawOperationOfOneThousandForDoeAccount() {
		try {
			service.addWithdrawOperation(accountDoe, ONE_THOUSAND, "TESTOP002");
		} catch (AccountException e) {
			fail();
		}
		assertEquals(MINUS_ONE_THOUSAND, accountDoe.getBalance());
	}
	
	@Test
	public void checkComputeBalanceForOneThousandDoeAccount() {	
		try {
			service.addDepositOperation(accountDoe, ONE_THOUSAND, "TESTOP003");
		} catch (AccountException e) {
			fail();
		}
		assertEquals(ONE_THOUSAND, service.computeBalance(accountDoe));
		assertEquals(ONE_THOUSAND, accountDoe.getBalance());
	}
	
}
