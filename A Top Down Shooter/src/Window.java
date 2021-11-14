import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

/**
 * The Window class implements Java AWT and Java Swing to create a window
 * 
 * @author Tyler Battershell
 */
public class Window {
	private JLabel scoreLbl;
	private JLabel healthLbl;
	private JLabel levelLbl;
	protected static int playerScore;
	protected static int level;
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

		JFrame frame = new JFrame(title);
		frame.setPreferredSize(new Dimension(width, height));
		frame.setMinimumSize(new Dimension(width, height));
		frame.setMaximumSize(new Dimension(width, height));

		JPanel hud = new JPanel(new GridLayout(1,3));
		JPanel healthPanel = new JPanel(new GridLayout(1,2));

		healthPanel.add(healthLbl);
		

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

	private void paintComponent(Graphics g) {
		// draw the rectangle here
		g.drawRect(SwingConstants.CENTER, SwingConstants.CENTER,100, 25);
	 }
}
