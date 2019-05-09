

public class move {
	private int x, y;
	private boolean colour;
	private String comment;
	
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
	public void addComment(String comment) {
		this.comment = comment;
	}
	public String getComment() {
		return comment;
	}
	public void clearComment() {
		comment = "";
	}
}
