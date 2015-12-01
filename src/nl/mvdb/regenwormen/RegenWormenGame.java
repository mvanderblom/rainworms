package nl.mvdb.regenwormen;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import nl.mvdb.regenwormen.io.UserInputReader;
import nl.mvdb.regenwormen.io.UserOutputWriter;

public class RegenWormenGame {

	private static final BigInteger WORM_NUMERICAL = BigInteger.valueOf(6);
	private static final BigInteger NEXT_TURN_NUMERICAL = BigInteger.valueOf(0);

	private UserInputReader reader;
	private UserOutputWriter writer;

	public RegenWormenGame(UserInputReader reader, UserOutputWriter writer) {
		this.reader = reader;
		this.writer = writer;
	}

	public void start() {
		writer.info("Hallo regenwormen!");

		// game loop
		while (true) {
			int numberOfDice = 8;
			Map<BigInteger, Integer> apartGelegdeDobbelstenen = new LinkedHashMap<>();

			if (!reader.bool("Klaar voor de volgende beurt?", "J", "N"))
				break;

			// beurt loop
			while (true) {
				if (numberOfDice == 0) {
					writer.error("Er zijn geen dobbelstenen mee over...");
					break;
				}

				// Gooien
				List<BigInteger> worp = throwDice(numberOfDice);

				// Apart leggen
				BigInteger apartTeLeggen = null;
				while (!isValid(apartGelegdeDobbelstenen, worp, apartTeLeggen)) {
					if (apartTeLeggen != null) {
						if (apartGelegdeDobbelstenen.containsKey(apartTeLeggen))
							writer.error("Deze dobbelstenen heb je al apart gelegd.");
						else if (!worp.contains(apartTeLeggen))
							writer.error("Deze dobbelstenen heb je niet gegooid.");
					}
					apartTeLeggen = reader.integer("Wat wil je apart leggen? (1 t/m 6, 0 voor volgende beurt)");
				}

				if (NEXT_TURN_NUMERICAL.equals(apartTeLeggen))
					break;

				// State bijwerken
				Integer aantalInWorp = countDice(worp, apartTeLeggen);
				apartGelegdeDobbelstenen.put(apartTeLeggen, aantalInWorp);
				numberOfDice -= aantalInWorp;

				int total = getTotal(apartGelegdeDobbelstenen);

				// Endstate: geldige worp, nog een keer?
				if (total >= 21 && apartGelegdeDobbelstenen.containsKey(WORM_NUMERICAL) && numberOfDice > 0) {
					boolean nogEenWorp = reader.bool("Er zijn nog " + numberOfDice + " dobbelsten(en) over. Nog een keer gooien?", "J", "N");
					if (!nogEenWorp)
						break;
				}
			}
		}
	}

	private boolean isValid(Map<BigInteger, Integer> apartGelegdeDobbelstenen, List<BigInteger> worp, BigInteger apartTeLeggen) {
		if (NEXT_TURN_NUMERICAL.equals(apartTeLeggen))
			return true;
		if (!apartGelegdeDobbelstenen.containsKey(apartTeLeggen) && worp.contains(apartTeLeggen))
			return true;
		return false;
	}

	private Integer countDice(List<BigInteger> dice, final BigInteger die) {
		long count = dice.stream().filter(d -> d.equals(die)).count();
		return Integer.valueOf("" + count);
	}

	private int getTotal(Map<BigInteger, Integer> map) {
		int total = 0;
		writer.info("Apart gelegd:");
		List<BigInteger> keySet = map.keySet().stream().sorted().collect(Collectors.toList());
		for (BigInteger dobbelSteen : keySet) {
			boolean isRegenworm = dobbelSteen.equals(WORM_NUMERICAL);
			total += map.get(dobbelSteen) * (isRegenworm ? 5 : dobbelSteen.intValue());
			writer.info(map.get(dobbelSteen) + " keer een " + (isRegenworm ? "Regenworm" : dobbelSteen.intValue()));
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
