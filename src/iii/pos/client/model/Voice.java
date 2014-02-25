package iii.pos.client.model;

public class Voice {
	private int voice_id;
	private int quantity;
	private String name;
	private int status;
	private int flag;
	private String code_table;
	private int checked;
	private int type;

	public Voice() {
	}

	public Voice(int voice_id, int quantity, String name, String code_table,
			int status, int flag, int type) {
		super();
		this.quantity = quantity;
		this.name = name;
		this.code_table = code_table;
		this.status = status;
		this.flag = flag;
		this.type = type;
	}

	public void setCode_table(String code_table) {
		this.code_table = code_table;
	}

	public String getCode_table() {
		return code_table;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getStatus() {
		return status;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public int getFlag() {
		return flag;
	}

	public void setVoice_id(int voice_id) {
		this.voice_id = voice_id;
	}

	public int getVoice_id() {
		return voice_id;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getQuantity() {
		return quantity;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getChecked() {
		return checked;
	}

	public void setChecked(int checked) {
		this.checked = checked;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

}
