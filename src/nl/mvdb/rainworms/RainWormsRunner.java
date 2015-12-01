package nl.mvdb.rainworms;

import java.util.Scanner;

import nl.mvdb.rainworms.io.ConsoleInputReader;
import nl.mvdb.rainworms.io.ConsoleOutputWriter;
import nl.mvdb.rainworms.io.UserInputReader;
import nl.mvdb.rainworms.io.UserOutputWriter;
import nl.mvdb.rainworms.model.Game;

public class RainWormsRunner {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		try {
			UserInputReader reader = new ConsoleInputReader(scanner);
			UserOutputWriter writer = new ConsoleOutputWriter();
			Game game = new Game(reader, writer);
			game.start();
		} finally {
			scanner.close();
		}
	}
}
