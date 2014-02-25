package iii.pos.client.model;

public class Invoice_Spend {
	private int id;
	private int user_id;
	private String code;
	private String payFor;
	private String price;
	private String cost;
	private String status;
	private String nameCost;
	private String personPay;
	private String purposePay;
	private String spending;
	private String username;
	private boolean check;
	
	public Invoice_Spend() {
		// TODO Auto-generated constructor stub
	}
	public Invoice_Spend(int id, String code, String payFor, String price, String cost,
			String status, String nameCost, String personPay,
			String purposePay, String spending, boolean check) {
		super();
		this.code = code;
		this.payFor = payFor;
		this.price = price;
		this.cost = cost;
		this.status = status;
		this.nameCost = nameCost;
		this.personPay = personPay;
		this.purposePay = purposePay;
		this.id=id;
		this.spending = spending;
		this.check = check;
	}
	
	
	public String getSpending() {
		return spending;
	}
	public void setSpending(String spending) {
		this.spending = spending;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getPayFor() {
		return payFor;
	}
	public void setPayFor(String payFor) {
		this.payFor = payFor;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getCost() {
		return cost;
	}
	public void setCost(String cost) {
		this.cost = cost;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getNameCost() {
		return nameCost;
	}
	public void setNameCost(String nameCost) {
		this.nameCost = nameCost;
	}
	public String getPersonPay() {
		return personPay;
	}
	public void setPersonPay(String personPay) {
		this.personPay = personPay;
	}
	public String getPurposePay() {
		return purposePay;
	}
	public void setPurposePay(String purposePay) {
		this.purposePay = purposePay;
	}
	public boolean isCheck() {
		return check;
	}
	public void setCheck(boolean check) {
		this.check = check;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	

}
