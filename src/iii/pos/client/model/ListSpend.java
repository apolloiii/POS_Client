package iii.pos.client.model;

public class ListSpend {
	private int id;
	private String codeList;
	private String nameCost;
	private String price;
	private String notes;
	private String typeCost;
	private String groupCost;
	private String status;
	private boolean check;

	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setCheck(boolean check) {
		this.check = check;
	}

	public boolean isCheck() {
		return check;
	}

	public ListSpend() {
		// TODO Auto-generated constructor stub
	}

	public ListSpend(int id, String codeList, String nameCost, String price,
			String notes, String typeCost, String groupCost) {
		super();
		this.codeList = codeList;
		this.nameCost = nameCost;
		this.price = price;
		this.notes = notes;
		this.typeCost = typeCost;
		this.groupCost = groupCost;
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCodeList() {
		return codeList;
	}

	public void setCodeList(String codeList) {
		this.codeList = codeList;
	}

	public String getNameCost() {
		return nameCost;
	}

	public void setNameCost(String nameCost) {
		this.nameCost = nameCost;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getTypeCost() {
		return typeCost;
	}

	public void setTypeCost(String typeCost) {
		this.typeCost = typeCost;
	}

	public String getGroupCost() {
		return groupCost;
	}

	public void setGroupCost(String groupCost) {
		this.groupCost = groupCost;
	}

}
