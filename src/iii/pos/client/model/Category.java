package iii.pos.client.model;

/*---------------------model Category -------------------*/
public class Category {

	/*-------------fields ------------------------------------------*/
	private int category_id;
	private String name;
	private String description;
	private String create_time;
	private String update_time;
	private int flag;

	private int parent_id;
	// -----------extra fields-----------------------------------------*/
	private String imgName;

	/*-----------constructor---------------------------------------*/
	public Category() {
		// TODO Auto-generated constructor stub
	}

	public Category(int category_id, String name, String description,
			String create_time, String update_time, int flag, String imgName,
			int media_id, int parent_id) {
		super();
		this.category_id = category_id;
		this.name = name;
		this.description = description;
		this.create_time = create_time;
		this.update_time = update_time;
		this.flag = flag;
		this.imgName = imgName;
		this.parent_id = parent_id;
	}

	/*---------------getter, setter-------------------------------*/
	public int getCategory_id() {
		return category_id;
	}

	public void setCategory_id(int ctegory_id) {
		this.category_id = ctegory_id;
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

	public String getImgname() {
		return imgName;
	}

	public void setImgname(String imgname) {
		imgName = imgname;
	}

	public int getParent_id() {
		return parent_id;
	}

	public void setParent_id(int parent_id) {
		this.parent_id = parent_id;
	}

	public String getImgName() {
		return imgName;
	}

	public void setImgName(String imgName) {
		this.imgName = imgName;
	}

}
