package com.yamounane.kata.bank.model;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Operation Class
 * 
 * @author Yassine Amounane
 */
@Getter @Setter @AllArgsConstructor @EqualsAndHashCode()
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

	public int compareTo(Operation op) {
		if (id.equals(op.getId())) {
			return 0;
		} else if (date.isAfter(op.getDate())) {
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
