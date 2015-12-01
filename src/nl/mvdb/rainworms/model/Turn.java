package nl.mvdb.rainworms.model;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import nl.mvdb.rainworms.io.UserInputReader;
import nl.mvdb.rainworms.io.UserOutputWriter;

public class Turn {
	private static final BigInteger WORM_NUMERICAL = BigInteger.valueOf(6);
	private static final BigInteger NEXT_TURN_NUMERICAL = BigInteger.valueOf(0);

	private UserInputReader reader;
	private UserOutputWriter writer;

	public Turn(UserInputReader reader, UserOutputWriter writer) {
		this.reader = reader;
		this.writer = writer;
	}

	public void execute(GameState gameState) {
		if (!reader.bool("Klaar voor de volgende beurt?", "J", "N"))
			return;

		TurnState state = gameState.getTurnState();

		// beurt loop
		while (true) {
			if (state.getNumberOfDice() == 0) {
				writer.error("Er zijn geen dobbelstenen mee over...");
				break;
			}

			// Gooien
			List<BigInteger> throw_ = throwDice(state.getNumberOfDice());

			// Apart leggen
			BigInteger selectedDie = null;
			while (!isValid(state.getSelectedDice(), throw_, selectedDie)) {
				if (selectedDie != null) {
					if (state.getSelectedDice().containsKey(selectedDie))
						writer.error("Deze dobbelstenen heb je al apart gelegd.");
					else if (!throw_.contains(selectedDie))
						writer.error("Deze dobbelstenen heb je niet gegooid.");
				}
				selectedDie = reader.integer("Wat wil je apart leggen? (1 t/m 6, 0 voor volgende beurt)");
			}

			if (NEXT_TURN_NUMERICAL.equals(selectedDie))
				break;

			// State bijwerken
			Integer count = countDice(throw_, selectedDie);
			state.getSelectedDice().put(selectedDie, count);
			state.setNumberOfDice(state.getNumberOfDice() - count);

			int total = getTotal(state.getSelectedDice());

			// Endstate: geldige worp, nog een keer?
			if (total >= 21 && state.getSelectedDice().containsKey(WORM_NUMERICAL) && state.getNumberOfDice() > 0) {
				boolean nogEenWorp = reader.bool("Er zijn nog " + state.getNumberOfDice() + " dobbelsten(en) over. Nog een keer gooien?", "J", "N");
				if (!nogEenWorp)
					break;
			}
		}
	}

	private boolean isValid(Map<BigInteger, Integer> selectedDice, List<BigInteger> throw_, BigInteger selectedDie) {
		if (NEXT_TURN_NUMERICAL.equals(selectedDie))
			return true;
		if (!selectedDice.containsKey(selectedDie) && throw_.contains(selectedDie))
			return true;
		return false;
	}

	private Integer countDice(List<BigInteger> dice, final BigInteger die) {
		long count = dice.stream().filter(d -> d.equals(die)).count();
		return Integer.valueOf("" + count);
	}

	private int getTotal(Map<BigInteger, Integer> selectedDice) {
		int total = 0;
		writer.info("Apart gelegd:");
		List<BigInteger> keySet = selectedDice.keySet().stream().sorted().collect(Collectors.toList());
		for (BigInteger dobbelSteen : keySet) {
			boolean isRegenworm = dobbelSteen.equals(WORM_NUMERICAL);
			total += selectedDice.get(dobbelSteen) * (isRegenworm ? 5 : dobbelSteen.intValue());
			writer.info(selectedDice.get(dobbelSteen) + " keer een " + (isRegenworm ? "Regenworm" : dobbelSteen.intValue()));
		}
		writer.info("Totaal: " + total);
		return total;
	}

	private List<BigInteger> throwDice(int numberOfDice) {
		List<BigInteger> dice = new ArrayList<>();
		for (int i = 0; i < numberOfDice; i++) {
			dice.add(randomDie());
		}
		Collections.sort(dice);

		writer.info("Worp met " + numberOfDice + " dobbelstenen");
		writer.info(dice.toString());

		return dice;
	}

	private BigInteger randomDie() {
		return BigInteger.valueOf((long) Math.ceil(Math.random() * 6));
	}

}
