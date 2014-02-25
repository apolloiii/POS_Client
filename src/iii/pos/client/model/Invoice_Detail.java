package iii.pos.client.model;

/*---------------------model invoice_detail-------------------*/
public class Invoice_Detail {
	/*---------------fields-----------------------------------*/
	private int id;
	private int item_id;
	private String inv_code;
	private Integer quantity;
	private int flag;

	/*--------------extra fields--------------------------------*/
	private String name;
	private int category_id;
	private String description;
	private float price;
	private String imgName;
	private String start_date;
	private String end_date;
	private String comment = "";
	private float inputAmounFloat;
	private int checked = 0;
	private boolean tranferCheck;

	private String lstOrder = "";

	/*-----------constructor---------------------------------------*/
	public Invoice_Detail() {
		// TODO Auto-generated constructor stub
	}

	public Invoice_Detail(int id, int item_id, String inv_code, int quantity,
			int flag, String name, int category_id, String description,
			float price, String imgName, String comment) {
		super();
		this.id = id;
		this.item_id = item_id;
		this.inv_code = inv_code;
		this.quantity = quantity;
		this.flag = flag;
		this.name = name;
		this.category_id = category_id;
		this.description = description;
		this.price = price;
		this.imgName = imgName;
		this.comment = comment;
	}

	/*---------------getter, setter-------------------------------*/
	public int getId() {
		return id;
	}

	public void setTranferCheck(boolean tranferCheck) {
		this.tranferCheck = tranferCheck;
	}

	public boolean isTranferCheck() {
		return tranferCheck;
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

	public String getInv_code() {
		return inv_code;
	}

	public void setInv_code(String inv_code) {
		this.inv_code = inv_code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public int getCategory_id() {
		return category_id;
	}

	public void setCategory_id(int category_id) {
		this.category_id = category_id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public String getImgName() {
		return imgName;
	}

	public void setImgName(String imgName) {
		this.imgName = imgName;
	}

	public String getStart_date() {
		return start_date;
	}

	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}

	public String getEnd_date() {
		return end_date;
	}

	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public float getInputAmounFloat() {
		return inputAmounFloat;
	}

	public void setInputAmounFloat(float inputAmounFloat) {
		this.inputAmounFloat = inputAmounFloat;
	}

	public int getChecked() {
		return checked;
	}

	public void setChecked(int checked) {
		this.checked = checked;
	}

	public String getLstOrder() {
		if (lstOrder.equals(""))
			lstOrder = String.valueOf(quantity) + ",";
		return lstOrder;
	}

	public void setLstOrder(String lstOrder) {
		this.lstOrder = lstOrder;
	}

}
