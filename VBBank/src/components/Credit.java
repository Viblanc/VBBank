package components;

import java.util.Date;

// 1.3.3 Creation of the Transfert, Credit, Debit classes
public class Credit extends Flow {
	public Credit(String comment, double amount, int targetAccountNumber, boolean effect,
			Date date) {
		super(comment, amount, targetAccountNumber, effect, date);
	}
}
