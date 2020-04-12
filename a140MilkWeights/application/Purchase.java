package application;

public class Purchase {

	private Date date;
	private int farmID;
	private int weight;

	public Purchase(Date date, int farmID, int weight) {
		this.date = date;
		this.farmID = farmID;
		this.weight = weight;
	}

	public Date getPurchaseDate() {
		return this.date;
	}

	public int getFarmID() {
		return farmID;
	}

	public int getPurchaseWeight() {
		return weight;
	}

}
