import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

/**
 * The Enemy class is used to create new enemy objects and control their movement, collision, and other mechanics
 * 
 * @authors Tyler Battershell, Michael Cesena, Ethan Hubbell
 */
public class Enemy extends Asset {
    
    AssetController assetController;
    private int diffX, diffY;
    private double distance;
	private BufferedImage image;

	/**
	 * Overloading constructor to create an object of the Player class
	 * 
	 * @param x               - x position of the wall
	 * @param y               - y position of the wall
	 * @param id              - ID value of the wall (ID.Wall)
	 * @param assetController - adds the assetController to the player to control
	 *                        movement
	 */
    public Enemy(int x, int y, ID id, AssetController assetController) {
        super(x, y, id); // calls Asset super constructor
        this.assetController = assetController; // sets assetController

		// attempts to set image of enemy object
		try {
			image = ImageIO.read(getClass().getResource("/images/Enemy_Sprite.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
    }

    public void update() {
		for(int i = 0; i < assetController.asset.size(); i++) {
			if(assetController.asset.get(i).getID() == ID.Player) {
				diffX = x - assetController.asset.get(i).getX(); // calculates the difference between the enemy's x and the player's x
                diffY = y - assetController.asset.get(i).getY(); // calculates the difference between the enemy's y and the player's y
                distance = Math.sqrt(Math.pow(diffX, 2) + Math.pow(diffY, 2)); // calculates total distance between enemy and player
			}
        }
        // Enemy only moves within 640 pixels of the player
		// Sets directional movement
        if (distance <= 640 && distance != 0) {
			if (diffX <= 0) {
				dX = 1; // sets x velocity to move right
				// attempts to change enemy image to face right
				try {
					image = ImageIO.read(getClass().getResource("/images/Enemy_Sprite.png"));
				} catch (IOException e) {
					e.printStackTrace();
				}
            }
            else if (diffX >= 0){
				dX = -1; // sets x velocity to move left
				// attempts to change enemy image to face left
				try {
					image = ImageIO.read(getClass().getResource("/images/Enemy_Sprite_Left.png"));
				} catch (IOException e) {
					e.printStackTrace();
				}
            }
			
            if (diffY <= 0) {
				dY = 1; // sets y velocity to move downwards
            }
            else if (diffY >= 0){
				dY = -1; // sets y velocity to move backwards
            }
        } else {
			dX = 0; // sets x veloctiy to 0
            dY = 0;	// sets y veloctiy to 0
        }

		x += dX; // applies x velocity to positional x
		y += dY; // applies y velocity to positional y
		
        Collision(); // calls method to check for collision
    }

    /**
	 * Collision system in the game. If an object's hitBox touches a Wall's hitBox,
	 * it will not allow it to move any further in that direction
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
			// Enemy to enemy collision (If wanted? Glitchy-ish)
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
				// if (hitBox().intersects(tempAsset.hitBox()) || hitBox2().intersects(tempAsset.hitBox())) {
				// 		Window.subtractPlayerHealth();
				// 		// Potential Knockback mechanic?? (Could be tough, allows enemies to "hit" the player through walls)
				// 		if (diffX < 0)
				// 			tempAsset.dX = 10;
				// 		else if (diffX > 0)
				// 			tempAsset.dX = -10;
				// 		if (diffY < 0)
				// 			tempAsset.dY = 10;
				// 		else if (diffY > 0)
				// 			tempAsset.dY = -10;
				// }
				if (hitBox().intersects(tempAsset.hitBox()) || hitBox2().intersects(tempAsset.hitBox())) {
					if(Player.canTakeDmg()) {
						Player.subtractHealth();
						Player.canTakeDmg(false);
					}
					if(Player.getInvincibilityTime() >= 60) {
						Player.canTakeDmg(true);
						Player.setInvincibilityTime(0);
					}
				}

			}
		}
    }

    public void render(Graphics g) {
		g.drawImage(image, x, y, null);
    }

    /**
	 * hitBox method to return a rectangle with the enemy's hit box, used for
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
	 * hitBox method to return a rectangle with the enemy's hit box, used for
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

