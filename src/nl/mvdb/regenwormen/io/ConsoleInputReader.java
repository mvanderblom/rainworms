package nl.mvdb.regenwormen.io;

import java.math.BigInteger;
import java.util.Scanner;

public class ConsoleInputReader implements UserInputReader {

	private static final String INVALID_INPUT_LINE = "Ongeldige invoer...";
	private Scanner scanner;

	public ConsoleInputReader(Scanner s) {
		this.scanner = s;
	}

	@Override
	public boolean bool(String question, String trueValue, String falseValue) {
		String userInput = null;
		do {
			if (!trueValue.equalsIgnoreCase(userInput) && !falseValue.equalsIgnoreCase(userInput) && userInput != null)
				System.err.println(INVALID_INPUT_LINE);
			System.out.println(question + " (" + trueValue + "/" + falseValue + ")");

			userInput = scanner.nextLine();
		} while (!trueValue.equalsIgnoreCase(userInput) && !falseValue.equalsIgnoreCase(userInput));

		return trueValue.equalsIgnoreCase(userInput);
	}

	@Override
	public BigInteger integer(String question) {
		String userInput = null;

		boolean inputWasInt = true;
		do {
			try {
				if (!inputWasInt)
					System.err.println(INVALID_INPUT_LINE);
				System.out.println(question);

				userInput = scanner.nextLine();
				Integer.parseInt(userInput);
				inputWasInt = true;
			} catch (Exception e) {
				inputWasInt = false;
			}
		} while (!inputWasInt);

		return new BigInteger(userInput);
	}

}
