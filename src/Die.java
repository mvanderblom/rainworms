import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

public class Die {
	private static final BigInteger NEXT_TURN_NUMERICAL = BigInteger.valueOf(0);
	private static final BigInteger WORM_NUMERICAL = BigInteger.valueOf(6);
	private BigInteger value;

	
	public Die() {
		this.value = BigInteger.valueOf((long) Math.ceil(Math.random() * 6));
	}

	public Die(BigInteger value) {
		this.value = value;
	}

	public BigInteger getValue() {
		return value;
	}

	@Override
	public String toString() {
		return WORM_NUMERICAL.equals(this.value) ? "Regenworm" : "" + this.value.intValue();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Die)
			return value.equals(((Die)obj).value);
		return value.equals(obj);
	}

	public static void main(String[] args) throws IOException {
		
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("Hallo regenwormen!");
		// game loop
		while (true) {
			LinkedHashMap<BigInteger, Integer> map = new LinkedHashMap<>();
			int numberOfDice = 8;
			
			System.out.print("Klaar voor de volgende beurt? (J/N)");
			String volgendeBeurt = scanner.next();
			if("n".equalsIgnoreCase(volgendeBeurt))
				break;
			
			// beurt loop
			while(true){
				if(numberOfDice == 0){
					System.out.println("Er zijn geen dobbelstenen mee over...");
					break;
				}
				
				// Gooien
				ArrayList<Die> dice = new ArrayList<Die>();
				for (int i = 0; i < numberOfDice; i++) {
					dice.add(new Die());
				}
				dice.sort((c, c_) -> c.value.compareTo(c_.value));
				
				System.out.println("Worp met " + numberOfDice + " dobbelstenen");
				System.out.println(dice);

				// Apart leggen
				BigInteger apartLeggen = null; 
				while(!NEXT_TURN_NUMERICAL.equals(apartLeggen) && (apartLeggen == null || map.containsKey(apartLeggen) || !dice.contains(new Die(apartLeggen)))){
					if(apartLeggen != null  && (map.containsKey(apartLeggen) || !dice.contains(new Die(apartLeggen))))
						System.out.println("Ongeldige keuze!");
					System.out.println("Wat wil je apart leggen? (1 t/m 6, 0 voor volgende beurt)");
					apartLeggen = scanner.nextBigInteger();	
				}
				
				if(NEXT_TURN_NUMERICAL.equals(apartLeggen))
					break;
				
				// State bijwerken
				final BigInteger filter = apartLeggen;
				int count = (int)dice.stream()
						.filter(d -> d.getValue().equals(filter))
						.count();
				map.put(apartLeggen, Integer.valueOf(count));
				numberOfDice -= count;
				
				System.out.println("Apart gelegd:");
				int total = 0;
				List<BigInteger> keySet = map.keySet().stream()
						.sorted()
						.collect(Collectors.toList());
				for (BigInteger dobbelSteen : keySet) {
					boolean isRegenworm = dobbelSteen.equals(WORM_NUMERICAL);
					total += map.get(dobbelSteen) * (isRegenworm ? 5 : dobbelSteen.intValue());
					System.out.println(map.get(dobbelSteen) + " keer een " + (isRegenworm ? "Regenworm" : dobbelSteen.intValue()));
				}
				System.out.println("Totaal: " + total);
				
				// Endstate: geldige worp, nog een keer?
				if(total > 21 && map.containsKey(WORM_NUMERICAL) && numberOfDice > 0){
					System.out.print("Er zijn nog " + numberOfDice + " dobbelsten(en) over. Nog een keer gooien? (J/N)");
					String nogEenWorp = scanner.next();
					if("n".equalsIgnoreCase(nogEenWorp))
						break;
				}
			}
			// einde beurt loop
			
		}
		// einde game loop
		scanner.close();
	}
}
