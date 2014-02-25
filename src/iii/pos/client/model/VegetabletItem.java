package iii.pos.client.model;

public class VegetabletItem {

	int vegetableImage;
	String vegetableName;
	int vegetableQuality;
	boolean vegetableChoose;
	
	public VegetabletItem() {
	}
	
	public VegetabletItem(int vegetableImage, String vegetableName,
			 boolean vegetableChoose) {
		super();
		this.vegetableImage = vegetableImage;
		this.vegetableName = vegetableName;
		this.vegetableQuality = vegetableQuality;
		this.vegetableChoose = vegetableChoose;
	}

	public int getVegetableImage() {
		return vegetableImage;
	}

	public void setVegetableImage(int vegetableImage) {
		this.vegetableImage = vegetableImage;
	}

	public String getVegetableName() {
		return vegetableName;
	}

	public void setVegetableName(String vegetableName) {
		this.vegetableName = vegetableName;
	}


	public int getVegetableQuality() {
		return vegetableQuality;
	}

	public void setVegetableQuality(int vegetableQuality) {
		this.vegetableQuality = vegetableQuality;
	}
	
	public boolean isVegetableChoose() {
		return vegetableChoose;
	}

	public void setVegetableChoose(boolean vegetableChoose) {
		this.vegetableChoose = vegetableChoose;
	}
}
