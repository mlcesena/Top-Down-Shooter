import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.IOException;
import java.util.Timer;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

/**
 * Player class is the class created to add the player into the game.
 * The pixel the corresponding player color will be turned into a 32x48 movable
 * character.
 * 
 * @author Tyler Battershell / Michael Cesena / Ethan Hubbell
 */
public class Player extends Asset {

	private BufferedImage image;
	private static int invincibilityTime = 0;
	private static boolean canTakeDmg = true;
	private static int playerScore = 0;
	private static int playerAmmo = 20;
	private static int playerHealth = 200;
	private static int dir = 1;
	AssetController assetController;

	/**
	 * Overloading constructor to create an object of the Player class.
	 * Sets an original PNG to be drawn upon loading.
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
			System.out.println("Player.java - Failed to set Player PNG");
		}
	}

	/**
	 * update() method to update the enemy x and y positions.
	 * Based on assetController's booleans, which are updated in the KeyInput class.
	 * PNG is updated based on the direction the enemy is moving.
	 * Loads a new level if there are no more enemies.
	 * Calls the Collision() method to allow collision.
	 * Uses a boolean and an integer value to allow 1 second of invincibility after
	 * being attacked.
	 */
	public void update() {
		if(ImageLoader.enemyCount <= 0 & ImageLoader.level >= 1) {
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
			try {
				image = ImageIO.read(getClass().getResource("/images/Player_Sprite_Left.png"));
			} catch (IOException e) {
				System.out.println("Player.java - Failed to update Player PNG");
			}
		} else if (!assetController.isRight())
			dX = 0;

		if (assetController.isRight()) {
			dX = 5;
			dir = 1;
			try {
				image = ImageIO.read(getClass().getResource("/images/Player_Sprite.png"));
			} catch (IOException e) {
				System.out.println("Player.java - Failed to update Player PNG");
			}
		} else if (!assetController.isLeft())
			dX = 0;

		if (assetController.isSprint()) {
			dX *= 1.5;
			dY *= 1.5;
		}

		Collision();
		invincibilityTime++;
		if (getInvincibilityTime() >= 60) {
			canTakeDmg(true);
		}
	}

	/**
	 * Collision() method for enemy collision.
	 * Does not allow the player to walk through walls.
	 * Increases player speed for 3 seconds when the player touches a speed up
	 * powerup. Also plays a SpeedUp sound.
	 * Adds 40 health when the player touches a MedKit. Also plays a MedKit sound.
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
			} else if (tempAsset.getID() == ID.SpeedUp) {
				if (hitBox().intersects(tempAsset.hitBox()) || hitBox2().intersects(tempAsset.hitBox())) {

					assetController.setSprint(true);
					assetController.removeAsset(tempAsset);
					Sound speedUp = new Sound("SpeedUp.wav");
					speedUp.start();

					Timer t = new java.util.Timer();
					t.schedule(new java.util.TimerTask() {
						public void run() {
							assetController.setSprint(false);
							t.cancel();
						}
					}, 3000);

				}
			} else if (tempAsset.getID() == ID.MedKit) {
				if (hitBox().intersects(tempAsset.hitBox()) || hitBox2().intersects(tempAsset.hitBox())) {
					if (playerHealth <= 160) {
						addHealth(40);
						assetController.removeAsset(tempAsset);
						Sound medKit = new Sound("MedKit.wav");
						medKit.start();
					}
				}
			}
		}
	}

	/**
	 * render() method to render the player into the game.
	 */
	public void render(Graphics g) {
		g.drawImage(image, x, y, null);
	}

	/**
	 * hitBox() method to return a rectangle with the enemy's hit box, used for
	 * horizontal collision.
	 */
	public Rectangle hitBox() {

		double boxX = x + dX;
		double boxY = y;
		double boxW = 32 + dX / 2;
		double boxH = 48;

		return new Rectangle((int) boxX, (int) boxY, (int) boxW, (int) boxH);
	}

	/**
	 * hitBox2() method to return a rectangle with the enemy's hit box, used for
	 * vertical collision.
	 */
	public Rectangle hitBox2() {

		double boxX = x;
		double boxY = y + dY;
		double boxW = 32;
		double boxH = 48 + dY / 2;

		return new Rectangle((int) boxX, (int) boxY, (int) boxW, (int) boxH);
	}

	/**
	 * getScore(), increaseScore(), getAmmoCount(), setAmmo(), reload(),
	 * getHealth(), setHealth(), addHealth(), subtractHealth(),
	 * getInvincibilityTime(), setInvincibilityTime(), canTakeDmg(), canTakeDmg(),
	 * and getDirection() methods are used to access player variables.
	 */
	public static int getScore() { // Returns player score
		return playerScore;
	}

	public static void increaseScore() { // Increases/Updates player score
		playerScore++;
		Window.updateScore();
	}

	public static int getAmmoCount() { // Returns ammo count
		return playerAmmo;
	}

	public static void setAmmo() { // Subtracts/Updates ammo count
		playerAmmo--;
		Window.updateAmmo();
	}

	public static void reload() { // Resets ammo count
		playerAmmo = 10;
		Window.updateAmmo();
	}

	public static int getHealth() { // Returns player health
		return playerHealth;
	}

	public static void setHealth(int health) { // Sets/Updates player health to parameter value
		playerHealth = health;
		Window.updateHealthBar();
	}

	public static void addHealth(int health) { // Adds/Updates player health to parameter value
		playerHealth += health;
		Window.updateHealthBar();
	}

	public static void subtractHealth() { // Subtracts/Updates player health
		playerHealth -= 40;
		Window.updateHealthBar();
	}

	public static int getInvincibilityTime() { // Returns invincibility time
		return invincibilityTime;
	}

	public static void setInvincibilityTime(int time) { // Sets invincibility time to parameter value
		invincibilityTime = time;
	}

	public static boolean canTakeDmg() { // Returns true/false if player can/cannot take damage
		return canTakeDmg;
	}

	public static void canTakeDmg(boolean state) { // Sets canTakeDmg to parameter value
		canTakeDmg = state;
	}

	public static int getDirection() { // Returns player direction
		return dir;
	}

	/**
	 * reset() method resets the player variables
	 */
	public static void reset() {
		playerHealth = 200;
		playerAmmo = 10;
		playerScore = 0;
		dir = 1;
	}
}
