package components;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY)
@JsonSubTypes({
	@JsonSubTypes.Type(value = CurrentAccount.class, name = "current"),
	@JsonSubTypes.Type(value = SavingsAccount.class, name = "savings")
})
// 1.2.1 Creation of the account class
public abstract class Account {
	protected String label;
	protected double balance;
	protected int accountNumber;
	protected Client client;
	private static int accountCount;
	
	public Account() {}
	
	public Account(String label, Client client) {
		this.label = label;
		this.balance = 0;
		this.accountNumber = ++accountCount;
		this.client = client;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public double getBalance() {
		return balance;
	}
	
	public void setBalance(double balance) {
		this.balance = balance;
	}

	public void setBalance(Flow flow) {
		if (flow instanceof Debit) {
			this.balance -= flow.getAmount();
		} else if (flow instanceof Credit) {
			this.balance += flow.getAmount();
		} else if (flow instanceof Transfer transfer) {
			if (transfer.getTargetAccountNumber() == this.accountNumber) {
				this.balance += transfer.getAmount();
			} else if (transfer.getFromAccount() == this.accountNumber) {
				this.balance -= transfer.getAmount();
			}
		}
	}

	public int getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(int accountNumber) {
		this.accountNumber = accountNumber;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public static int getAccountCount() {
		return accountCount;
	}

	@Override
	public String toString() {
		return "Account [label=" + label + ", balance=" + balance + ", accountNumber=" + accountNumber + ", client="
				+ client + "]";
	}
}
