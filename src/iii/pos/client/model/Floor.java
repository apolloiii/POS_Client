package iii.pos.client.model;

public class Floor {
	private int id;
	private int id_sv;
	private String code;
	private String name;
	private int status;
	public Floor() {
	}

	public Floor(int id, int id_sv, String code, String name, int status) {
		super();
		this.id = id;
		this.code = code;
		this.name = name;
		this.status= status;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public int getId_sv() {
		return id_sv;
	}

	public void setIDSV(int id_sv) {
		this.id_sv = id_sv;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
