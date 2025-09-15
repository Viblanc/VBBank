package components;

import java.util.Date;

// 1.3.2 Creation of the Flow class
public abstract class Flow {
	private String comment;
	private String identifier;
	private double amount;
	private int targetAccountNumber;
	private boolean effect;
	private Date date;
	
	public Flow(String comment, String identifier, double amount, int targetAccountNumber, boolean effect, Date date) {
		this.comment = comment;
		this.identifier = identifier;
		this.amount = amount;
		this.targetAccountNumber = targetAccountNumber;
		this.effect = effect;
		this.date = date;
	}

	public String getComment() {
		return comment;
	}

	public String getIdentifier() {
		return identifier;
	}

	public double getAmount() {
		return amount;
	}

	public int getTargetAccountNumber() {
		return targetAccountNumber;
	}

	public boolean isEffect() {
		return effect;
	}

	public Date getDate() {
		return date;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public void setTargetAccountNumber(int targetAccountNumber) {
		this.targetAccountNumber = targetAccountNumber;
	}

	public void setEffect(boolean effect) {
		this.effect = effect;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}
