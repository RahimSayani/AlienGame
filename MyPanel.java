package gameRefined;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.sound.sampled.AudioInputStream;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.xml.stream.events.EndDocument;
import javax.xml.stream.events.StartDocument;

public class MyPanel extends JPanel implements KeyListener{
	
	// PANEL DIMENSIONS
	private final int PANEL_WIDTH = 700;
	private final int PANEL_HEIGHT = 1000;
	
	// START BUTTON
	private JButton startButton;
	boolean playable = false;
	
	// DIFFICULTY BUTTON
	private JButton difficultyButton;
	private int difficulty = 0;
	
	// QUIT BUTTON
	private JButton quitButton;
	
	// LABELS
	private JLabel pauseLabel;
	private JLabel resumeLabel; 
	private JLabel endMessage;
	private JLabel ammo;
	private JLabel wave;
	private JLabel kills;
	private JLabel gameOver;
	
	// COMPONENTS
	private javax.swing.Timer timer;
	private Player player;
	private Bullet [] bullets;
	private Enemy [] enemies;

	// IMAGES
	private Image spaceship;
	private Image earth;
	private Image enemy;
	private Image ammunition;
	private Image skull;
	private Image playerSymbol;
	private Image booster;
	private Image explosion;
	private Image explosionFaded;
	private Image whiteStar;
	private Image blueStar;
	private Image redStar;
	private Image comet;
	
	// PLAYER VARIABLES
	private int playerSpeed = 10;
	private int playerWidth = 82;
	private int playerHeight = 101;
	private int playerHealth = 100;
	private boolean collision = true;

	// BULLET VARIABLES
	private Bullet prevBullet;
	private int currentBullet = 0;
	private int bulletFrequency = 20;
	private int numBullets = 32;
	private int bulletSpeed = 15;
	private int xOffset = 41;
	private int yOffset = 13;
	private int bulletWidth = 3;
	private int bulletLength = 20;
	private boolean firing = false;
	
	// ALIEN VARIABLES
	private int numAliens = 200;
	private int enemySpawnAreaMultiplier;
	private int enemySpeed;
	private int enemyWidth = 80;
	private int enemyHeight = 45;
	private int aliensDead = 0;
	
	// EARTH VARIABLES
	private int earthStart = 835;
	private int earthHealth = 100;
	
	// BOOST VARIABLES
	private boolean isBoostAvailable = true;
	private boolean isBoosting = false;
	private int boostMeter = 100;
	
	
	
