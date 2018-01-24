package com.yamounane.kata.bank.model;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.LinkedHashSet;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Account Class
 * 
 * @author Yassine Amounane
 */
@Getter @Setter @EqualsAndHashCode(exclude= {"customer","balance","operations"})
public class Account {
	
	private String id;
	
	private BigDecimal balance;
	
	private LinkedHashSet<Operation> operations;

	private Customer customer;
	
	public Account(String id, Customer customer) {
		super();
		this.id = id;
		this.operations = new LinkedHashSet<>();
		this.balance = BigDecimal.ZERO;
		this.customer = customer;
		this.customer.getAccounts().add(this);
	}
	
	@Override
	public String toString() {
	    DecimalFormat df = new DecimalFormat("###,##0.00");
		return "Balance for Account [" + id + "] = " + df.format(balance);
	}

}
