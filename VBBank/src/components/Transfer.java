package components;

import java.util.Date;

// 1.3.3 Creation of the Transfert, Credit, Debit classes
public class Transfer extends Flow {
	private int fromAccount;

	public Transfer(String comment, String identifier, double amount, int targetAccountNumber, boolean effect,
			Date date, int fromAccount) {
		super(comment, identifier, amount, targetAccountNumber, effect, date);
		this.fromAccount = fromAccount;
	}

	public int getFromAccount() {
		return this.fromAccount;
	}
}
