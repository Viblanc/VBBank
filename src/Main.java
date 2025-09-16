import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import components.Account;
import components.AccountList;
import components.Client;
import components.Credit;
import components.CurrentAccount;
import components.Debit;
import components.Flow;
import components.SavingsAccount;
import components.Transfer;

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
		clients.stream().forEach(System.out::println);
	}

	// 1.2.3 Creation of the table of accounts
	public static List<Account> loadAccounts(List<Client> clients) {
		List<Account> accounts = new ArrayList<>();
		BiFunction<String, Client, String> getLabel = (accountType, client) -> String.format("%s %s's %s Account",
				client.getFirstName(), client.getLastName(), accountType);

		for (Client client : clients) {
			accounts.add(new CurrentAccount(getLabel.apply("Current", client), client));
			accounts.add(new SavingsAccount(getLabel.apply("Savings", client), client));
		}

		return accounts;
	}

	public static void displayAccounts(List<Account> accounts) {
		accounts.stream().forEach(System.out::println);
	}

	// 1.3.1 Adaptation of the table of accounts
	public static Map<Integer, Account> loadAccountHashMap(List<Account> accounts) {
		return accounts.stream().collect(Collectors.toMap(Account::getAccountNumber, Function.identity()));
	}

	public static void displayAccountHashMap(Map<Integer, Account> accountMap) {
		accountMap.entrySet().stream()
				.sorted((e1, e2) -> Double.compare(e1.getValue().getBalance(), e2.getValue().getBalance()))
				.forEach(System.out::println);
	}

	// 1.3.4 Creation of the flow array
	public static List<Flow> loadFlows(List<Account> accounts) {
		List<Flow> flows = new ArrayList<>();

		// debit of 50€ from account 1
		flows.add(new Debit("debit on account 1", 50.0, 1, true, LocalDate.now().plusDays(2)));

		// credit of 100.50€ on every current account
		flows.addAll(accounts.stream().filter(CurrentAccount.class::isInstance)
				.map(account -> new Credit("credit on current account " + account.getAccountNumber(), 100.5,
						account.getAccountNumber(), true, LocalDate.now().plusDays(2)))
				.toList());

		// credit of 500€ on every savings account
		flows.addAll(accounts.stream().filter(SavingsAccount.class::isInstance)
				.map(account -> new Credit("credit on savings account " + account.getAccountNumber(), 1500,
						account.getAccountNumber(), true, LocalDate.now().plusDays(2)))
				.toList());

		// transfer of 50€ from account 1 to account 2
		flows.add(new Transfer("transfer from account 1 to account 2", 50.0, 2, true, LocalDate.now().plusDays(2), 1));

		return flows;
	}

	// 1.3.5 Updating accounts
	public static void processFlows(List<Flow> flows, Map<Integer, Account> accountMap) {
		Predicate<Account> hasNegativeBalance = account -> account.getBalance() < 0.0;

		// update balances
		for (Flow flow : flows) {
			if (flow instanceof Transfer transfer) {
				Account from = accountMap.get(transfer.getFromAccount());
				Account to = accountMap.get(transfer.getTargetAccountNumber());
				from.setBalance(transfer);
				to.setBalance(transfer);
			} else {
				Account account = accountMap.get(flow.getTargetAccountNumber());
				account.setBalance(flow);
			}
		}

		accountMap.values().stream().filter(hasNegativeBalance).findAny()
				.ifPresent(acc -> System.out.println("Account " + acc.getAccountNumber() + " has a negative value!"));
	}

	// 2.1 JSON file of flows
	public static List<Flow> loadFlowsFromJson() {
		List<Flow> flows = new ArrayList<>();
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());

		try (BufferedReader in = Files.newBufferedReader(Paths.get("./flows.json"))) {
			String data = in.lines().collect(Collectors.joining(System.lineSeparator()));
			flows = Arrays.asList(mapper.readValue(data, Flow[].class));
		} catch (IOException e) {
			e.printStackTrace();
		}

		return flows;
	}
	
	// 2.2 XML file of accounts
	public static List<Account> loadAccountsFromXML() {
		List<Account> accounts = new ArrayList<>();
		XmlMapper mapper = new XmlMapper();
		mapper.registerModule(new JavaTimeModule());

		try (BufferedReader in = Files.newBufferedReader(Paths.get("./accounts.xml"))) {
			String data = in.lines().collect(Collectors.joining(System.lineSeparator()));
			accounts = mapper.readValue(data, AccountList.class).getAccounts();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return accounts;
	}

	public static void main(String[] args) {
		List<Client> clients = loadClients(5);
		System.out.println("Clients:");
		displayClients(clients);
		System.out.println("");

//		List<Account> accounts = loadAccounts(clients);
		List<Account> accounts = loadAccountsFromXML();
		System.out.println("Clients:");
		displayAccounts(accounts);
		System.out.println("");

		Map<Integer, Account> accountMap = loadAccountHashMap(accounts);
		System.out.println("Clients' accounts:");
		displayAccountHashMap(accountMap);
		System.out.println("");

//		List<Flow> flows = loadFlows(accounts);
		List<Flow> flows = loadFlowsFromJson();
		processFlows(flows, accountMap);
		System.out.println("Clients' accounts after flows:");
		displayAccountHashMap(accountMap);
	}
}
