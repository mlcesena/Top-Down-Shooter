import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

/**
 * Player class is the class created to add the player into the game. The pixel
 * with the corresponding player color will be turned into a 32x48 movable
 * character.
 * 
 * @author Tyler Battershell
 */
public class Player extends Asset {
	//Initializing player character and updater
	private BufferedImage image;

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
	
		try {
			image = ImageIO.read(getClass().getResource("/images/Player_Sprite.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * update method to update the player's position. Changes based on
	 * assetController's booleans, which are updated in the KeyInput class.
	 */
	public void update() {
		isPlayerDead();
		
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

		if (assetController.isLeft()) {
			dX = -5;
			try {
				image = ImageIO.read(getClass().getResource("/images/Player_Sprite_Left.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else if (!assetController.isRight())
			dX = 0;

		if (assetController.isRight()) {
			dX = 5;
			try {
				image = ImageIO.read(getClass().getResource("/images/Player_Sprite.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else if (!assetController.isLeft())
			dX = 0;

		if (assetController.isSprint()) {
			dX *= 2;
			dY *= 2;
		}

		Collision();

	}

	private void isPlayerDead() {
		if(Window.getPlayerHealth() <= 0) {
			ImageLoader.level = 999;
			Game.imageLoader.loadLevel();
		}
	}

	/**
	 * Collision system in the game. If an object's hitBox touches a Wall's hitBox,
	 * it will not allow it to move any further in that direction
	 */
	private void Collision() {
		for (int i = 0; i < assetController.asset.size(); i++) {
			Asset tempAsset = assetController.asset.get(i);
			if (tempAsset.getID() == ID.Wall) {
				if (hitBox().intersects(tempAsset.hitBox())) {

					if (dX > 0) {
						dX = 0;
						x = tempAsset.getX() - 32;
					} else if (dX < 0) {
						dX = 0;
						x = tempAsset.getX() + 32;
					}

				}
				if (hitBox2().intersects(tempAsset.hitBox())) {

					if (dY > 0) {
						dY = 0;
						y = tempAsset.getY() - 48;
					} else if (dY < 0) {
						dY = 0;
						y = tempAsset.getY() + 32;
					}

				}
			//Collision with speed powerup
			} else if (tempAsset.getID() == ID.Power) {
				if (hitBox().intersects(tempAsset.hitBox())) {

					if (dX > 0) {
						dX = 12;
					} else if (dX < 0) {
						dX = -12;
					}

				}
				if (hitBox2().intersects(tempAsset.hitBox())) {

					if (dY > 0) {
						dY = 12;
					} else if (dY < 0) {
						dY = -12;
					}

				}
			}
			
			
			//Areas for other collisions







		}
	}

	/**
	 * render method to render the player into the game.
	 */
	public void render(Graphics g) {

		// Graphics2D g2d = (Graphics2D) g;
		// g2d.setColor(Color.red);
		// g2d.fill(hitBox());

		// g2d.setColor(Color.blue);
		// g2d.fill(hitBox2());
		
		g.drawImage(image, x, y, null);
		//g.setColor(Color.cyan);
		//g.fillRect(x, y, 32, 48);
	}

	/**
	 * hitBox method to return a rectangle with the player's hit box, used for
	 * horizontal collision
	 */
	public Rectangle hitBox() {

		double boxX = x + dX;
		double boxY = y;
		double boxW = 32 + dX / 2;
		double boxH = 48;

		return new Rectangle((int) boxX, (int) boxY, (int) boxW, (int) boxH);
	}

	/**
	 * hitBox method to return a rectangle with the player's hit box, used for
	 * vertical collision
	 */
	public Rectangle hitBox2() {

		double boxX = x;
		double boxY = y + dY;
		double boxW = 32;
		double boxH = 48 + dY / 2;

		return new Rectangle((int) boxX, (int) boxY, (int) boxW, (int) boxH);
	}

}
