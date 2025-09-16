package components;

import java.util.List;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "accounts")
public class AccountList {
	@JacksonXmlElementWrapper(useWrapping = false)
	@JacksonXmlProperty(localName = "account")
	private List<Account> accounts;
	
	public AccountList() {}

	public AccountList(List<Account> accounts) {
		this.accounts = accounts;
	}
	
	public List<Account> getAccounts() {
		return this.accounts;
	}
}
