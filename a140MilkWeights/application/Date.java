/**
 * Date created by braffel on MacBook Pro in a140MilkWeights
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

import java.time.LocalDate;

/**
 * Object to represent a Date that a purchase was made on
 * 
 * @author benjaminraffel
 *
 */
public class Date implements Comparable<Date> {

	private int year;
	private int day;
	private int month;

	/**
	 * Creates a new Date object
	 * 
	 * @param year  the purchase was made
	 * @param month of the year the purchase was made
	 * @param day   of the month the purchase was made
	 * @throws InvalidDateException if the given month is not a valid month
	 */
	public Date(int year, int month, int day) {
		this.year = year;
		this.month = month;
		this.day = day;
	}

	/*
	 * Creates a new Date object using inforamtion fromm a java LocalDate object
	 * 
	 * @param LDate is the LocalDate to get information from
	 */
	public Date(LocalDate LDate) {
		this.year = LDate.getYear();
		this.month = LDate.getMonthValue();
		this.day = LDate.getDayOfMonth();
	}

	/**
	 * Gets the year the purchase was made
	 * 
	 * @return the year the purchase was made
	 */
	public int getNumYear() {
		return year;
	}

	/**
	 * Gets the month the purchase was made
	 * 
	 * @return the month the purchase was made
	 */
	public int getNumMonth() {
		return month;
	}

	/**
	 * Returns the day of the month the purchase was made
	 * 
	 * @return the day of the month the purchase was made
	 */
	public int getNumDay() {
		return day;
	}

	/**
	 * Returns a String representation of this date
	 */
	public String toString() {
		return ("" + month + "/" + day + "/" + year);
	}

	/**
	 * Allows this date to be compared to another date
	 * 
	 * @param od - the Date to compare this date to
	 * @return -1 if this date is before the other date, 0 if this date and the
	 *         other date are the same, and +1 if this date is after the other date
	 */
	@Override
	public int compareTo(Date od) {
		// compare year
		int thisYear = this.getNumYear();
		int odYear = od.getNumYear();
		if (thisYear < odYear) {
			return -1;
		} else if (thisYear > odYear) {
			return 1;
		} else {
			// compare month
			int odMonth = od.getNumMonth();
			if (this.month < odMonth) {
				return -1;
			} else if (this.month > odMonth) {
				return 1;
			} else {
				// compare day
				int odDay = od.getNumDay();
				int thisDay = this.getNumDay();
				if (thisDay < odDay) {
					return -1;
				}
				if (thisDay > odDay) {
					return 1;
				} else {
					return 0;
				}

			}
		}

	}

}
