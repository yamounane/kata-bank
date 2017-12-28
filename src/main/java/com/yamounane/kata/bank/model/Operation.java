package com.yamounane.kata.bank.model;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;
import java.util.UUID;

/**
 * Operation Class
 * 
 * @author Yassine Amounane
 */
public class Operation implements Comparable<Operation> {

	private String id;

	private OperationType type;

	private Instant date;

	private BigDecimal amount;

	public Operation(OperationType type, BigDecimal amount) {
		super();
		this.id = UUID.randomUUID().toString();
		this.type = type;
		this.date = Instant.now();
		this.amount = amount;
	}
	
	public Operation(String id, OperationType type, Instant date, BigDecimal amount) {
		super();
		this.id = id;
		this.type = type;
		this.date = date;
		this.amount = amount;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public OperationType getType() {
		return type;
	}

	public void setType(OperationType type) {
		this.type = type;
	}

	public Instant getDate() {
		return date;
	}

	public void setDate(Instant date) {
		this.date = date;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((amount == null) ? 0 : amount.hashCode());
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		Operation other = (Operation) obj;
		if (!id.equals(other.getId()))
			return false;
		return true;
	}

	public int compareTo(Operation op) {
		if (id.equals(op.getId())) {
			return 0;
		} else if (date.toEpochMilli() > op.getDate().toEpochMilli()) {
			return -1;
		}
		return 1;
	}

	@Override
	public String toString() {
		DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM).withLocale(Locale.UK)
				.withZone(ZoneId.systemDefault());
		DecimalFormat df = new DecimalFormat("#,###.00");
		return formatter.format(date) + " " + id + " " + type.name() + " " + df.format(amount);
	}

}
