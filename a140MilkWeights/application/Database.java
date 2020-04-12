package application;

import java.util.HashSet;

public class Database {

	public HashSet<Farm> allFarms;
	private int totalWeight;
	private String filePath;

	public Database() {
		allFarms = new HashSet<Farm>();
		totalWeight = 0;
		filePath = null;
	}
	
	public Database(String filePath) {
		allFarms = new HashSet<Farm>();
		totalWeight = 0;
		this.filePath = filePath;
		parseInput(filePath);
	}
	

	public int getTotalWeight() {
		return totalWeight;
	}

	public HashSet<Purchase> getAllPurchases() {
		HashSet<Purchase> allPurchases = new HashSet<Purchase>();

		for (Farm f : allFarms) {
			allPurchases.addAll(f.getAllPurchases());
		}

		return allPurchases;
	}

	public HashSet<Purchase> getPurchasesInRange(Date start, Date end) {
		HashSet<Purchase> purchases = new HashSet<Purchase>();

		for (Farm f : allFarms) {
			purchases.addAll(f.getPurchasesInDate(start, end));
		}

		return purchases;
	}
	
	public void parseInput(String filePath) {
		//TODO: implement this
	}

}
