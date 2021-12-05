import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

/**
 * Enemy class is used to create new enemy objects.
 * This class controls their movement and collision.
 * 
 * @authors Tyler Battershell, Michael Cesena, Ethan Hubbell
 */
public class Enemy extends Asset {

	AssetController assetController;
	private int diffX, diffY;
	private double distance;
	private BufferedImage image;

	/**
	 * Overloading constructor to create an object of the Enemy class.
	 * Sets an original PNG to be drawn upon loading.
	 * 
	 * @param x               - x position of the enemy
	 * @param y               - y position of the enemy
	 * @param id              - ID value of the enemy (ID.Enemy)
	 * @param assetController - assetController to allow the enemy to be updated
	 *                        and rendered
	 */
	public Enemy(int x, int y, ID id, AssetController assetController) {
		super(x, y, id);
		this.assetController = assetController;
		try {
			image = ImageIO.read(getClass().getResource("/images/Enemy_Sprite.png"));
		} catch (IOException e) {
			System.out.println("Enemy.java - Failed to set Enemy PNG");
		}

	}

	/**
	 * update() method to update the enemy x and y positions.
	 * Calculates the difference in x and y values between this enemy and the
	 * player. Calculates the total difference between the two.
	 * Enemy only moves when they are within 640 pixels of the player.
	 * PNG is updated based on the direction the enemy is moving.
	 * Calls the Collision() method to allow collision.
	 */
	public void update() {
		for (int i = 0; i < assetController.asset.size(); i++) {
			if (assetController.asset.get(i).getID() == ID.Player) {
				diffX = x - assetController.asset.get(i).getX();
				diffY = y - assetController.asset.get(i).getY();
				distance = Math.sqrt(Math.pow(diffX, 2) + Math.pow(diffY, 2));
			}
		}
		if (distance <= 640 && distance != 0) {
			try {
				if (diffX <= 0) {
					dX = 1;
					image = ImageIO.read(getClass().getResource("/images/Enemy_Sprite.png"));
				} else if (diffX >= 0) {
					dX = -1;
					image = ImageIO.read(getClass().getResource("/images/Enemy_Sprite_Left.png"));
				}
			} catch (IOException e) {
				System.out.println("Enemy.java - Failed to set Enemy PNG");
			}

			if (diffY <= 0) {
				dY = 1;
			} else if (diffY >= 0) {
				dY = -1;
			}
		} else {
			dX = 0;
			dY = 0;
		}

		x += dX;
		y += dY;

		Collision();
	}

	/**
	 * Collision() method for enemy collision.
	 * Does not allow the enemy to walk through walls.
	 * Does not allow the enemy to walk through other enemies.
	 * Hurts the player if an enemy contacts a player and the player does not have
	 * the invincibility timer active.
	 * Plays a playerDamaged sound effect when the player is touched.
	 */
	private void Collision() {
		for (int i = 0; i < assetController.asset.size(); i++) {
			if (assetController.asset.size() <= i) {
				break;
			}
			Asset tempAsset = assetController.asset.get(i);
			if (tempAsset.getID() == ID.Wall) {
				if (hitBox().intersects(tempAsset.hitBox())) {

					if (dX > 0) {
						dX = 0;
						x = tempAsset.getX() - 33;
					} else if (dX < 0) {
						dX = 0;
						x = tempAsset.getX() + 33;
					}

				}
				if (hitBox2().intersects(tempAsset.hitBox())) {

					if (dY > 0) {
						dY = 0;
						y = tempAsset.getY() - 49;
					} else if (dY < 0) {
						dY = 0;
						y = tempAsset.getY() + 33;
					}
				}
			}
			if (tempAsset.getID() == ID.Enemy && !tempAsset.equals(this)) {
				if (hitBox().intersects(tempAsset.hitBox())) {

					if (dX > 0) {
						dX = 0;
						x = tempAsset.getX() - 33;
					} else if (dX < 0) {
						dX = 0;
						x = tempAsset.getX() + 33;
					}

				}
				if (hitBox2().intersects(tempAsset.hitBox())) {

					if (dY > 0) {
						dY = 0;
						y = tempAsset.getY() - 49;
					} else if (dY < 0) {
						dY = 0;
						y = tempAsset.getY() + 33;
					}
				}
			}
			if (tempAsset.getID() == ID.Player) {
				// if (hitBox().intersects(tempAsset.hitBox()) ||
				// hitBox2().intersects(tempAsset.hitBox())) {
				// Window.subtractPlayerHealth();
				// // Potential Knockback mechanic?? (Could be tough, allows enemies to "hit"
				// the player through walls)
				// if (diffX < 0)
				// tempAsset.dX = 10;
				// else if (diffX > 0)
				// tempAsset.dX = -10;
				// if (diffY < 0)
				// tempAsset.dY = 10;
				// else if (diffY > 0)
				// tempAsset.dY = -10;
				// }
				if (hitBox().intersects(tempAsset.hitBox()) || hitBox2().intersects(tempAsset.hitBox())) {
					if (Player.canTakeDmg()) {
						Player.canTakeDmg(false);
						Player.subtractHealth();
						Player.setInvincibilityTime(0);
						Sound playerDamaged = new Sound("PlayerDamaged.wav");
						playerDamaged.start();
					}
				}
			}
		}
	}

	/**
     * render() method to render the enemy into the game.
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
}
