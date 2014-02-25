package iii.pos.client.model;

public class CookItem {

	private int id;
	private int item_id;
	private int user_id;
	private int quantity;	
	private String cook_createtime;
	private String cook_updatetime;
	private int checked;
	private String notes;
	
	public CookItem() {
	}
	
	public CookItem(int item_id, int user_id, int quantity,
			String cook_createtime, String cook_updatetime, int checked,
			String notes) {
		super();
		
		this.item_id = item_id;
		this.user_id = user_id;
		this.quantity = quantity;
		this.cook_createtime = cook_createtime;
		this.cook_updatetime = cook_updatetime;
		this.checked = checked;
		this.notes = notes;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getItem_id() {
		return item_id;
	}
	public void setItem_id(int item_id) {
		this.item_id = item_id;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public String getCook_createtime() {
		return cook_createtime;
	}
	public void setCook_createtime(String cook_createtime) {
		this.cook_createtime = cook_createtime;
	}
	public String getCook_updatetime() {
		return cook_updatetime;
	}
	public void setCook_updatetime(String cook_updatetime) {
		this.cook_updatetime = cook_updatetime;
	}
	public int getChecked() {
		return checked;
	}
	public void setChecked(int checked) {
		this.checked = checked;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	
	
	
}
