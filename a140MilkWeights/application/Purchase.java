/**
 * Purchase created by braffel on MacBook Pro in a140MilkWeights
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
 * Class to represent a single purchase made from a farm
 * 
 * @author benjaminraffel
 *
 */
public class Purchase {

	// private instance variables
	private Date date;
	private String dateString;
	private int farmID;
	private int weight;

	/**
	 * Creates a new Purchase object
	 * 
	 * @param date   purchase was made
	 * @param farmID of the farm that the purchase was made from
	 * @param weight of milk purchased
	 */
	public Purchase(Date date, int farmID, int weight) {
		this.date = date;
		this.farmID = farmID;
		this.weight = weight;
		this.dateString = date.toString();
	}

	/**
	 * Get the date the purchase was made
	 * 
	 * @return Date of the purchase
	 */
	public Date getPurchaseDate() {
		return this.date;
	}

	public String getDateString() {
		dateString = date.toString();
		return dateString;
	}

	/**
	 * get the ID of the farm that made the purchase
	 * 
	 * @return farmID of the farm that made the purchase
	 */
	public int getFarmID() {
		return farmID;
	}

	/**
	 * get the amount of Milk purchased
	 * 
	 * @return the weight of milk purchased
	 */
	public int getWeight() {
		return weight;
	}

	/**
	 * Creates and returns a string representation of the purchase showing the farm
	 * that made the purchase, the amount of milk purchase, and the date the
	 * purchase was made
	 */
	public String toString() {
		return "Purchase by farm " + farmID + " of " + weight + " pounds on " + date.toString();
	}

	/**
	 * Prints String representation of purchase to console
	 */
	public void print() {
		System.out.println(toString());
	}

}
