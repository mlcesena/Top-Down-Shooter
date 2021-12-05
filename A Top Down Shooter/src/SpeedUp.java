import java.awt.Graphics;
import java.awt.Rectangle;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * SpeedUp class to create speed boost powerups to increase the player's speed
 * 
 * @author Ethan Hubbell / Tyler Battershell
 */

public class SpeedUp extends Asset {

	private BufferedImage image;

	/**
	 * Overloading constructor to create an object of the Speed Up class.
	 * Sets the image to the SpeedUp PNG.
	 * 
	 * @param x  - x position of the SpeedUp
	 * @param y  - y position of the SpeedUp
	 * @param id - ID value of the SpeedUp (ID.SpeedUp)
	 */
	public SpeedUp(int x, int y, ID id) {
		super(x, y, id);
		try {
			image = ImageIO.read(getClass().getResource("/images/SpeedUp.png"));
		} catch (IOException e) {
			System.out.println("SpeedUp.java - Failed to set SpeedUp PNG");
		}
	}

	/**
	 * update() method updates MedKit. No movement = no code.
	 */
	public void update() {
	}

	/**
	 * render method to render the SpeedUp into the game.
	 */
	public void render(Graphics g) {
		g.drawImage(image, x, y, null);
	}

	/**
	 * hitBox() method to return a rectangle with the SpeedUp's hit box, used for
	 * collision.
	 */
	public Rectangle hitBox() {
		return new Rectangle(x, y, 64, 64);
	}
}
