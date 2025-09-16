package components;

import java.time.LocalDate;

// 1.3.3 Creation of the Transfert, Credit, Debit classes
public class Transfer extends Flow {
	private int fromAccount;
	
	public Transfer() {
		super();
	}

	public Transfer(String comment, double amount, int targetAccountNumber, boolean effect,
			LocalDate date, int fromAccount) {
		super(comment, amount, targetAccountNumber, effect, date);
		this.fromAccount = fromAccount;
	}

	public int getFromAccount() {
		return this.fromAccount;
	}
}
