package iii.pos.client.model;

public class WinnerItem {

	int winneImage;
	String winneName;
	String wineCost;
	boolean wineChoose;
	
	public WinnerItem() {
	}
	public WinnerItem(int winneImage, String winneName, String wineCost,
			boolean wineChoose) {
		super();
		this.winneImage = winneImage;
		this.winneName = winneName;
		this.wineCost = wineCost;
		this.wineChoose = wineChoose;
	}

	public int getWinneImage() {
		return winneImage;
	}

	public void setWinneImage(int winneImage) {
		this.winneImage = winneImage;
	}


	public String getWinneName() {
		return winneName;
	}

	public void setWinneName(String winneName) {
		this.winneName = winneName;
	}

	public String getWineCost() {
		return wineCost;
	}

	public void setWineCost(String wineCost) {
		this.wineCost = wineCost;
	}

	public boolean isWineChoose() {
		return wineChoose;
	}

	public void setWineChoose(boolean wineChoose) {
		this.wineChoose = wineChoose;
	}
	
}
