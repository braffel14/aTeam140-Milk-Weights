package application;

import java.util.HashSet;

public class Farm {

	public int farmID;
	private HashSet<Purchase> purchases;

	public Farm(int farmID) {
		this.farmID = farmID;
		purchases = new HashSet<Purchase>();
	}

	public HashSet<Purchase> getAllPurchases() {
		return purchases;
	}

	public int getNumPurchases() {
		return purchases.size();
	}

	public HashSet<Purchase> getPurchasesInDate(Date start, Date end) {
		HashSet<Purchase> toReturn = new HashSet<Purchase>();

		//check each purchase is in proper range
		for (Purchase p : purchases) {
			if (p.getPurchaseDate().compareTo(start) >= 0 && p.getPurchaseDate().compareTo(end) <= 0) {
				toReturn.add(p);
			}
		}

		return toReturn;
	}
	
	public void addPurchase(Date date, int weight) {
		purchases.add(new Purchase(date, this.farmID, weight));
	}

}
