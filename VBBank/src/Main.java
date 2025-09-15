import java.util.ArrayList;
import java.util.List;

import components.Client;

// 1.1.2 Creation of main class for tests
public class Main {
	public static List<Client> loadClients(int numberOfClients) {
		List<Client> clients = new ArrayList<>();
		
		for (int i = 1; i <= numberOfClients; i++) {
			clients.add(new Client("firstName" + i, "lastName" + i));
		}
		
		return clients;
	}
	
	public static void display(List<Client> clients) {
		clients.stream().forEach(client -> {
			System.out.println(client.toString());
		});
	}

	public static void main(String[] args) {
		List<Client> clients = loadClients(5);
		display(clients);
	}
}
