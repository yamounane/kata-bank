package com.yamounane.kata.bank;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import com.yamounane.kata.bank.exception.AccountException;
import com.yamounane.kata.bank.model.Operation;
import com.yamounane.kata.bank.model.OperationType;
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

	private final BigDecimal _1000 = new BigDecimal(1000);

	private final BigDecimal _0 = new BigDecimal(0);

	@Test
	public void should_balance_increase_when_deposit_operation_occur() throws AccountException {
		Operation operation = service.addDepositOperation(_1000, "TESTOP001");

		assertThat(operation.getAmount()).isEqualTo(_1000);
		assertThat(operation.getType()).isEqualTo(OperationType.CREDIT);
	}

	@Test
	public void should_operation_is_null_when_deposit_operation_of_zero_occur() throws AccountException {
		Operation operation = service.addDepositOperation(_0, "TESTOP001");

		assertThat(operation).isEqualTo(null);
	}

	@Test
	public void should_balance_decrease_when_withdrawal_operation_occur() throws AccountException {
		Operation operation = service.addWithdrawOperation(_1000, "TESTOP002");

		assertThat(operation.getAmount()).isEqualTo(_1000);
		assertThat(operation.getType()).isEqualTo(OperationType.DEBIT);
	}
	
	@Test
	public void should_operation_is_null_when_withdrawal_operation_of_zero_occur() throws AccountException {
		Operation operation = service.addWithdrawOperation(_0, "TESTOP001");

		assertThat(operation).isEqualTo(null);
	}

}
