import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import components.Account;
import components.AccountList;
import components.Client;
import components.Credit;
import components.CurrentAccount;
import components.Debit;
import components.Flow;
import components.SavingsAccount;
import components.Transfer;
import net.datafaker.Faker;
import net.datafaker.providers.base.Name;
import utils.FileManager;

// 1.1.2 Creation of main class for tests
public class Main {
	public static List<Client> loadClients(int numberOfClients) {
		// Add fake data to generate clients
		Faker faker = new Faker(Locale.FRANCE);

		List<Client> clients = IntStream.range(0, numberOfClients).mapToObj(idx -> {
			Name name = faker.name();
			return new Client(name.firstName(), name.lastName());
		}).toList();

		// save clients in a json file
		FileManager.saveAsJson(clients, "./clients.json");

		return clients;
	}

	public static List<Client> loadClientsFromJson() {
		return FileManager.loadJsonFile("./clients.json", Client.class);
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

		// save accounts in an xml file
		FileManager.saveAsXml(new AccountList(accounts), "./accounts.xml");

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
				.ifPresent(acc -> System.out.println("Account " + acc.getAccountNumber() + " has a negative value!\n"));
	}

	// 2.1 JSON file of flows
	public static List<Flow> loadFlowsFromJson() {
		return FileManager.loadJsonFile("./flows.json", Flow.class);
	}

	// 2.2 XML file of accounts
	public static List<Account> loadAccountsFromXML() {
		return FileManager.<Account>loadXmlFile("./accounts.xml", Account.class);
	}

	public static void loadFromMain() {
		List<Client> clients = loadClients(5);
		List<Account> accounts = loadAccounts(clients);
		Map<Integer, Account> accountMap = loadAccountHashMap(accounts);
		List<Flow> flows = loadFlows(accounts);

		display(clients, accounts, accountMap, flows);
	}

	public static void loadFromFiles() {
		List<Client> clients = loadClientsFromJson();
		List<Account> accounts = loadAccountsFromXML();
		Map<Integer, Account> accountMap = loadAccountHashMap(accounts);
		List<Flow> flows = loadFlowsFromJson();

		display(clients, accounts, accountMap, flows);
	}

	public static void display(List<Client> clients, List<Account> accounts, Map<Integer, Account> accountMap,
			List<Flow> flows) {
		System.out.println("Clients:");
		displayClients(clients);
		System.out.println("");

		System.out.println("Accounts:");
		displayAccounts(accounts);
		System.out.println("");

		System.out.println("Clients' accounts:");
		displayAccountHashMap(accountMap);
		System.out.println("");

		processFlows(flows, accountMap);
		System.out.println("Clients' accounts after flows:");
		displayAccountHashMap(accountMap);
	}

	public static void main(String[] args) {
//		loadFromMain();
		loadFromFiles();
	}
}
