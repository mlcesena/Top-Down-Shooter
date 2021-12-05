import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

/**
 * Wall class is the class created to add walls into the game. Each pixel with
 * the corresponding wall color will be turned into a 32x32 "wall."
 * 
 * @author Tyler Battershell / Ethan Hubbell
 */
public class Wall extends Asset {

	private static BufferedImage image;
	public static int count = 0;

	/**
	 * Overloading constructor to create an object of the Wall class.
	 * The PNG is randomly chosen between 4 variants.
	 * 
	 * @param x  - x position of the wall
	 * @param y  - y position of the wall
	 * @param id - ID value of the wall (ID.Wall)
	 */
	public Wall(int x, int y, ID id, int level) {
		super(x, y, id);

		if (count == 0) {
			try {
				Random randGen = new Random();
				int mapChooser = randGen.nextInt(4);
				switch (mapChooser) {
					case 0:
						image = ImageIO.read(getClass().getResource("/images/Wall_Sprite.png"));
						break;
					case 1:
						image = ImageIO.read(getClass().getResource("/images/Wall_Sprite_Green.png"));
						break;
					case 2:
						image = ImageIO.read(getClass().getResource("/images/Wall_Sprite_Blue.png"));
						break;
					case 3:
						image = ImageIO.read(getClass().getResource("/images/Wall_Sprite_Red.png"));
						break;
				}
			} catch (IOException e) {
				System.out.println("Wall.java - Failed to set Wall PNG");
			}
			count = 1;
		}

	}

	/**
	 * update() method updates MedKit. No movement = no code.
	 */

	public void update() {
	}

	/**
	 * render() method to render the walls into the game.
	 * Adds a drop shadow under the wall to add depth.
	 */
	public void render(Graphics g) {
		g.drawImage(image, x, y, null);
		g.setColor(Color.DARK_GRAY);
		g.fillRect(x, y + 32, 32, 16);
	}

	/**
	 * hitBox() method to return a rectangle with the wall's hit box, used for
	 * collision.
	 */
	public Rectangle hitBox() {
		return new Rectangle(x, y, 32, 32);
	}
}
