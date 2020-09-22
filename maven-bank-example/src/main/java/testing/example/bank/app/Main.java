package testing.example.bank.app;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import testing.example.bank.Bank;

public class Main {

	private static final Logger LOGGER = LogManager.getLogger(Main.class);

	public static void main(String[] args) {
		LOGGER.info("App started");
		Bank bank = new Bank(new ArrayList<>());
		int bankAccountId = bank.openNewBankAccount(10);
		bank.deposit(bankAccountId, 20);
		LOGGER.info("App terminated");
	}
}
