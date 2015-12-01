package nl.mvdb.regenwormen.io;

public class ConsoleOutputWriter implements UserOutputWriter {

	@Override
	public void info(String message) {
		System.out.println(message);
	}

	@Override
	public void error(String message) {
		System.err.println(message);
	}

}
