package nl.mvdb.rainworms.model;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.LinkedHashMap;
import java.util.Map;

import nl.mvdb.rainworms.Constants;

public class TurnState implements Constants, Serializable {
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

	public int getTotal() {

		return selectedDice.entrySet().stream() //
				.map(x -> {
					return x.getKey().intValue() * x.getValue().intValue();
				}) //
				.reduce((x1, x2) -> {
					return x1 + x2;
				}) //
				.get();
	}

	public boolean canTakeSomeStone() {
		return getTotal() >= 21 && selectedDice.containsKey(WORM_NUMERICAL);
	}

	public boolean hasDiceLeft() {
		return numberOfDice > 0;
	}
}
