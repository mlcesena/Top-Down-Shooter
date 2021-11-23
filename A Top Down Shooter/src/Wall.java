import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

/**
 * Wall class is the class created to add walls into the game. Each pixel with
 * the corresponding wall color will be turned into a 32x32 "wall"
 * 
 * @author Tyler Battershell
 */
public class Wall extends Asset {
	//Initializing wall and updater
	private BufferedImage image;
	private int count = 0;
	

	/**
	 * Overloading constructor to create an object of the Wall class
	 * 
	 * @param x  - x position of the wall
	 * @param y  - y position of the wall
	 * @param id - ID value of the wall (ID.Wall)
	 */
	public Wall(int x, int y, ID id) {
		super(x, y, id);
		
		if(count == 0) {
			try {
				image = ImageIO.read(getClass().getResource("/images/Test_Wall_Sprite.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			count = 1;
		}

	}

	/**
	 * update method to update the walls. There is no movement so there is no code.
	 */
	public void update() {
	}
	
	/**
	 * render method to render the walls into the game.
	 */
	public void render(Graphics g) {
		g.drawImage(image, x, y, null);
		
		//Trying to make a drop shadow but Enemies apear under it
		g.setColor(Color.DARK_GRAY);
		g.fillRect(x, y + 32, 32, 16);
		
	}
	
	/**
	 * hitBox method to return a rectangle with the wall's hit box (Same size as the wall)
	 */
	public Rectangle hitBox() {
		return new Rectangle(x, y, 32, 32);
	}

}
