
public class AI {
	public int turn;
	public AI(int t) {
		turn = t;
	}
	public move getMove() {
		int x = (int)(Math.random() * 18) + 1;
		int y = (int)(Math.random() * 18) + 1;
		boolean colour;
		if(turn%2==0) {
			colour = true;
		}
		else {
			colour = false;
		}
		
		move AIMove = new move(x, y, colour);
		return AIMove;
	}
}
