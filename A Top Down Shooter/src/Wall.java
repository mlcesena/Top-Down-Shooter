import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 * Wall class is the class created to add walls into the game. Each pixel with
 * the corresponding wall color will be turned into a 32x32 "wall"
 * 
 * @author Tyler Battershell
 */
public class Wall extends Asset {

	/**
	 * Overloading constructor to create an object of the Wall class
	 * 
	 * @param x  - x position of the wall
	 * @param y  - y position of the wall
	 * @param id - ID value of the wall (ID.Wall)
	 */
	public Wall(int x, int y, ID id) {
		super(x, y, id);
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
		g.setColor(Color.lightGray);
		g.fillRect(x, y, 32, 32);
	}
	
	/**
	 * hitBox method to return a rectangle with the wall's hit box (Same size as the wall)
	 */
	public Rectangle hitBox() {
		return new Rectangle(x, y, 32, 32);
	}

}