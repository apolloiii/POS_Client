package iii.pos.client.model;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.Date;

/*---------------------model User -------------------*/
public class User implements Serializable {

	private static final long serialVersionUID = 1L;
	private int user_id;
	private String username;
	private String password;
	private int sex;
	private int age;
	private String email;
	private String address;
	private String create_time;
	private String update_time;
	private int flag;
	private String title;
	private String companyCode; // Mã nhà hàng

	public User(String username, String password, String companyCode) {
		super();
		this.username = username;
		this.password = password;
		this.companyCode = companyCode;
	}

	/*-----------constructor---------------------------------------*/
	public User() {
		user_id = 0;
		username = "";
		password = "";
		sex = 1;
		age = 0;
		email = "";
		address = "";
		create_time = DateFormat.getDateTimeInstance().format(new Date());
		update_time = DateFormat.getDateTimeInstance().format(new Date());
		flag = 0;
		title = "";
	}

	/*-----------constructor---------------------------------------*/
	public User(int user_id, String username, String password, int sex,
			int age, String email, String address, String create_time,
			String update_time, int flag, String title) {
		super();
		this.user_id = user_id;
		this.username = username;
		this.password = password;
		this.sex = sex;
		this.age = age;
		this.email = email;
		this.address = address;
		this.create_time = create_time;
		this.update_time = update_time;
		this.flag = flag;
		this.title = title;
	}

	/*---------------getter, setter-------------------------------*/
	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getCompanyCode() {
		return companyCode;
	}
}
