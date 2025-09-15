import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

import components.Account;
import components.Client;
import components.CurrentAccount;
import components.SavingsAccount;

// 1.1.2 Creation of main class for tests
public class Main {
	public static List<Client> loadClients(int numberOfClients) {
		List<Client> clients = new ArrayList<>();

		for (int i = 1; i <= numberOfClients; i++) {
			clients.add(new Client("firstName" + i, "lastName" + i));
		}

		return clients;
	}

	public static void displayClients(List<Client> clients) {
		clients.stream().forEach(client -> {
			System.out.println(client.toString());
		});
	}

	// 1.2.3 Creation of the table of accounts
	public static List<Account> loadAccounts(List<Client> clients) {
		List<Account> accounts = new ArrayList<>();
		BiFunction<String, Client, String> getLabel = (accountType, client) -> String.format("%s %s's %s Account",
				client.getFirstName(), client.getLastName(), accountType);
		;

		for (Client client : clients) {
			accounts.add(new CurrentAccount(getLabel.apply("Current", client), client));
			accounts.add(new SavingsAccount(getLabel.apply("Savings", client), client));
		}

		return accounts;
	}

	public static void displayAccounts(List<Account> accounts) {
		accounts.stream().forEach(account -> {
			System.out.println(account.toString());
		});
	}

	// 1.3.1 Adaptation of the table of accounts
	public static Map<Integer, Account> loadAccountHashMap(List<Account> accounts) {
		Map<Integer, Account> accountMap = accounts.stream()
				.collect(Collectors.toMap(a -> a.getAccountNumber(), a -> a));

		return accountMap;
	}

	public static void displayAccountHashMap(Map<Integer, Account> accountMap) {
		accountMap.entrySet().stream()
				.sorted((e1, e2) -> Double.compare(e1.getValue().getBalance(), e2.getValue().getBalance()))
				.forEach(entry -> {
					System.out.println(entry.toString());
				});
	}

	public static void main(String[] args) {
		List<Client> clients = loadClients(5);
		displayClients(clients);
		List<Account> accounts = loadAccounts(clients);
		displayAccounts(accounts);
		Map<Integer, Account> accountMap = loadAccountHashMap(accounts);
		displayAccountHashMap(accountMap);
	}
}
