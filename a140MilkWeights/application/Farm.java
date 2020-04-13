package application;

import java.util.HashSet;

/**
 * Represents a farm that the factory purchases milk from
 * 
 * @author benjaminraffel
 */
public class Farm {

	// private instance variables
	public int farmID;
	private HashSet<Purchase> purchases;

	/**
	 * Creates a new farm with ID farmID and an empty purchases list
	 * 
	 * @param farmID the ID of the farm that is being created
	 */
	public Farm(int farmID) {
		this.farmID = farmID;
		purchases = new HashSet<Purchase>();
	}

	/**
	 * Gets the list of all of the purchases made from this farm
	 * 
	 * @return a HashSet<Purchase> of all of the purchases made from this farm
	 */
	public HashSet<Purchase> getAllPurchases() {
		return purchases;
	}

	/**
	 * Gets the number of purchases made from this farm
	 * 
	 * @return
	 */
	public int getNumPurchases() {
		return purchases.size();
	}

	/**
	 * Returns a list of purchases made from this farm in a certain date range
	 * 
	 * @param start - beginnnign of the date range
	 * @param end   - end of the date range
	 * @return HashSet<Purchase> that's a list of purchases made by the farm in the
	 *         specified date range
	 */
	public HashSet<Purchase> getPurchasesInDate(Date start, Date end) {
		HashSet<Purchase> toReturn = new HashSet<Purchase>();

		// check each purchase is in proper range
		for (Purchase p : purchases) {
			if (p.getPurchaseDate().compareTo(start) >= 0 && p.getPurchaseDate().compareTo(end) <= 0) {
				toReturn.add(p);
			}
		}

		return toReturn;
	}

	/**
	 * Adds a new purchase to this farms list with a specified date and weight
	 * 
	 * @param date   the purchase was made
	 * @param weight of the milk purchased
	 */
	public void addPurchase(Date date, int weight) {
		Purchase toAdd = new Purchase(date, this.farmID, weight);
		toAdd.print();
		purchases.add(toAdd);
	}

	/**
	 * Get the total amount of milk purchased from this farm
	 * 
	 * @return total weight of milk purchases from this farm
	 */
	public int getFarmWeight() {
		int totalWeight = 0;
		for (Purchase p : purchases) {
			totalWeight += p.getPurchaseWeight();
		}
		return totalWeight;
	}

}
