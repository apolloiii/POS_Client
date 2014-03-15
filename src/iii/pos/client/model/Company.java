package iii.pos.client.model;

public class Company {
	private String companyname;
	private String address;
	private String phone;
	private String email;
	private String fax;
	private String website;
	private String logo;
	private String companycode;
	private double lat;
	private double lon;
	private float radius;

	public Company() {
		// TODO Auto-generated constructor stub
	}

	public Company(String companyname, String address, String phone,
			String email, String fax, String website, String logo,
			String companycode) {
		super();
		this.companyname = companyname;
		this.address = address;
		this.phone = phone;
		this.email = email;
		this.fax = fax;
		this.website = website;
		this.logo = logo;
		this.companycode = companycode;
	}

	public String getCompanycode() {
		return companycode;
	}

	public void setCompanycode(String companycode) {
		this.companycode = companycode;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getCompanyname() {
		return companyname;
	}

	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLon() {
		return lon;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}

	public float getRadius() {
		return radius;
	}

	public void setRadius(float radius) {
		this.radius = radius;
	}

}
