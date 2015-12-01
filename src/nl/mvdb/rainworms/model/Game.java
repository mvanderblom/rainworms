package nl.mvdb.rainworms.model;

import java.math.BigInteger;

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

		BigInteger numberOfPlayers = reader.integer("Met hoeveel personen wil je spelen?");
		for (int i = 0; i < numberOfPlayers.intValue(); i++) {
			String playerName = reader.string("Geef de naam op voor speler " + (i + 1) + ":");
			gameState.addPlayer(new Player(playerName));
		}

		while (true) {
			gameState.initTurnState();

			Turn turn = new Turn(reader, writer);
			turn.execute(gameState);

			if (!gameState.shouldNextTurnTakePlace())
				break;

			gameState.nexPlayer();
		}
	}
}
