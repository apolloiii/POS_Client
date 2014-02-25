package iii.pos.client.model;

import java.util.ArrayList;

/*---------------------model invoice -------------------*/
public class Invoice {

	private int inv_id;
	private String inv_code;
	private float total;
	private float cost;
	private int vat;
	private int commision;
	private String inv_endtime;
	private String inv_starttime;
	private int user_id;
	private int status;
	private boolean check;
	private int inv_type;
	private String parent_inv;
	private ArrayList<String> lstCodeTables; //tao mot mang chua cac code_table cua invoice

	public Invoice() {
	}

	public Invoice(int inv_id, String inv_code, float total, float cost,
			int vat, int commision, String inv_endtime, String inv_starttime,
			int user_id, ArrayList<String> lstCodeTables) {
		super();
		this.inv_id = inv_id;
		this.inv_code = inv_code;
		this.total = total;
		this.cost = cost;
		this.vat = vat;
		this.commision = commision;
		this.inv_endtime = inv_endtime;
		this.inv_starttime = inv_starttime;
		this.user_id = user_id;
		this.lstCodeTables = lstCodeTables;
	}

	/*---------------getter, setter-------------------------------*/
	public int getInv_id() {
		return inv_id;
	}

	public void setInv_id(int inv_id) {
		this.inv_id = inv_id;
	}

	public String getInv_code() {
		return inv_code;
	}

	public void setInv_code(String inv_code) {
		this.inv_code = inv_code;
	}

	public float getTotal() {
		return total;
	}

	public void setTotal(float total) {
		this.total = total;
	}

	public float getCost() {
		return cost;
	}

	public void setCost(float cost) {
		this.cost = cost;
	}

	public int getVat() {
		return vat;
	}

	public void setVat(int vat) {
		this.vat = vat;
	}

	public int getCommision() {
		return commision;
	}

	public void setCommision(int commision) {
		this.commision = commision;
	}

	public String getInv_endtime() {
		return inv_endtime;
	}

	public void setInv_endtime(String inv_endtime) {
		this.inv_endtime = inv_endtime;
	}

	public String getInv_starttime() {
		return inv_starttime;
	}

	public void setInv_starttime(String inv_starttime) {
		this.inv_starttime = inv_starttime;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public boolean isCheck() {
		return check;
	}

	public void setCheck(boolean check) {
		this.check = check;
	}

	public int getInv_type() {
		return inv_type;
	}

	public void setInv_type(int inv_type) {
		this.inv_type = inv_type;
	}

	public String getParent_inv() {
		return parent_inv;
	}

	public void setParent_inv(String parent_inv) {
		this.parent_inv = parent_inv;
	}
	public void toggleChecked() {
		check = !check ;
	}

	public ArrayList<String> getLstCodeTables() {
		return lstCodeTables;
	}

	public void setLstCodeTables(ArrayList<String> lstCodeTables) {
		this.lstCodeTables = lstCodeTables;
	}

}
