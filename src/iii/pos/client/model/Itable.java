package iii.pos.client.model;

/*---------------------model Itable -------------------*/
public class Itable {

	private int itable_id;
	private String code_table;
	private String description_table;
	private String create_time;
	private String update_date;
	private int status;
	private int flag;
	private int pos_x;
	private int pos_y;
	private Double location_X;
	private Double location_Y;

	/*-----------constructor---------------------------------------*/
	public Itable() {

	}

	public Itable(int itable_id, String code_table, String description_table,
			String create_time, String update_date, int status, int flag) {
		super();
		this.itable_id = itable_id;
		this.code_table = code_table;
		this.description_table = description_table;
		this.create_time = create_time;
		this.update_date = update_date;
		this.status = status;
		this.flag = flag;
	}
	public Itable(int itable_id, String code_table,  int pos_x,  int pos_y) {
		this.itable_id = itable_id;
		this.code_table = code_table;
		this.pos_x = pos_x;
		this.pos_y = pos_y;
	}
	
	/*---------------getter, setter-------------------------------*/
	public int getItable_id() {
		return itable_id;
	}

	public void setItable_id(int itable_id) {
		this.itable_id = itable_id;
	}

	public String getCode_table() {
		return code_table;
	}

	public void setCode_table(String code_table) {
		this.code_table = code_table;
	}

	public String getDescription_table() {
		return description_table;
	}

	public void setDescription_table(String description_table) {
		this.description_table = description_table;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public String getUpdate_date() {
		return update_date;
	}

	public void setUpdate_date(String update_date) {
		this.update_date = update_date;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public int getPos_x() {
		return pos_x;
	}

	public void setPos_x(int pos_x) {
		this.pos_x = pos_x;
	}

	public int getPos_y() {
		return pos_y;
	}

	public void setPos_y(int pos_y) {
		this.pos_y = pos_y;
	}

	public void setLocation_X(Double location_X) {
		this.location_X = location_X;
	}

	public Double getLocation_X() {
		return location_X;
	}

	public void setLocation_Y(Double location_Y) {
		this.location_Y = location_Y;
	}

	public Double getLocation_Y() {
		return location_Y;
	}
}
