package gameRefined;

public class Enemy extends GameComponent {
	
	private boolean isMoving;
	private boolean isQueued;
	private boolean isDead;
	private int index;
	
	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public boolean isQueued() {
		return isQueued;
	}

	public void setQueued(boolean isQueued) {
		this.isQueued = isQueued;
	}

	public Enemy(int x, int y, int xVel, int yVel) {
		super(x, y, xVel, yVel);
		isMoving = false;
		isQueued = false;
		isDead = false;
	}
	
	public boolean isDead() {
		return isDead;
	}

	public void setDead(boolean isDead) {
		this.isDead = isDead;
	}

	public boolean isMoving() {
		return isMoving;
	}

	public void setMoving(boolean isMoving) {
		this.isMoving = isMoving;
	}

	public void update() {
		setX(getX() + getxVel());
		setY(getY() + getyVel());
	}
	
	public void reset() {
		setxVel(0);
		setyVel(0);
		setX(-125);
		setY(0);
		isMoving = false;
		isQueued = false;
		isDead = true;
	}
	
}
