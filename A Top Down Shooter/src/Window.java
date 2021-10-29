import java.awt.*;
import javax.swing.*;

/**
 * The Window class implements Java AWT and Java Swing to create a window
 * 
 * @author Tyler Battershell
 */
public class Window {

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

		JFrame frame = new JFrame(title);
		frame.setPreferredSize(new Dimension(width, height));
		frame.setMinimumSize(new Dimension(width, height));
		frame.setMaximumSize(new Dimension(width, height));

		frame.add(game);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

	}

}
