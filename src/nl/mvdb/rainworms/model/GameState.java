package nl.mvdb.rainworms.model;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class GameState implements Serializable {
	// Serializable in order to save game state.
	private static final long serialVersionUID = -1909007390244694728L;

	// game scope
	private List<Object> stones;
	private List<Object> players;

	// turn scope
	private int numberOfDice;
	private Map<BigInteger, Integer> selectedDice;

	public void initTurn() {
		this.numberOfDice = 8;
		selectedDice = new LinkedHashMap<>();
	}

	public int getNumberOfDice() {
		return numberOfDice;
	}

	public void setNumberOfDice(int numberOfDice) {
		this.numberOfDice = numberOfDice;
	}

	public Map<BigInteger, Integer> getSelectedDice() {
		return selectedDice;
	}

	public boolean shouldNextTurnTakePlace() {
		return true;
	}

}
