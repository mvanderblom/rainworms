package nl.mvdb.regenwormen;

import java.util.Scanner;

import nl.mvdb.regenwormen.io.ConsoleInputReader;
import nl.mvdb.regenwormen.io.ConsoleOutputWriter;
import nl.mvdb.regenwormen.io.UserInputReader;
import nl.mvdb.regenwormen.io.UserOutputWriter;

public class RegenWormenRunner {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		try {
			UserInputReader reader = new ConsoleInputReader(scanner);
			UserOutputWriter writer = new ConsoleOutputWriter();
			RegenWormenGame game = new RegenWormenGame(reader, writer);
			game.start();
		} finally {
			scanner.close();
		}
	}
}
