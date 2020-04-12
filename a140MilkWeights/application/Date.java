package application;

public class Date implements Comparable<Date> {

	private String year;
	private String day;
	private int month;

	public Date(String year, int month, String day) {
		this.year = year;
		this.month = month;
		this.day = day;

	}

	public int getNumYear() {
		return Integer.parseInt(year);
	}

	public int getNumMonth() {
		return month;
	}

	public int getNumDay() {
		return Integer.parseInt(day);
	}

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
