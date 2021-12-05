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
 * @authors Tyler Battershell and Michael Cesena
 */
public class Window extends JFrame {
	// Labels used in HUD
	private static JLabel scoreLbl;
	private JLabel healthLbl;
	private static JLabel levelLbl;
	private static JLabel ammoLbl;

	// Labels used in game over screen
	private static JLabel gameOverLbl;
	private static JLabel endScoreLbl;

	// Frames used for game windows
	private static JFrame frame;
	private static JFrame fr;

	// HUD components and variables
	private static JComponent healthBar;
	private static JPanel hud;

	// Background Music
	private static Sound backgroundMusic = new Sound("BackgroundMusic.wav");

	/**
	 * Overloading constructor to always be called when creating a window.
	 * The first block creates the window.
	 * The second block adds the game, makes it impossible to resize, terminates the
	 * program when the window is closed, and displays the window in the middle of
	 * the screen.
	 * 
	 * @param width  - The width of the window to be created (in pixels)
	 * @param height - The height of the window to be created (in pixels)
	 * @param title  - The title of the window (will show up in top left)
	 * @param game   - The game is passed into the window to begin displaying it
	 */
	public Window(int width, int height, String title, Game game) {

		backgroundMusic.loop();

		Font f = new Font("Courier", Font.BOLD, 30); // Creates new font for HUD items
		JPanel healthPanel = new JPanel(new GridLayout(1, 2)); // Creates new JPanel with GridLayout to hold health
																// items
		hud = new JPanel(new GridLayout(1, 4)); // Instantiates hud JPanel with a GridLayout of 1x4

		// Instantiates all labels used in HUD
		scoreLbl = new JLabel("Score: " + Player.getScore(), SwingConstants.CENTER);
		healthLbl = new JLabel("Health: ", SwingConstants.CENTER);
		ammoLbl = new JLabel("Ammo: " + Player.getAmmoCount(), SwingConstants.CENTER);
		levelLbl = new JLabel("Level: " + ImageLoader.level, SwingConstants.CENTER);

		// Sets fonts of all HUD labels
		scoreLbl.setFont(f);
		healthLbl.setFont(f);
		ammoLbl.setFont(f);
		levelLbl.setFont(f);

		// Adds healthLbl to healhPanel, draws health bar rectangles, and adds health
		// bar to healthPanel
		healthPanel.add(healthLbl);
		healthBar = new JComponent() {
			protected void paintComponent(Graphics g) {
				g.setColor(Color.RED);
				g.fillRect(0, healthPanel.getHeight() - 55, 200, 40);
				g.setColor(Color.GREEN);
				g.fillRect(0, healthPanel.getHeight() - 55, Player.getHealth(), 40);
			};
		};
		healthPanel.add(healthBar);

		// Adds all labels and graphics to hud and sets dimensions
		hud.add(scoreLbl);
		hud.add(healthPanel);
		hud.add(ammoLbl);
		hud.add(levelLbl);
		hud.setPreferredSize(new Dimension(width, 75));

		frame = new JFrame(title); // Sets frame title

		// Sets frame dimensions
		frame.setPreferredSize(new Dimension(width, height));
		frame.setMinimumSize(new Dimension(width, height));
		frame.setMaximumSize(new Dimension(width, height));
		frame.setResizable(false);

		// Adds game and hud to the frame
		frame.add(game, BorderLayout.CENTER);
		frame.add(hud, BorderLayout.NORTH);

		// Sets frame properties
		frame.setCursor(new Cursor(1));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	/**
	 * Creates a new game over the current window. Used for the end screen.
	 * 
	 * @param width  - The width of the window to be created (in pixels)
	 * @param height - The height of the window to be created (in pixels)
	 * @param title  - The title of the window (will show up in top left)
	 */
	public Window(int width, int height, String title) {
		beforeEnd();

		// Game over sound
		backgroundMusic.reset();
		Sound roundLose = new Sound("RoundLose.wav");
		roundLose.start();

		// Fonts used for game over screen
		Font f = new Font("Courier", Font.BOLD, 75);
		Font f2 = new Font("Courier", Font.PLAIN, 40);

		fr = new JFrame(title);
		JPanel endPanel = new JPanel();
		endPanel.setLayout(new BoxLayout(endPanel, BoxLayout.PAGE_AXIS));

		// Instantiates end screen components
		JButton reset = new JButton("Play Again");
		reset.addActionListener(e -> resetGame());
		gameOverLbl = new JLabel("Game Over", SwingConstants.CENTER);
		endScoreLbl = new JLabel("Score: " + Player.getScore(), SwingConstants.CENTER);

		// Sets fonts for labels and buttons
		reset.setFont(new Font("Courier", Font.PLAIN, 25));
		gameOverLbl.setFont(f);
		endScoreLbl.setFont(f2);

		// Sets end screen positional alignments
		gameOverLbl.setAlignmentX(Component.CENTER_ALIGNMENT);
		endScoreLbl.setAlignmentX(Component.CENTER_ALIGNMENT);
		reset.setAlignmentX(Component.CENTER_ALIGNMENT);

		// Adds components to the endPanel and white space between them
		endPanel.add(Box.createRigidArea(new Dimension(0, 250)));
		endPanel.add(gameOverLbl);
		endPanel.add(Box.createRigidArea(new Dimension(0, 150)));
		endPanel.add(endScoreLbl);
		endPanel.add(Box.createRigidArea(new Dimension(0, 50)));
		endPanel.add(reset);

		fr.add(endPanel); // Adds endPanel to the frame

		// Sets frame dimensions
		fr.setPreferredSize(new Dimension(width, height));
		fr.setMinimumSize(new Dimension(width, height));
		fr.setMaximumSize(new Dimension(width, height));
		fr.setResizable(false);

		// Sets frame properties
		fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fr.setLocationRelativeTo(null);
		fr.setVisible(true);

		close(frame); // Closes original game frame
	}

	private void beforeEnd() { // Removes assets and calls newAsetList() from the AssetController
		for (int i = 0; i < Game.imageLoader.assetController.asset.size(); i++) {
			Asset tempAsset = Game.imageLoader.assetController.asset.get(i);
			Game.imageLoader.assetController.removeAsset(tempAsset);
		}
		Game.imageLoader.assetController.newAssetList();
	}

	public static void updateHealthBar() { // Repaints the healthbar
		healthBar.repaint();
	}

	public static void updateAmmo() { // Updates ammo label
		ammoLbl.setText("Ammo: " + Player.getAmmoCount());
	}

	public static void updateScore() { // Updates score label
		scoreLbl.setText("Score: " + Player.getScore());
	}

	public static void updateLevel() { // Updates level label
		levelLbl.setText("Level: " + ImageLoader.level);
	}

	public static void resetGame() { // Used to reset game variables upon game over and starts new game
		AssetController ac = new AssetController();
		ac.asset.clear();
		close(fr);
		Player.reset();
		ImageLoader.level = -1;
		Game.main(null);
		backgroundMusic.loop();
	}

	public static void requestReload() { // Changes the ammo count to "RELOAD!"
		ammoLbl.setText("RELOAD!");
	}

	public static void close(JFrame gameFrame) { // Deletes unused frames
		gameFrame.dispose();
	}
}
