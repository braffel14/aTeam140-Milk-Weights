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
	 * Prints purchase to console
	 */
	public void print() {
		System.out.println("Purchase by farm " + farmID + " of " + weight + " pounds on " + date.toString());
	}

}
