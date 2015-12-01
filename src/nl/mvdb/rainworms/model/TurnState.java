package nl.mvdb.rainworms.model;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.LinkedHashMap;
import java.util.Map;

public class TurnState implements Serializable {
	private static final long serialVersionUID = 8900707745523669141L;

	private int numberOfDice = 8;
	private Map<BigInteger, Integer> selectedDice = new LinkedHashMap<>();

	public int getNumberOfDice() {
		return numberOfDice;
	}

	public void setNumberOfDice(int numberOfDice) {
		this.numberOfDice = numberOfDice;
	}

	public Map<BigInteger, Integer> getSelectedDice() {
		return selectedDice;
	}
}
