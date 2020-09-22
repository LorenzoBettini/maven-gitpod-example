package testing.example.bank;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

public class BankTest {

	private Bank bank;

	// the collaborator of Bank that we manually instrument and inspect
	private List<BankAccount> bankAccounts;

	@Before
	public void setup() {
		bankAccounts = new ArrayList<BankAccount>();
		bank = new Bank(bankAccounts);
	}

	@Test
	public void testOpenNewAccountShouldReturnAPositiveIdAndStoreTheAccount() {
		int newAccountId = bank.openNewBankAccount(0);
		assertThat(newAccountId).isGreaterThan(0);
		assertThat(bankAccounts).
			hasSize(1).
			extracting(BankAccount::getId).
				contains(newAccountId);
	}

	@Test
	public void testDepositWhenAccountIsNotFoundShouldThrow() {
		assertThatThrownBy(() -> bank.deposit(1, 10))
			.isInstanceOf(NoSuchElementException.class)
			.hasMessage("No account found with id: 1");
	}

	@Test
	public void testDepositWhenAccountIsFoundShouldIncrementBalance() {
		// setup
		BankAccount testAccount = createTestAccount(10);
		bankAccounts.add(testAccount);
		// exercise
		bank.deposit(testAccount.getId(), 5);
		// verify
		assertThat(testAccount.getBalance()).isEqualTo(15);
	}

	@Test
	public void testWithdrawWhenAccountIsNotFoundShouldThrow() {
		assertThatThrownBy(() -> bank.withdraw(1, 10))
			.isInstanceOf(NoSuchElementException.class)
			.hasMessage("No account found with id: 1");
	}

	@Test
	public void testWithdrawWhenAccountIsFoundShouldDecrementBalance() {
		// setup
		BankAccount testAccount = createTestAccount(10);
		bankAccounts.add(testAccount);
		// exercise
		bank.withdraw(testAccount.getId(), 5);
		// verify
		assertThat(testAccount.getBalance()).isEqualTo(5);
	}

	/**
	 * Utility method for creating a BankAccount for testing.
	 */
	private BankAccount createTestAccount(double initialBalance) {
		BankAccount bankAccount = new BankAccount();
		bankAccount.setBalance(initialBalance);
		return bankAccount;
	}
}
