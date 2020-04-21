package application;

import java.text.DecimalFormat;
import java.util.HashSet;

public class farmPercent {

	private int ID;
	private String Percent;
	private int farmWeight;

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

	public String getID() {
		return Integer.valueOf(ID).toString();
	}

	public String getPercent() {
		return Percent;
	}

	public String getFarmWeight() {
		return Integer.valueOf(farmWeight).toString();
	}

}
