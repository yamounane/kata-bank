package com.yamounane.kata.bank;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import com.yamounane.kata.bank.exception.AccountException;
import com.yamounane.kata.bank.model.Account;
import com.yamounane.kata.bank.service.OperationService;
import com.yamounane.kata.bank.service.OperationServiceImpl;

/**
 * Test class for {@link OperationService}
 *  
 * @author Yassine Amounane
 */
@RunWith(BlockJUnit4ClassRunner.class)
public class OperationServiceTest {
	
	OperationService service = new OperationServiceImpl();
		
	private Account accountDoe;
	
	private final String DOE_ACCOUNT_ID = "A001";
	
	private final BigDecimal ONE_THOUSAND = new BigDecimal(1000);
	
	private final BigDecimal MINUS_ONE_THOUSAND = new BigDecimal(-1000);
	
	private String doeAccountStatement;

    @Before
    public void setUp() throws Exception {  
    	//Account initilization
    	accountDoe = new Account(DOE_ACCOUNT_ID);
    	doeAccountStatement = "TESTOP010 CREDIT 1 000,00 \n" + 
    							"TESTOP011 DEBIT 1 000,00 \n" + 
								"TESTOP012 CREDIT 1 000,00 \n" + 
								"TESTOP013 CREDIT 1 000,00 \n";
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
	
	@Test
	public void checkStatementForTwoThousandDoeAccount() {	
		try {
			service.addDepositOperation(accountDoe, ONE_THOUSAND, "TESTOP010");
			service.addWithdrawOperation(accountDoe, ONE_THOUSAND, "TESTOP011");
			service.addDepositOperation(accountDoe, ONE_THOUSAND, "TESTOP012");
			service.addDepositOperation(accountDoe, ONE_THOUSAND, "TESTOP013");
			String computedStatement = service.getStatement(accountDoe);
			assertEquals(doeAccountStatement, computedStatement);
		} catch (AccountException e) {
			fail();
		}
	}
	
}
