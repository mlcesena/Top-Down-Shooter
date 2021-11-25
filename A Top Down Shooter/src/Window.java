import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JComponent;
import javax.swing.SwingConstants;
import java.awt.Graphics;
import java.awt.Color;

/**
 * The Window class implements Java AWT and Java Swing to create a window
 * 
 * @author Tyler Battershell
 */
public class Window extends JFrame {
	private JLabel scoreLbl;
	private JLabel healthLbl;
	private JLabel levelLbl;
	private static JFrame frame;
	protected static int playerScore;
	protected static int level = 0;
	private static int playerHealth = 250;
	public static JComponent healthBar;
	
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
		Font f = new Font("Helvetica", Font.BOLD, 30);
		scoreLbl = new JLabel("Score: " + playerScore, SwingConstants.CENTER);
		healthLbl = new JLabel("Health: ", SwingConstants.CENTER);
		levelLbl = new JLabel("Level: " + level, SwingConstants.CENTER);

		scoreLbl.setFont(f);
		healthLbl.setFont(f);
		levelLbl.setFont(f);

		frame = new JFrame(title);
		frame.setPreferredSize(new Dimension(width, height));
		frame.setMinimumSize(new Dimension(width, height));
		frame.setMaximumSize(new Dimension(width, height));

		JPanel hud = new JPanel(new GridLayout(1,3));
		JPanel healthPanel = new JPanel(new GridLayout(1,2));

		healthPanel.add(healthLbl);
		
		healthBar = new JComponent() {
			protected void paintComponent(Graphics g) {
				
				g.setColor(Color.RED);
				g.fillRect(0, healthPanel.getHeight() - 55, 250, 40);
				g.setColor(Color.GREEN);
				g.fillRect(0, healthPanel.getHeight() - 55, playerHealth, 40);
			};
		};
		healthPanel.add(healthBar);
		
		hud.add(scoreLbl);
		hud.add(healthPanel);
		hud.add(levelLbl);
		hud.setPreferredSize(new Dimension(width, 75));
		
		frame.add(game);
		frame.add(hud, BorderLayout.NORTH);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	public static int getPlayerHealth() {
		return playerHealth;
	}

	public static void subtractPlayerHealth() {
		playerHealth -= 10;
		healthBar.repaint();
	}

  	public static void setPlayerHealth(int newHealth) {
	   playerHealth = newHealth;
   	}

}
