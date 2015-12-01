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
		GameState gameState = new GameState();
		while (true) {
			gameState.initTurnState();
			Turn turn = new Turn(reader, writer);
			turn.execute(gameState);

			if (!gameState.shouldNextTurnTakePlace())
				break;
		}
	}

}
