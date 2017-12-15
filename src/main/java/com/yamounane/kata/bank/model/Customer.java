package com.yamounane.kata.bank.model;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Customer's Class
 * 
 * @author Yassine Amounane
 */
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	
	public Set<Account> getAccounts() {
		return accounts;
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
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Customer other = (Customer) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Customer [" + id + "] " + lastname + " " + firstname;
	}

}
