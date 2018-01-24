package com.yamounane.kata.bank.model;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Customer's Class
 * 
 * @author Yassine Amounane
 */
@Getter @Setter @EqualsAndHashCode(exclude="accounts")
public class Customer {
	
	private String id;
	
	private String lastname;
	
	private String firstname;
	
	private Set<Account> accounts = new HashSet<>();
	
	public Customer(String lastname, String firstname) {
		super();
		this.id = UUID.randomUUID().toString();
		this.lastname = lastname;
		this.firstname = firstname;
		this.accounts.add(new Account(id, this));
	}

	@Override
	public String toString() {
		return "Customer [" + id + "] " + lastname + " " + firstname;
	}

}
