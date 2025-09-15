package components;

// 1.1.1 Creation of the client class
public class Client {
	private String firstName;
	private String lastName;
	private int clientNumber;
	private static int clientCount;
	
	public Client(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.clientNumber = ++clientCount;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getClientNumber() {
		return clientNumber;
	}

	public void setClientNumber(int clientNumber) {
		this.clientNumber = clientNumber;
	}

	public static int getClientCount() {
		return clientCount;
	}

	@Override
	public String toString() {
		return "Client [firstName=" + firstName + ", lastName=" + lastName + ", clientNumber=" + clientNumber + "]";
	}
}
