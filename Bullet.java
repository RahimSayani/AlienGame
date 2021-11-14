package gameRefined;

public class Bullet extends GameComponent{

	private int bulletVelocity;
	private boolean isFired;
	private int firedyVelocity;
	private int firedxVelocity;
	private boolean turnShot;
	
	public Bullet(int x, int y, int xVel, int yVel, int bulletVelocity) {
		super(x, y, xVel, yVel);
		this.bulletVelocity = bulletVelocity;
		isFired = false;
		turnShot = false;
	}

	public boolean isTurnShot() {
		return turnShot;
	}

	public void setTurnShot(boolean turnShot) {
		this.turnShot = turnShot;
	}

	public void reset() {
		setX(-125);
		setY(200);
		setxVel(0);
		setyVel(0);
		isFired = false;
		firedyVelocity = 0;
		turnShot = false;
	}
	
	public int getFiredxVelocity() {
		return firedxVelocity;
	}

	public void setFiredxVelocity(int firedxVeloctiy) {
		this.firedxVelocity = firedxVeloctiy;
	}

	public void setBulletVelocity(int bulletVelocity) {
		this.bulletVelocity = bulletVelocity;
	}

	public int getFiredyVelocity() {
		return firedyVelocity;
	}
	
	public void setFiredyVelocity(int firedyVelocity) {
		this.firedyVelocity = firedyVelocity;
	}
	
	public int getBulletVelocity() {
		return bulletVelocity;
	}

	public boolean isFired() {
		return isFired;
	}

	public void setFired(boolean isFired) {
		this.isFired = isFired;
	}
	
}
