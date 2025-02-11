package nl.mvdb.rainworms.io;

import java.math.BigInteger;

public interface UserInputReader {

	BigInteger integer(String question);

	String string(String question);

	boolean bool(String question, String trueValue, String falseValue);
}
