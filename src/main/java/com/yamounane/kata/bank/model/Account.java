package com.yamounane.kata.bank.model;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.TreeSet;

/**
 * Account Class
 * 
 * @author Yassine Amounane
 */
public class Account {
	
	private String id;
	
	private BigDecimal balance;
	
	private TreeSet<Operation> operations;
	
	public Account(String id) {
		super();
		this.id = id;
		this.operations = new TreeSet<>();
		this.balance = BigDecimal.ZERO;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public TreeSet<Operation> getOperations() {
		return operations;
	}

	public void setOperations(TreeSet<Operation> operations) {
		this.operations = operations;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Account other = (Account) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
	    DecimalFormat df = new DecimalFormat("###,##0.00");
		return "Balance for Account [" + id + "] = " + df.format(balance);
	}

}
