package components;

import java.util.Date;

// 1.3.2 Creation of the Flow class
public abstract class Flow {
	private String comment;
	private int identifier;
	private double amount;
	private int targetAccountNumber;
	private boolean effect;
	private Date date;
	private static int flowCount = 0;
	
	public Flow(String comment, double amount, int targetAccountNumber, boolean effect, Date date) {
		this.comment = comment;
		this.identifier = ++flowCount;
		this.amount = amount;
		this.targetAccountNumber = targetAccountNumber;
		this.effect = effect;
		this.date = date;
	}

	public String getComment() {
		return comment;
	}

	public int getIdentifier() {
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
