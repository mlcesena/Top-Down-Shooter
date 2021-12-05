import java.awt.Graphics;
import java.awt.Rectangle;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * MedKit class to allow player to pick up med kits to heal themselves for 40
 * health.
 * 
 * @author Tyler Battershell
 */

public class MedKit extends Asset {

	private BufferedImage image;

	/**
	 * Overloading constructor to create an object of the MedKit class.
	 * Sets the image to the MedKit PNG.
	 * 
	 * @param x  - x position of the MedKit
	 * @param y  - y position of the MedKit
	 * @param id - ID value of the MedKit (ID.MedKit)
	 */
	public MedKit(int x, int y, ID id) {
		super(x, y, id);
		try {
			image = ImageIO.read(getClass().getResource("/images/MedKit.png"));
		} catch (IOException e) {
			System.out.println("MedKit.java - Failed to set MedKit PNG");
		}
	}

	/**
	 * update() method updates MedKit. No movement = no code.
	 */
	public void update() {
	}

	/**
	 * render method to render the MedKit into the game.
	 */
	public void render(Graphics g) {
		g.drawImage(image, x, y, null);
	}

	/**
	 * hitBox() method to return a rectangle with the MedKit's hit box, used for
	 * collision.
	 */
	public Rectangle hitBox() {
		return new Rectangle(x, y, 64, 48);
	}
}