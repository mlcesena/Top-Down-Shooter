import java.awt.Graphics;
import java.awt.Rectangle;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;


/**
 * The Bullet class is used to create new bullet objects, control their movement, collision, and other mechanics
 * 
 * @authors Michael Cesena and Matthew Arble
 */

public class Bullet extends Asset {

    private BufferedImage image;
    AssetController assetController;
    Bullet tempBullet;

    /**
	 * Overloaded Constructor to create a bullet from the player
	 * 
     * @param x               - x Coordinate for wall
     * @param y               - y Coordinate for wall
     * @param ID              - ID value for the wall
	 * @param assetController - assetController to control the movement of the bullet
	 */
	public Bullet(int x, int y, ID id, AssetController assetController) {
        // Call the Asset Super Constructor
        super(x, y, id); 

        // Set the assetController
		this.assetController = assetController;

        // Set image of bullet object to Bullet.png
        if (Player.getDirection() == 1) {
            try {
                image = ImageIO.read(getClass().getResource("/images/Bullet_Right.png"));
            }
            catch(IOException e) {
                e.printStackTrace();
                System.out.println("Bullet.java - Failed to set image of bullet to Bullet_Right.png");
            }    
            dX += 20;
        }
        else if (Player.getDirection() == -1) {
            try {
                image = ImageIO.read(getClass().getResource("/images/Bullet_Left.png"));
            }
            catch(IOException e) {
                e.printStackTrace();
                System.out.println("Bullet.java - Failed to set image of bullet to Bullet_Left.png");
            }
            dX -= 20;
        }
            
	}

    // Renders a bullet
    public void render(Graphics g) {
		g.drawImage(image, x, y, null);
    }

    public void update() {

        x += dX;
		y += dY;

        Collision();
    }

    /**
	 * hitBox method that is used for horizontal collision with the bullet
	 */
	public Rectangle hitBox() {

		double boxX = x + dX;
		double boxY = y;
		double boxW = 20 + dX / 2;
		double boxH = 20;

		return new Rectangle((int) boxX, (int) boxY, (int) boxW, (int) boxH);
	}

	/**
	 * Second hitBox method that is used for vertical collision
     * with the bullet
	 */
	public Rectangle hitBox2() {

		double boxX = x;
		double boxY = y + dY;
		double boxW = 20;
		double boxH = 20 + dY / 2;

		return new Rectangle((int) boxX, (int) boxY, (int) boxW, (int) boxH);

    }

    /**
     * Collision Method
     * If a bullet collides with a wall, remove the bullet
     * If a bullet collides with an enemy, remove the enemy.
     */
    private void Collision() {
        for (int i = 0; i < assetController.asset.size(); i++) {
            // Current asset we are analying in the list
            Asset tempAsset = assetController.asset.get(i);
            // For  horizontal hit box collision with a wall
            if (tempAsset.getID() == ID.Wall) {
				if (hitBox().intersects(tempAsset.hitBox()) || hitBox2().intersects(tempAsset.hitBox())) {
                    assetController.removeAsset(this);
                }
            }   

            // Dealing with collision with an enemy 
            if (tempAsset.getID() == ID.Enemy) {
                if (hitBox().intersects(tempAsset.hitBox()) || hitBox2().intersects(tempAsset.hitBox())) {
                    assetController.removeAsset(this);
                    assetController.removeAsset(tempAsset);
                    Player.increaseScore();
                }
            }
        }       
    }
}






