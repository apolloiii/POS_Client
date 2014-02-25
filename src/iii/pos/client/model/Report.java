package iii.pos.client.model;

public class Report {
	private String Datetime;
	private int totalReport;
	private float totalValue;
	private float average;

	public Report(String datetime, int totalReport, float totalValue,
			float average) {
		super();
		this.Datetime = datetime;
		this.totalReport = totalReport;
		this.totalValue = totalValue;
		this.average = average;
	}

	public Report() {

	}

	public String getDatetime() {
		return Datetime;
	}

	public void setDatetime(String datetime) {
		Datetime = datetime;
	}

	public int getTotalReport() {
		return totalReport;
	}

	public void setTotalReport(int totalReport) {
		this.totalReport = totalReport;
	}

	public float getTotalValue() {
		return totalValue;
	}

	public void setTotalValue(float totalValue) {
		this.totalValue = totalValue;
	}

	public float getAverage() {
		return average;
	}

	public void setAverage(float average) {
		this.average = average;
	}

}
