import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 * Player class is the class created to add the player into the game. The pixel with
 * the corresponding player color will be turned into a 32x48 movable character.
 * 
 * @author Tyler Battershell
 */
public class Player extends Asset {

	AssetController assetController;

	/**
	 * Overloading constructor to create an object of the Player class
	 * 
	 * @param x               - x position of the wall
	 * @param y               - y position of the wall
	 * @param id              - ID value of the wall (ID.Wall)
	 * @param assetController - adds the assetController to the player to control
	 *                        movement
	 */
	public Player(int x, int y, ID id, AssetController assetController) {
		super(x, y, id);
		this.assetController = assetController;
	}

	/**
	 * update method to update the player's position. Changes based on
	 * assetController's booleans, which are updated in the KeyInput class.
	 */
	public void update() {
		x += dX;
		y += dY;

		if (assetController.isUp())
			dY = -5;
		else if (!assetController.isDown())
			dY = 0;

		if (assetController.isDown())
			dY = 5;
		else if (!assetController.isUp())
			dY = 0;

		if (assetController.isLeft())
			dX = -5;
		else if (!assetController.isRight())
			dX = 0;

		if (assetController.isRight())
			dX = 5;
		else if (!assetController.isLeft())
			dX = 0;

		if (assetController.isSprint()) {
			dX *= 2;
			dY *= 2;
		}

	}

	/**
	 * render method to render the player into the game.
	 */
	public void render(Graphics g) {
		g.setColor(Color.cyan);
		g.fillRect(x, y, 32, 48);
	}

	/**
	 * hitBox method to return a rectangle with the player's hit box
	 */
	public Rectangle hitBox() {
		return new Rectangle(x, y, 32, 48);
	}

}
