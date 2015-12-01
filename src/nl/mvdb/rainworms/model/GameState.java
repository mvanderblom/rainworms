package nl.mvdb.rainworms.model;

import java.io.Serializable;
import java.util.List;

public class GameState implements Serializable {
	private static final long serialVersionUID = -1909007390244694728L;

	private List<Object> stones;
	private List<Object> players;
	private TurnState turnState;

	public void initTurnState() {
		this.turnState = new TurnState();
	}

	public TurnState getTurnState() {
		return turnState;
	}

	public boolean shouldNextTurnTakePlace() {
		return true;
	}

}
