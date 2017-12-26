package com.yamounane.kata.bank;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import com.yamounane.kata.bank.exception.AccountException;
import com.yamounane.kata.bank.model.Account;
import com.yamounane.kata.bank.model.Customer;
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
	
	private final BigDecimal _1000 = new BigDecimal(1000);	
	
    @Before
    public void setUp() throws Exception {  
    	Customer johnDoe = new Customer("John", "Doe");
    	accountDoe = new Account("A001", johnDoe);
    }	

	@Test
	public void should_balance_increase_when_deposit_operation_occur() throws AccountException{
		service.addDepositOperation(accountDoe, _1000, "TESTOP001");

		assertThat(accountDoe.getBalance()).isEqualTo(_1000);
	}
	
	@Test
	public void should_balance_decrease_when_withdrawal_operation_occur() throws AccountException {
		service.addWithdrawOperation(accountDoe, _1000, "TESTOP002");

		assertThat(accountDoe.getBalance()).isEqualTo(_1000.negate());
	}
	
	@Test
	public void should_compute_balance_increase_when_deposit_operation_occur() throws AccountException {	
		service.addDepositOperation(accountDoe, _1000, "TESTOP003");

		assertThat(service.computeBalance(accountDoe)).isEqualTo(_1000);
		assertThat(accountDoe.getBalance()).isEqualTo(_1000);
	}
	
	@Test
	public void should_statement_operations_number_right_when_multiple_operations_are_made() throws AccountException {	
		service.addDepositOperation(accountDoe, _1000, "TESTOP010");
		service.addWithdrawOperation(accountDoe, _1000, "TESTOP011");
		service.addDepositOperation(accountDoe, _1000, "TESTOP012");
		service.addDepositOperation(accountDoe, _1000, "TESTOP013");
		
		String[] computedStatement = service.getStatement(accountDoe).split(";");
		
		assertEquals(4, computedStatement.length);
	}
	
}
