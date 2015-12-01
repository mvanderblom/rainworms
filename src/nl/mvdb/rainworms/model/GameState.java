package nl.mvdb.rainworms.model;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class GameState implements Serializable {
	private static final long serialVersionUID = -1909007390244694728L;

	@SuppressWarnings("unused")
	private List<Object> stones = new LinkedList<>();

	private List<Player> players = new LinkedList<>();
	private int activePlayerIndex = 0;

	private TurnState turnState;

	public void initTurnState() {
		this.turnState = new TurnState();
	}

	public TurnState getTurnState() {
		return turnState;
	}

	public void addPlayer(Player p) {
		players.add(p);
	}

	public List<Player> getPlayers() {
		return players;
	}

	public void nexPlayer() {
		if (activePlayerIndex + 1 >= players.size())
			activePlayerIndex = 0;
		else
			activePlayerIndex++;
	}

	public Player getActivePlayer() {
		return this.players.get(activePlayerIndex);
	}

	public boolean shouldNextTurnTakePlace() {
		return true;
	}

}
