package nl.mvdb.regenwormen.io;

import java.math.BigInteger;

public interface UserInputReader {
	
	BigInteger integer(String question);
	
	boolean bool(String question, String trueValue, String falseValue);
}
