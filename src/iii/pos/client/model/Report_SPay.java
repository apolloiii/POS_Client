package iii.pos.client.model;

public class Report_SPay {
	private String date;
	private float totalRevenue;
	private float totalSpending;
	private float benefit;

	public Report_SPay() {

	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public float getTotalRevenue() {
		return totalRevenue;
	}

	public void setTotalRevenue(float totalRevenue) {
		this.totalRevenue = totalRevenue;
	}

	public float getTotalSpending() {
		return totalSpending;
	}

	public void setTotalSpending(float totalSpending) {
		this.totalSpending = totalSpending;
	}

	public float getBenefit() {
		return benefit;
	}

	public void setBenefit(float benefit) {
		this.benefit = benefit;
	}

}
