package application;

public class MissingDataException extends Exception {
	/**
	 * default no-arg constructor
	 */
	public MissingDataException() {
	}

	/**
	 * This constructor is provided to allow user to include a message
	 * 
	 * @param msg Additional message for this exception
	 */
	public MissingDataException(String msg) {
		super(msg);
	}
}