	public MyPanel() {
		
		// BUTTON INSTANTIATION
		startButton = new JButton();
		startButton.setText("START");
		startButton.setFont(new Font("Calibri", Font.BOLD, 25));
		startButton.setFocusable(false);
		startButton.setBackground(Color.black);
		startButton.setForeground(Color.WHITE);
		startButton.setBounds((PANEL_WIDTH / 2) - 75, PANEL_HEIGHT - 700, 150, 50);
		startButton.setVisible(true);
		startButton.addActionListener(new ActionListener() {
		
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == startButton) {
					playable = true;
					startButton.setVisible(false);
					gameOver.setVisible(false);
					difficultyButton.setVisible(false);
					quitButton.setVisible(false);
					endMessage.setVisible(false);
					if (difficulty == 0) {
						enemySpeed = 7;
						enemySpawnAreaMultiplier = 7;
					}
					else if (difficulty == 1) {
						enemySpeed = 7;
						enemySpawnAreaMultiplier = 4;
					}
					else if(difficulty == 2) {
						enemySpeed = 7;
						enemySpawnAreaMultiplier = 2;
					}
					setEnemyPosition();
					earthHealth = 100;
					playerHealth = 100;
					player.setKills(0);
					aliensDead = 0;
					kills.setText(player.getKills() + "");
				}				
			}
		});
		
		difficultyButton = new JButton();
		difficultyButton.setText("NORMAL");
		difficultyButton.setFont(new Font("Calibri", Font.BOLD, 20));
		difficultyButton.setFocusable(false);
		difficultyButton.setBackground(Color.black);
		difficultyButton.setForeground(Color.WHITE);
		difficultyButton.setBounds((PANEL_WIDTH / 2) - 70, PANEL_HEIGHT - 630, 140, 35);
		difficultyButton.setVisible(true);
		difficultyButton.addActionListener(new ActionListener() {
		
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == difficultyButton) {
					if (difficulty == 0) {difficulty++; difficultyButton.setText("HARD");}
					else if (difficulty == 1) {difficulty++; difficultyButton.setText("IMPOSSIBLE");}
					else if (difficulty == 2) { difficulty = 0; difficultyButton.setText("NORMAL");}
				}
			}
		});
		
		quitButton = new JButton();
		quitButton.setText("QUIT");
		quitButton.setFont(new Font("Calibri", Font.BOLD, 20));
		quitButton.setFocusable(false);
		quitButton.setBackground(Color.black);
		quitButton.setForeground(Color.WHITE);
		quitButton.setBounds((PANEL_WIDTH / 2) - 40, PANEL_HEIGHT - 575, 80, 30);
		quitButton.setVisible(true);
		quitButton.addActionListener(new ActionListener() {
		
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == quitButton) {
					System.exit(0);
				}
			}
		});
		
		// LABEL INSTANTIATION
		pauseLabel = new JLabel();
		pauseLabel.setText(" 'p' to pause");
		pauseLabel.setBackground(Color.LIGHT_GRAY);
		pauseLabel.setOpaque(true);
		pauseLabel.setBounds(0, 0, 100, 30);
		pauseLabel.setVisible(false);
		
		resumeLabel = new JLabel();
		resumeLabel.setText(" 'r' to resume");
		resumeLabel.setBackground(Color.LIGHT_GRAY);
		resumeLabel.setOpaque(true);
		resumeLabel.setBounds(0, 30, 100, 30);
		resumeLabel.setVisible(false);
		
		ammo = new JLabel();
		ammo.setFont(new Font("Calibri", Font.BOLD, 17));
		ammo.setText(32 - currentBullet + "");
		ammo.setForeground(Color.WHITE);
		ammo.setBounds(670, 0, 100, 30);
		ammo.setVisible(true);
		
		wave = new JLabel();
		wave.setForeground(Color.RED);
		wave.setFont(new Font("Calibri", Font.BOLD, 23));
		wave.setBounds(290, 400, 200, 30);
		wave.setVisible(false);
		
		kills = new JLabel();
		kills.setForeground(Color.WHITE);
		kills.setFont(new Font("Calibri", Font.BOLD, 17));
		kills.setText(0 + "");
		kills.setBounds(670, 35, 100, 30);
		kills.setVisible(true);
		
		gameOver = new JLabel();
		gameOver.setForeground(Color.RED);
		gameOver.setFont(new Font("Calibri", Font.BOLD, 50));
		gameOver.setBounds(265, 200, 200, 60);
		gameOver.setVisible(true);
		
		endMessage = new JLabel();
		endMessage.setForeground(Color.white);
		endMessage.setFont(new Font("Calibri", Font.BOLD, 25));
		endMessage.setBounds(250, 260, 250, 30);
		endMessage.setVisible(false);
		
		// PANEL INSTANTIATION
		this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
		this.setBackground(Color.BLACK);
		this.add(pauseLabel);
		this.add(resumeLabel);
		this.add(ammo);
		this.add(wave);
		this.add(kills);
		this.add(gameOver);
		this.add(startButton);
		this.add(difficultyButton);
		this.add(quitButton);
		this.add(endMessage);
		this.setLayout(new BorderLayout());
		this.addKeyListener(this);
		this.setFocusable(true);
		
		// PLAYER - BULLET - ENEMY INSTANTIATION
		player = new Player((PANEL_WIDTH / 2) - (playerWidth / 2), (PANEL_HEIGHT - 300), 0, 0); // int x, y, xVel, yVel
		
		prevBullet = null;
		bullets = new Bullet[numBullets];
		for(int i = 0; i < bullets.length; i++) {
			bullets[i] = new Bullet(-125, 200, 0, 0, bulletSpeed); // int x, y, xVel, yVel, bulletVelocity
		}
		
		enemies = new Enemy[numAliens];
		for (int i = 0; i < enemies.length; i++) {
			enemies[i] = new Enemy(-125, 0, 0, 0); 
		}

		// IMAGE INSTANTIATION
		spaceship = new ImageIcon("spaceship.png").getImage();
		earth = new ImageIcon("earth.png").getImage();
		enemy = new ImageIcon("enemy.png").getImage();
		ammunition = new ImageIcon("ammo2.png").getImage(); 
		skull = new ImageIcon("skull2.png").getImage();
		whiteStar = new ImageIcon("whitestar.png").getImage();
		blueStar = new ImageIcon("blueStar3.png").getImage(); 
		comet = new ImageIcon("comet.png").getImage();
		playerSymbol = new ImageIcon("player3.png").getImage();
		booster = new ImageIcon("booster3.png").getImage();
		explosion = new ImageIcon("explosion2.png").getImage(); 
		explosionFaded = new ImageIcon("explosion2faded2.png").getImage(); 

		
		// TIMER 
		timer = new javax.swing.Timer(16, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (playable) {
					booster();
					bulletsFiring();
					collisionDetection();
					successCheck();
					failureCheck();
					repaint();
				}
			}
		});
		timer.start();
	}
	
	public void booster() {
		if (isBoosting && isBoostAvailable && boostMeter != 0) {
			boostMeter--;
			if (boostMeter == 0) {
				isBoostAvailable = false;
				System.out.println("boost depleted!");
			}
		}
		if (!isBoostAvailable) {
			boostMeter++;
			if (boostMeter == 100) {
				isBoostAvailable = true;
				System.out.println("boost ready!");
			}
		}
		if (!isBoosting && boostMeter < 100 && boostMeter > 0 && isBoostAvailable) {
			boostMeter++;
		}
		if (isBoosting && isBoostAvailable) {
			if ((player.getxVel() < 0 && player.getxVel() > -playerSpeed * 2) || (player.getxVel() > 0 && player.getxVel() < playerSpeed * 2)) {player.setxVel(player.getxVel() * 2);}
			if ((player.getyVel() < 0 && player.getyVel() > -playerSpeed * 2) || (player.getyVel() > 0 && player.getyVel() < playerSpeed * 2)) {player.setyVel(player.getyVel() * 2);}
		}
		if ((isBoosting && !isBoostAvailable) || (!isBoosting && boostMeter < 100 && boostMeter > 0)) {
			if (player.getxVel() != 0 && !(player.getxVel() == playerSpeed || player.getxVel() == -playerSpeed)) {player.setxVel(player.getxVel() / 2);}
			if (player.getyVel() != 0 && !(player.getyVel() == playerSpeed || player.getyVel() == -playerSpeed)) {player.setyVel(player.getyVel() /2 );}
		}
	}
	
	public void successCheck() {
		if (aliensDead == numAliens) {
			playable = false;
			for (int i = 0; i < numBullets; i++) {
				bullets[i].reset();
			}
			for (int i = 0; i < numAliens; i++) {
				enemies[i].reset();
			}
			gameOver.setText("SUCCESS");
			gameOver.setVisible(true);
			startButton.setText("RESTART");
			startButton.setVisible(true);
			difficultyButton.setVisible(true);
			quitButton.setVisible(true);
			endMessage.setText("you defended earth");
			endMessage.setVisible(true);
		}
	}
	
	public void failureCheck() {
		if (earthHealth <= 0 || playerHealth <= 0) {
			playable = false;
			for (int i = 0; i < numAliens; i++) {
				enemies[i].reset();
			}
			for (int i = 0; i < numBullets; i++) {
				bullets[i].reset();
			}
			gameOver.setText("FAILURE");
			gameOver.setVisible(true);
			startButton.setText("RESTART");
			startButton.setVisible(true);
			difficultyButton.setVisible(true);
			quitButton.setVisible(true);
			if (earthHealth == 0) {endMessage.setText(" earth was invaded");}
			else {endMessage.setText("          you died");}
			endMessage.setVisible(true);
		}
	}

	public void bulletsFiring() {
		
		if (firing) {
			if (currentBullet == 32) {currentBullet = 0;}
			if (prevBullet == null) {
				bullets[currentBullet].setFired(true);
				bullets[currentBullet].setX(player.getX() + xOffset);
				bullets[currentBullet].setY(player.getY() + yOffset);
				bullets[currentBullet].setFiredyVelocity(player.getyVel());
				prevBullet = bullets[currentBullet];
				currentBullet++;
			}
			else if ((prevBullet.isFired() && prevBullet.getY() + bulletFrequency < player.getY()) || (!prevBullet.isFired())) {
				bullets[currentBullet].setFired(true);
				bullets[currentBullet].setX(player.getX() + xOffset);
				bullets[currentBullet].setY(player.getY() + yOffset);
				bullets[currentBullet].setFiredyVelocity(player.getyVel());
				prevBullet = bullets[currentBullet];
				currentBullet++;
			}
			ammo.setText(currentBullet + "");
		}
	}
	
	public void setEnemyPosition() {
		
		int randomX;
		int randomY;
		boolean intersect;
		
		for (int j = 0; j < enemies.length; j++) {
			while (true) {
				intersect = false;
				randomX = (int) (Math.random()*((PANEL_WIDTH - enemyWidth) - 0 + 1) + 0);
				randomY = (int) (Math.random()*(PANEL_HEIGHT * enemySpawnAreaMultiplier - enemyHeight + 1) + enemyHeight);
				if (j == 0) {
					enemies[j].setX(randomX);
					enemies[j].setY(-randomY);
					break;
				}
				for (int i = j - 1; i >= 0; i--) {
					if (enemies[i].getX() >= 0) {
						int comparisonX = enemies[i].getX();
						int comparisonY = enemies[i].getY();
						boolean topLeft = randomX >= comparisonX && randomX <= comparisonX + enemyWidth && (-randomY) >= comparisonY && (-randomY) <= comparisonY + enemyHeight;
						boolean topRight = randomX + enemyWidth >= comparisonX && randomX + enemyWidth <= comparisonX + enemyWidth && (-randomY) >= comparisonY && (-randomY) <= comparisonY + enemyHeight;
						boolean bottomLeft = randomX >= comparisonX && randomX <= comparisonX + enemyWidth && (-randomY) + enemyHeight >= comparisonY && (-randomY) + enemyHeight <= comparisonY + enemyHeight;
						boolean bottomRight = randomX + enemyWidth >= comparisonX && randomX + enemyWidth <= comparisonX + enemyWidth && (-randomY) + enemyHeight >= comparisonY && (-randomY) + enemyHeight <= comparisonY + enemyHeight;
						if (topLeft || topRight || bottomLeft || bottomRight) {
							intersect = true;
						}
					}
				}
				if (!intersect) {
					enemies[j].setX(randomX);
					enemies[j].setY(-randomY);
					break;
				}
			}
			enemies[j].setQueued(true);
		}
	}

	public void collisionDetection() {
		
		// PLAYER - BORDER
		if (player.getX() > PANEL_WIDTH) {player.setX(-80);}
		if (player.getX() + playerWidth < 0) {player.setX(PANEL_WIDTH);}
		if (player.getY() < 0) {player.setyVel(0); player.setY(0);}
		if (player.getY() + playerHeight > PANEL_HEIGHT) {player.setyVel(0);}
		
		// PLAYER - EARTH
		if (player.getY() + playerHeight > earthStart) {player.setyVel(0); player.setY(earthStart - playerHeight);}
		
		// BULLET - BORDER
		for (int i = 0; i < 32; i++) {
			if (bullets[i].isFired() && bullets[i].getY() < 0) {
				bullets[i].reset();
			}
		}
		
		// EMEMY
		int playerX = player.getX();
		int playerY = player.getY();
		for (int j = 0; j < enemies.length; j++) {
			
			// earth
			if (enemies[j].getY() >= PANEL_HEIGHT) {enemies[j].reset(); earthHealth -= 5; aliensDead++;}
			
			int enemyX = enemies[j].getX();
			int enemyY = enemies[j].getY();
			
			// bullet
			for (int i = 0; i < 32; i++) {
				if (bullets[i].isFired() && bullets[i].getY() + 50 <= player.getY()) {
					if (!(enemies[j].getY() + enemyHeight < 0)) {
						int bulletX = bullets[i].getX();
						int bulletY = bullets[i].getY();
						enemyX = enemies[j].getX();
						enemyY = enemies[j].getY();
						if (bulletY < enemyY + enemyHeight && bulletY > enemyY && bulletX < enemyX + enemyWidth && bulletX > enemyX) {
							enemies[j].setJustKilled(true);
							enemies[j].setExplosionX(enemies[j].getX());
							enemies[j].setExplosionY(enemies[j].getY());
							aliensDead++;
							bullets[i].reset();
							player.addKill();
							kills.setText(player.getKills() + "");
						}
					}
				}
			}
			
			// player
			boolean leftX = enemyX >= playerX && enemyX <= playerX + playerWidth;
			boolean rightX = enemyX + enemyWidth >= playerX && enemyX + enemyWidth <= playerX + playerWidth;
			boolean topY = enemyY >= playerY && enemyY <= playerY + playerHeight;
			boolean bottomY = enemyY + enemyHeight >= playerY && enemyY + enemyHeight <= playerY + playerHeight;
						
			if (((topY && leftX) || (topY && rightX) || (bottomY && leftX) || (bottomY && rightX)) && collision) {
				enemies[j].reset();
				aliensDead++;
				playerHealth -= 10;
				player.addKill();
			}
		}
	}

	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D graphics2d = (Graphics2D) g;
		
		//graphics2d.drawImage(whiteStar, 100, 100, null);
		
		// BULLETS
		graphics2d.setPaint(Color.GREEN);
		for (int i = 0; i < 32; i++) {
			if (bullets[i].isFired()) {
				if (bullets[i].getFiredyVelocity() < 0) {
					bullets[i].setY(bullets[i].getY() - bullets[i].getBulletVelocity() + bullets[i].getFiredyVelocity());
				}
				else {
					bullets[i].setY(bullets[i].getY() - bullets[i].getBulletVelocity());
				}
			}
			graphics2d.fillRect(bullets[i].getX(), bullets[i].getY(), bulletWidth, bulletLength);
		}
		
		// ALIENS
		for (int i = 0; i < enemies.length; i++) {
			
			if (enemies[i].isQueued()) {
				enemies[i].setyVel(enemySpeed);
				enemies[i].setY(enemies[i].getY() + enemies[i].getyVel());
			}
			
			if (enemies[i].isJustKilled()) {
				enemies[i].reset();
				
				if (enemies[i].getKillTimer() > 15) {
					//graphics2d.drawImage(explosion, enemies[i].getExplosionX(), enemies[i].getExplosionY(), null);
				}
				else {
					//graphics2d.drawImage(explosionFaded, enemies[i].getExplosionX(), enemies[i].getExplosionY(), null);
				}
				enemies[i].setKillTimer(enemies[i].getKillTimer() - 1);
				if (enemies[i].getKillTimer() == 0) {
					enemies[i].setJustKilled(false);
				}
			}
			
			//graphics2d.fillRect(enemies[i].getX(), enemies[i].getY(), enemyWidth, enemyHeight);
			graphics2d.drawImage(enemy, enemies[i].getX(), enemies[i].getY(), null);	
		}
		
		// PLAYER
		//graphics2d.fillRect(player.getX() + player.getxVel(), player.getY() + player.getyVel(), playerWidth, playerHeight);
		graphics2d.drawImage(spaceship, player.getX() + player.getxVel(), player.getY() + player.getyVel(), null);
		player.update();
		
		// EARTH
		//graphics2d.fillRect(0, earthStart, earthWidth, PANEL_HEIGHT - 835);
		graphics2d.drawImage(earth, 0, 700, null);
		
		// SKULL
		graphics2d.drawImage(skull, 635, 35, null);
		
		// AMMO
		graphics2d.drawImage(ammunition, 635, 0, null);
		
		// PLAYER HEALTH
		graphics2d.setPaint(Color.DARK_GRAY);
		graphics2d.drawImage(playerSymbol, 2, 2, null);
		graphics2d.fillRect(45, 10, 300, 10);
		graphics2d.setPaint(Color.red);
		graphics2d.fillRect(45, 10, playerHealth * 3, 10);
		
		// EARTH HEALTH
		graphics2d.setPaint(Color.DARK_GRAY);
		graphics2d.fillRect(50, PANEL_HEIGHT - 20, 602, 17);
		graphics2d.setPaint(Color.GREEN);
		graphics2d.fillRect(50, PANEL_HEIGHT - 20, earthHealth * 6, 15);
		
		// BOOST METER
		graphics2d.setPaint(Color.DARK_GRAY);
		graphics2d.drawImage(booster, 2, 40, null);
		graphics2d.fillRect(45, 40, 300, 10);
		graphics2d.setPaint(Color.CYAN);
		graphics2d.fillRect(45, 40, boostMeter * 3, 10);
		
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if (playable) {
		switch (e.getKeyChar()) {
			case 'w': if (player.getyVel() == playerSpeed) {player.setyVel(-playerSpeed);} else {player.setyVel(-playerSpeed); player.setxVel(0);}
				break;
			case 's': if (player.getyVel() == -playerSpeed) {player.setyVel(playerSpeed);} else {player.setyVel(playerSpeed); player.setxVel(0);}
				break;
			case 'a': if (player.getxVel() == playerSpeed) {player.setxVel(-playerSpeed);} else {player.setxVel(-playerSpeed); player.setyVel(0);}
				break;
			case 'd': if (player.getxVel() == -playerSpeed) {player.setxVel(playerSpeed);} else {player.setxVel(playerSpeed); player.setyVel(0);}
				break;
			case 'p': if (timer.isRunning()) {timer.stop(); pauseLabel.setVisible(true); resumeLabel.setVisible(true);}
				break;
			case 'r': if (!timer.isRunning()) {timer.start(); pauseLabel.setVisible(false); resumeLabel.setVisible(false);}
		}
		
		if (e.getKeyCode() == 32) {
			if(firing) {firing = false;}
			else {firing = true;}
		}
		
		if (e.getKeyCode() == 16) {
			if (!isBoosting) {isBoosting = true;}
			else {
				isBoosting = false;
			}
		}
		repaint();
		}
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
