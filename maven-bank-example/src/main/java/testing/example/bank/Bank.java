package testing.example.bank;

import java.util.Collection;
import java.util.NoSuchElementException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Bank {

	private static final Logger LOGGER = LogManager.getLogger(Bank.class);

	private Collection<BankAccount> bankAccounts;

	public Bank(Collection<BankAccount> bankAccounts) {
		this.bankAccounts = bankAccounts;
	}

	public int openNewBankAccount(double initialBalance) {
		BankAccount newBankAccount = new BankAccount();
		newBankAccount.setBalance(initialBalance);
		bankAccounts.add(newBankAccount);
		LOGGER.info("New account opened with id: " + newBankAccount.getId());
		return newBankAccount.getId();
	}

	public void deposit(int bankAccountId, double amount) {
		findBankAccountById(bankAccountId).deposit(amount);
		LOGGER.debug(() -> String.format("Success: deposit(%d, %.2f)", bankAccountId, amount));
	}

	public void withdraw(int bankAccountId, double amount) {
		findBankAccountById(bankAccountId).withdraw(amount);
		LOGGER.debug(() -> String.format("Success: withdraw(%d, %.2f)", bankAccountId, amount));
	}

	private BankAccount findBankAccountById(int bankAccountId) {
		return bankAccounts.stream()
			.filter(a -> a.getId() == bankAccountId)
			.findFirst()
			.orElseThrow(
				() -> new NoSuchElementException
					("No account found with id: " + bankAccountId));
	}
}
