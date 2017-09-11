package steam;

public class SteamImageDimensions {
	private int width;
	private int height;
	
	public SteamImageDimensions() {
		super();
	}

	public SteamImageDimensions(int width, int height) {
		super();
		this.width = width;
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void setHeight(int height) {
		this.height = height;
	}
}
