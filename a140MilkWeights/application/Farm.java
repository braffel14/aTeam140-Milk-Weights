/**
 * Farm created by braffel on MacBook Pro in a140MilkWeights
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

import java.text.DecimalFormat;
import java.util.ArrayList;
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
	private ArrayList<String> monthPercent;
	private int[] monthWeight;

	/**
	 * Creates a new farm with ID farmID and an empty purchases list
	 * 
	 * @param farmID the ID of the farm that is being created
	 */
	public Farm(int farmID) {
		this.farmID = farmID;
		purchases = new HashSet<Purchase>();
		monthWeight = new int[12];
		monthPercent = generateMonthPercent();

	}

	/**
	 * Generates an arraylist with a percentage of milk sold by this farm farm
	 * compared to all farms in the report being generated
	 * 
	 * @return ArrayList of length 12 with a percentage value for each month of the
	 *         year
	 */
	private ArrayList<String> generateMonthPercent() {
		ArrayList<String> monthPercent = new ArrayList<String>();

		monthWeight = new int[12];

		// fill monthWeight
		for (Purchase p : purchases) {
			monthWeight[p.getPurchaseDate().getNumMonth() - 1] += p.getWeight();
		}

		for (int i = 0; i < 12; i++) {
			if (getFarmWeight() > 0) {
				double percent = ((double) monthWeight[i] / (double) getFarmWeight()) * 100;
				DecimalFormat df = new DecimalFormat("##.###");
				monthPercent.add("" + df.format(percent) + "%");
			} else {
				monthPercent.add("0%");
			}

		}

		return monthPercent;
	}

	/**
	 * Default no-arg constructor to create a blank farm
	 */
	public Farm() {
		this.farmID = -1;
		purchases = new HashSet<Purchase>();
		monthWeight = new int[12];
		monthPercent = generateMonthPercent();

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
		System.out.print("Adding ");
		toAdd.print();
		purchases.add(toAdd);
		monthPercent = generateMonthPercent();
	}

	/**
	 * Removes a purchase from this farm. compares weight and purchase date to make
	 * sure they match
	 * 
	 * @param toRem is the purchase to remove
	 */
	public void removePurchase(Purchase toRem) {
		Purchase toRemove = null;
		for (Purchase p : purchases) {
			if (p.getWeight() == toRem.getWeight() && p.getPurchaseDate().compareTo(toRem.getPurchaseDate()) == 0) {
				toRemove = p;
			}
		}
		if (toRemove != null) {
			purchases.remove(toRemove);
		}
	}

	/**
	 * Get the total amount of milk purchased from this farm
	 * 
	 * @return total weight of milk purchases from this farm
	 */
	public int getFarmWeight() {
		int totalWeight = 0;
		for (Purchase p : purchases) {
			totalWeight += p.getWeight();
		}
		return totalWeight;
	}

	/**
	 * Returns generated ArrayList for percentage of milk sold each month
	 * 
	 * @return generated ArrayList for percentage of milk sold each month
	 */
	public ArrayList<String> getMonthPercentages() {
		monthPercent = generateMonthPercent();
		return monthPercent;
	}

}
