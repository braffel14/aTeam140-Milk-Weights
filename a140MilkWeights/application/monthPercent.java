package application;

import java.util.HashSet;

/**
 * Object to hold the percentage of sales done by a farm in a given month
 * 
 * @author benjaminraffel
 */
public class monthPercent {

	private String month;
	private String percent;

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
