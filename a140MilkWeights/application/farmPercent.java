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
import java.util.HashSet;

/**
 * Creates a farmPrecent object to use to generate percent tables for Date Range
 * Report and Annual Report. The object is later parsed by Property Value
 * Factories to generate the tables
 * 
 * @author benjaminraffel
 *
 */
public class farmPercent {

	private int ID;
	private String Percent;
	private int farmWeight;

	/**
	 * Constructor for creating a farmPercent object to be used in annual reports
	 * 
	 * @param farm        to generate percent for
	 * @param totalWeight of milk sold in that year
	 * @param year        of the annual report
	 */
	public farmPercent(Farm farm, int totalWeight, int year) {
		this.ID = farm.farmID;
		HashSet<Purchase> purchases = farm.getPurchasesInDate(new Date(year, 1, 0), new Date(year, 12, 32));
		farmWeight = 0;
		for (Purchase p : purchases) {
			farmWeight += p.getWeight();
		}
		double dpercent = ((double) farmWeight / (double) totalWeight) * 100;
		DecimalFormat df = new DecimalFormat("##.###");
		Percent = "" + df.format(dpercent) + "%";
	}

	/**
	 * Constructor to be used in the generating date range reports
	 * 
	 * @param farm        to generate percent for
	 * @param totalWeight total weight of milk sold in that range
	 * @param start       of the date range
	 * @param end         of the date range
	 */
	public farmPercent(Farm farm, int totalWeight, Date start, Date end) {
		this.ID = farm.farmID;
		HashSet<Purchase> purchases = farm.getPurchasesInDate(start, end);
		farmWeight = 0;
		for (Purchase p : purchases) {
			farmWeight += p.getWeight();
		}
		double dpercent = ((double) farmWeight / (double) totalWeight) * 100;
		DecimalFormat df = new DecimalFormat("##.###");
		Percent = "" + df.format(dpercent) + "%";
	}

	/**
	 * Returns the farm ID for this farmPercent
	 * 
	 * @return farmID
	 */
	public Integer getID() {
		// return Integer.valueOf(ID).toString();
		return Integer.valueOf(ID);
	}

	/**
	 * Returns String value of the percent of milk sold by this farm in this year or
	 * date range
	 * 
	 * @return String representation of percent milk sold
	 */
	public String getPercent() {
		return Percent;
	}

	/**
	 * Returns String representation of total milk sold by a specific farm used to
	 * create this farmPercent object
	 * 
	 * @return String representation of total milk sold by this farm
	 */
	public String getFarmWeight() {
		return Integer.valueOf(farmWeight).toString();
	}

}
