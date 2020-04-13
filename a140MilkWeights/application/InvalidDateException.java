package application;

public class InvalidDateException extends Exception {
	/**
	 * default no-arg constructor
	 */
	public InvalidDateException() {
	}

	/**
	 * This constructor is provided to allow user to include a message
	 * 
	 * @param msg Additional message for this exception
	 */
	public InvalidDateException(String msg) {
		super(msg);
	}
}
