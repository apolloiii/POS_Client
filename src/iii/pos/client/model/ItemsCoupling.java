package iii.pos.client.model;

public class ItemsCoupling {

	private String name;
	private boolean check;
	
	public ItemsCoupling(String name, boolean check) {
		super();
		this.name = name;
		this.check = check;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean getCheck() {
		return check;
	}
	public void setCheck(boolean check) {
		this.check = check;
	}
	
	
}
