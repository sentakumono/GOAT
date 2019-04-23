

public class move {
	private int x, y;
	private boolean colour;
	
	public move(int indexX, int indexY, boolean c) {
		this.x = indexX;
		this.y = indexY;
		this.colour = c;
	}
	
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public boolean colour() {
		return colour;
	}
}
