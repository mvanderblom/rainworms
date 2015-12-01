package nl.mvdb.rainworms.model;

import nl.mvdb.rainworms.io.UserInputReader;
import nl.mvdb.rainworms.io.UserOutputWriter;

public class Game {

	private UserInputReader reader;
	private UserOutputWriter writer;

	public Game(UserInputReader reader, UserOutputWriter writer) {
		this.reader = reader;
		this.writer = writer;
	}

	public void start() {
		writer.info("Hallo regenwormen!");

		boolean nextTurn = true;
		while (nextTurn) {
			Turn turn = new Turn(reader, writer);
			nextTurn = turn.execute();
		}
	}

}
