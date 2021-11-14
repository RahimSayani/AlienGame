package gameRefined;

public class GameComponent {
	private int x;
	private int y;
	private int xVel;
	private int yVel;
	
	public GameComponent(int x, int y, int xVel, int yVel) {
		this.x = x;
		this.y = y;
		this.xVel = xVel;
		this.yVel = yVel;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getxVel() {
		return xVel;
	}

	public void setxVel(int xVel) {
		this.xVel = xVel;
	}

	public int getyVel() {
		return yVel;
	}

	public void setyVel(int yVel) {
		this.yVel = yVel;
	}
	
	
	
}
