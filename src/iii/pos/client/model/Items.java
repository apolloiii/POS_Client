package iii.pos.client.model;


//---------------------model items ----------------//
public class Items {

	//----------------fields-----------------------//
	private int item_id;
	private String name;
	private String description;
	private int category_id;
	private String create_time;
	private String update_time;
	private int flag;
	private float inputAmoutFloat;
	
	//----------------extra fields-----------------//
	private float price;
	private int quantityItem;
	private String ImgName;
	
	/*-----------constructor---------------------------------------*/
	
	public Items() {
		// TODO Auto-generated constructor stub
	}
	
	public Items(int item_id, String name, String description, int category_id,
			String create_time, String update_time, int flag, float price,
			int quantityItem, String imgName, String inv_code) {
		super();
		this.item_id = item_id;
		this.name = name;
		this.description = description;
		this.category_id = category_id;
		this.create_time = create_time;
		this.update_time = update_time;
		this.flag = flag;
		this.price = price;
		this.quantityItem = quantityItem;
		ImgName = imgName;
	}
	/*---------------getter, setter-------------------------------*/
	
	public int getItem_id() {
		return item_id;
	}
	public void setItem_id(int item_id) {
		this.item_id = item_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getCategory_id() {
		return category_id;
	}
	public void setCategory_id(int category_id) {
		this.category_id = category_id;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public int getQuantityItem() {
		return quantityItem;
	}
	public void setQuantityItem(int quantityItem) {
		
		this.quantityItem = quantityItem;
	}
	public String getImgName() {
		return ImgName;
	}
	public void setImgName(String imgName) {
		ImgName = imgName;
	}


	public float getInputAmoutFloat() {
		return inputAmoutFloat;
	}

	public void setInputAmoutFloat(float inputAmoutFloat) {
		this.inputAmoutFloat = inputAmoutFloat;
	}
}
