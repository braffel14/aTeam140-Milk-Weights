/**
 * MonthPercent created by braffel on MacBook Pro in a140MilkWeights
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

import java.util.HashSet;

/**
 * Creates a monthPercent object to use to generate percent tables for farm
 * report. The object is later parsed by Property Value Factories to generate
 * the table
 * 
 * @author benjaminraffel
 */
public class monthPercent {

	private String month;
	private String percent;

	/**
	 * Constructor a new monthPercent Object
	 * 
	 * @param month   string representation of the month
	 * @param percent string representation of percent of milk sold by a farm that
	 *                month
	 */
	public monthPercent(String month, String percent) {
		this.month = month;
		this.percent = percent;
	}

	public String getMonth() {
		return month;
	}

	public String getPercent() {
		return percent;
	}

}
