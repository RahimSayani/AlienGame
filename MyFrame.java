package gameRefined;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class MyFrame extends JFrame {

	private MyPanel game;
	private ImageIcon spaceshipIcon;
	
	public MyFrame() {
		game = new MyPanel();
		spaceshipIcon = new ImageIcon("spaceship.png");
		this.setIconImage(spaceshipIcon.getImage());
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		this.add(game);
		this.pack();
		this.setVisible(true);
	}
}
