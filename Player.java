package gameRefined;

public class Player extends GameComponent {
	
	private int kills;
	private int health;
	
	public Player(int x, int y, int xVel, int yVel) {
		super(x, y, xVel, yVel);
		kills = 0;
		health = 100;
	}
	
	public void addKill() {
		kills++;
	}
	
	public void update() {
		setX(getX() + getxVel());
		setY(getY() + getyVel());
	}
	
	public int getKills() {
		return kills;
	}

	public void setKills(int kills) {
		this.kills = kills;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

}
