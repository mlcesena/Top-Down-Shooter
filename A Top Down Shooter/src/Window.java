import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.SwingConstants;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;

/**
 * The Window class implements Java AWT and Java Swing to create a window
 * 
 * @author Tyler Battershell
 */
public class Window extends JFrame {
	private JLabel scoreLbl;
	private JLabel healthLbl;
	private JLabel levelLbl;
	private static JLabel gameOverLbl;
	private static JLabel ammoLbl;
	private static JLabel endScoreLbl;
	private static JFrame frame;
	private static JFrame fr;
	protected static int playerScore;
	protected static int level = 1;
	protected static int playerAmmo = 20;
	private static int playerHealth = 200;
	private static JComponent healthBar;
	// private static JPanel endPanel;
	private static JPanel hud;
	

	/**
	 * This overloading constructor should always be called when creating a window
	 * The first block creates the window.
	 * The second block adds the game, makes it
	 * impossible to resize, terminates the program when the window is closed, and
	 * displays the window in the middle of the screen.
	 * 
	 * @param width  - The width of the window to be created (in pixels)
	 * @param height - The height of the window to be created (in pixels)
	 * @param title  - The title of the window (will show up in top left)
	 * @param game   - The game is passed into the window to begin displaying it
	 */
	public Window(int width, int height, String title, Game game) {
		Font f = new Font("Courier", Font.BOLD, 30);
		JPanel healthPanel = new JPanel(new GridLayout(1, 2));
		hud = new JPanel(new GridLayout(1, 4));

		scoreLbl = new JLabel("Score: " + playerScore, SwingConstants.CENTER);
		healthLbl = new JLabel("Health: ", SwingConstants.CENTER);
		ammoLbl = new JLabel("Ammo: " + playerAmmo, SwingConstants.CENTER);
		levelLbl = new JLabel("Level: " + level, SwingConstants.CENTER);

		scoreLbl.setFont(f);
		healthLbl.setFont(f);
		ammoLbl.setFont(f);
		levelLbl.setFont(f);

		frame = new JFrame(title);
		frame.setPreferredSize(new Dimension(width, height));
		frame.setMinimumSize(new Dimension(width, height));
		frame.setMaximumSize(new Dimension(width, height));

		healthPanel.add(healthLbl);
		healthBar = new JComponent() {
			protected void paintComponent(Graphics g) {
				g.setColor(Color.RED);
				g.fillRect(0, healthPanel.getHeight() - 55, 200, 40);
				g.setColor(Color.GREEN);
				g.fillRect(0, healthPanel.getHeight() - 55, playerHealth, 40);
			};
		};
		healthPanel.add(healthBar);

		hud.add(scoreLbl);
		hud.add(healthPanel);
		hud.add(ammoLbl);
		hud.add(levelLbl);
		hud.setPreferredSize(new Dimension(width, 75));

		frame.add(game, BorderLayout.CENTER);
		frame.setCursor(new Cursor(1));
		frame.add(game);
		frame.add(hud, BorderLayout.NORTH);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	public Window(int width, int height, String title) {
		Font f = new Font("Courier", Font.BOLD, 75);
		Font f2 = new Font("Courier", Font.PLAIN, 40);
		fr = new JFrame(title);
		JButton reset = new JButton("Play Again");
		JPanel endPanel = new JPanel();
		endPanel.setLayout(new BoxLayout(endPanel, BoxLayout.PAGE_AXIS));
		reset.addActionListener(e -> resetGame());
		reset.setFont(new Font("Courier", Font.PLAIN, 25));
		gameOverLbl = new JLabel("Game Over", SwingConstants.CENTER);
		endScoreLbl = new JLabel("Score: " + playerScore, SwingConstants.CENTER);
		gameOverLbl.setFont(f);
		endScoreLbl.setFont(f2);

		gameOverLbl.setAlignmentX(Component.CENTER_ALIGNMENT);
		endScoreLbl.setAlignmentX(Component.CENTER_ALIGNMENT);
		reset.setAlignmentX(Component.CENTER_ALIGNMENT);

		endPanel.add(Box.createRigidArea(new Dimension(0, 250)));
		endPanel.add(gameOverLbl);
		endPanel.add(Box.createRigidArea(new Dimension(0, 150)));
		endPanel.add(endScoreLbl);
		endPanel.add(Box.createRigidArea(new Dimension(0, 50)));
		endPanel.add(reset);
		
		fr.add(endPanel);
		fr.setPreferredSize(new Dimension(width, height));
		fr.setMinimumSize(new Dimension(width, height));
		fr.setMaximumSize(new Dimension(width, height));
		fr.setResizable(false);
		fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fr.setLocationRelativeTo(null);
		fr.setVisible(true);
		close();
	}

	public static int getPlayerHealth() {
		return playerHealth;
	}

	public static void subtractPlayerHealth() {
		playerHealth -= 10;
		healthBar.repaint();
	}

	public static int getPlayerAmmo() {
		return playerAmmo;
	}

	public static void setPlayerAmmo() {
		playerAmmo--;
		ammoLbl.setText("Ammo: " + playerAmmo);
	}

	public static void reload() {
		playerAmmo = 20;
		ammoLbl.setText("Ammo: " + playerAmmo);
	}

	public static void resetGame() {
		fr.dispose();
		playerHealth = 200;
		playerAmmo = 20;
		playerScore = 0;
		level = 1;
		ImageLoader.level = 0;
		Window.playerScore = 0;
		Game.imageLoader.loadLevel();
		Game.main(null);
	}

	public static void close() {
		frame.dispose();
	}

  	public static void setPlayerHealth(int newHealth) {
	   playerHealth = newHealth;
   	}

}
