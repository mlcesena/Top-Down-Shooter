import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.IOException;
import java.util.Timer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

/**
 * Player class is the class created to add the player into the game. The pixel
 * with the corresponding player color will be turned into a 32x48 movable
 * character.
 * 
 * @author Tyler Battershell / Michael Cesena / Ethan Hubbell
 */
public class Player extends Asset {
	//Initializing player character and updater
	private BufferedImage image;
	private static int invincibilityTime = 0;
	private static boolean canTakeDmg = true;
	private static int playerScore = 0;
	private static int playerAmmo = 20;
	private static int playerHealth = 200;
	private static int dir = 1;

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

		//Loads original sprite to be updated on movement.
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
		//Check if all enemies are gone if so load the next level.
		if(ImageLoader.enemyCount <= 0 & ImageLoader.level > 1) {
			ImageLoader.level = ImageLoader.level + 1;
			Game.imageLoader.loadLevel();
		}
		
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
			dir = -1;
			//Changes sprite orientation for directional movement
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
			dir = 1;
			//Changes sprite orientation for directional movement
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
		invincibilityTime++;

	}

	// private void isPlayerDead() {
	// 	if(Window.getPlayerHealth() <= 0) {
	// 		ImageLoader.level = 999;
	// 		Game.imageLoader.loadLevel();
	// 	}
	// }

	/**
	 * Collision system in the game. If an object's hitBox touches a Wall's hitBox,
	 * it will not allow it to move any further in that direction
	 */
	private void Collision() {

		for (int i = 0; i < assetController.asset.size(); i++) {
			Asset tempAsset = assetController.asset.get(i);

			//If colliding with wall reset payer to before wall (prevents travel through walls)
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
			} else if (tempAsset.getID() == ID.SpeedUp) {
				if (hitBox().intersects(tempAsset.hitBox()) || hitBox2().intersects(tempAsset.hitBox())) {

					assetController.setSprint(true);
					assetController.removeAsset(tempAsset);
					
					Timer t = new java.util.Timer();
					t.schedule(new java.util.TimerTask() {
						public void run() {
							assetController.setSprint(false);
							t.cancel();
						}
					}, 3000);

				}
			}
			
			//Areas for other collisions
		}
	}

	/**
	 * render method to render the player into the game and update its directional movement.
	 */
	public void render(Graphics g) {
		g.drawImage(image, x, y, null);
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

	public static int getScore() { // returns player score
		return playerScore;
	}

	public static void increaseScore() { // increases player score
		playerScore++;
		Window.updateScore();
	}

	public static int getAmmoCount() { // returns ammo count
		return playerAmmo;
	}

	public static void setAmmo() { // subtracts from ammo count
		playerAmmo--;
		Window.updateAmmo();
	}

	public static void reload() { // resets ammo count
		playerAmmo = 20;
		Window.updateAmmo();
	}

	public static int getHealth() { // returns player health
		return playerHealth;
	}

	public static void setHealth(int health) { // sets player health to parameter value
		playerHealth = health;
	}

	public static void subtractHealth() { // subtracts player health
		playerHealth -= 20;
		Window.updateHealthBar();
	}

	public static void reset() { // resets player variables
		playerHealth = 200;
		playerAmmo = 20;
		playerScore = 0;
	}

	public static int getInvincibilityTime() {
		return invincibilityTime;
	}

	public static void setInvincibilityTime(int time) {
		invincibilityTime = time;
	}

	public static boolean canTakeDmg() {
		return canTakeDmg;
	}
	public static void canTakeDmg(boolean state) {
		canTakeDmg = state;
	}

	public static int getDirection() {
		return dir;
	}

}
