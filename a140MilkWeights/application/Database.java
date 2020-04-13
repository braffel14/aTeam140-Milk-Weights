package application;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;

public class Database {

	public HashSet<Farm> allFarms;
	private String filePath;

	/**
	 * Default no-arg constructor to create a new database for a cheese factory
	 */
	public Database() {
		allFarms = new HashSet<Farm>();
		filePath = null;
	}

	/**
	 * Creates a new database for a cheese factory with a filePath to get purchases
	 * from
	 * 
	 * @param filePath to get initial purchase data
	 */
	public Database(String filePath) {
		allFarms = new HashSet<Farm>();
		this.filePath = filePath;
		parseInput(filePath);
	}

	/**
	 * Gets the total weight of milk purchased by this farm
	 * 
	 * @return total weight of milk purchased by this farm
	 */
	public int getTotalWeight() {
		int totalWeight = 0;
		for (Farm f : allFarms) {
			totalWeight += f.getFarmWeight();
		}

		return totalWeight;
	}

	/**
	 * Get a list of all purchases made by this cheese factory
	 * 
	 * @return HashSet<Purchase> of all purchases made by this cheese factory
	 */
	public HashSet<Purchase> getAllPurchases() {
		HashSet<Purchase> allPurchases = new HashSet<Purchase>();

		for (Farm f : allFarms) {
			allPurchases.addAll(f.getAllPurchases());
		}

		return allPurchases;
	}

	/**
	 * Get list of purchase made by this cheese factory in a certian date range
	 * 
	 * @param start of the date range
	 * @param end   of the date range
	 * @return HashSet<Purchase> of all purchases made by this cheese factory in a
	 *         date range
	 */
	public HashSet<Purchase> getPurchasesInRange(Date start, Date end) {
		HashSet<Purchase> purchases = new HashSet<Purchase>();

		for (Farm f : allFarms) {
			purchases.addAll(f.getPurchasesInDate(start, end));
		}

		return purchases;
	}

	/**
	 * Parse purchases from a file and add them to the database
	 * 
	 * @param filePath of the file to get purchases from
	 */
	public void parseInput(String filePath) {
		try {
			String line;
			BufferedReader br = new BufferedReader(new FileReader(filePath));
			while ((line = br.readLine()) != null) {

				String[] lineArray = line.split(",");

				if (!lineArray[0].equals("date")) {
					// parse date
					String[] dateArray = lineArray[0].split("-");
					int year = Integer.parseInt(dateArray[0]);
					int month = Integer.parseInt(dateArray[1]);
					int day = Integer.parseInt(dateArray[2]);

					// parse farmID
					int farmID = Integer.parseInt(lineArray[1].substring(5));

					// parse weight
					int weight = Integer.parseInt(lineArray[2]);

					// add purchase to database
					addPurchase(year, month, day, farmID, weight);
				}
			}
			if (br != null) {
				try {
					br.close();
				} catch (IOException ioe) {
					ioe.printStackTrace();
				}
			}
		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Manually add a new purchase made by this cheese factory. Adds purchase to the
	 * correct farm
	 * 
	 * @param year   the purchase was made
	 * @param month  of the year the purchase was made
	 * @param day    of the month the purchase was made
	 * @param farmID of the farm that made the purchase
	 * @param weight of milk purchased
	 */
	public void addPurchase(int year, int month, int day, int farmID, int weight) {
		boolean added = false;
		Date date = new Date(year, month, day);
		for (Farm f : allFarms) {
			if (f.farmID == farmID) {
				f.addPurchase(date, weight);
				added = true;
			}
		}
		if (!added) {
			Farm newFarm = new Farm(farmID);
			newFarm.addPurchase(date, weight);
			allFarms.add(newFarm);
		}
	}

	/**
	 * Checks if a farm id exists int the database
	 * 
	 * @param farmID of the farm to check for
	 * @return true if farm exists, false if not
	 */
	public boolean hasFarm(int farmID) {
		for (Farm f : allFarms) {
			if (f.farmID == farmID)
				return true;
		}
		return false;
	}

}
