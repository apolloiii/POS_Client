package iii.pos.client.model;

public class Voice_pos {
	private int voice_id;
	private String name;
	private String type;
	private int user_id;
	private int flag;
	private String date;
	public Voice_pos() {
		// TODO Auto-generated constructor stub
	}
	public Voice_pos(int voice_id, String name, String type, int user_id,
			int flag, String date) {
		super();
		this.voice_id = voice_id;
		this.name = name;
		this.type = type;
		this.user_id = user_id;
		this.flag = flag;
		this.date = date;
	}
	public int getVoice_id() {
		return voice_id;
	}
	public void setVoice_id(int voice_id) {
		this.voice_id = voice_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
}
