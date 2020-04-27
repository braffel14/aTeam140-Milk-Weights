/**
 * InvalidDateException created by braffel on MacBook Pro in a140MilkWeights
 *
 * Author:      Benjamin Raffel (braffel@wisc.edu)
 * Date:        4/27/2020
 * 
 * Course:      CS400
 * Semester:    Spring 2020
 * Lecture:     001
 *
 * List Collaboratprs: n/a
 *
 * Other Credits: n/a
 *
 * Known Bugs: n/a
 */
package application;

/**
 * Exception to be thrown when parsing data or creating a data that does not
 * have valid data or with data in improper format
 * 
 * @author benjaminraffel
 *
 */
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
