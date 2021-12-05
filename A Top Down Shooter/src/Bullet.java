import java.awt.Graphics;
import java.awt.Rectangle;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Bullet class is used to create new bullet objects.
 * This class controls their movement and collision.
 * 
 * @authors Michael Cesena and Matthew Arble
 */
public class Bullet extends Asset {

    private BufferedImage image;
    AssetController assetController;

    /**
     * Overloading constructor to create a bullet from the player.
     * Sets the PNG to be drawn based on the direction faced by the player.
     * Plays a gunshot sound when the object is created.
     * 
     * @param x               - x coordinate for the bullet
     * @param y               - y coordinate for the bullet
     * @param ID              - ID value for the bullet (ID.Bullet)
     * @param assetController - assetController to allow the bullet to be updated
     *                        and rendered
     */
    public Bullet(int x, int y, ID id, AssetController assetController) {
        super(x, y+8, id);
        this.assetController = assetController;
        Sound gunshot = new Sound("Gunshot.wav");
		gunshot.start();
        try {
            if (Player.getDirection() == 1) {
                image = ImageIO.read(getClass().getResource("/images/Bullet_Right.png"));
                dX += 20;
            } else if (Player.getDirection() == -1) {
                image = ImageIO.read(getClass().getResource("/images/Bullet_Left.png"));
                dX -= 20;
            }
        } catch (IOException e) {
            System.out.println("Bullet.java - Failed to set Bullet PNG");
        }
    }

    /**
     * render() method to render the bullet into the game.
     */
    public void render(Graphics g) {
        g.drawImage(image, x, y, null);
    }

    /**
     * update() method to update the x position of the bullet.
     * Calls the Collision() method to allow collision.
     */
    public void update() {
        x += dX;
        y += dY;
        Collision();
    }

    /**
	 * hitBox() method to return a rectangle with the bullet's hit box, used for
	 * collision.
	 */
    public Rectangle hitBox() {

        double boxX = x + dX;
        double boxY = y;
        double boxW = 5 + dX / 2;
        double boxH = 5;

        return new Rectangle((int) boxX, (int) boxY, (int) boxW, (int) boxH);
    }

    /**
	 * hitBox2() method to return a rectangle with the bullet's hit box, used for
	 * collision.
	 */
    public Rectangle hitBox2() {

		double boxX = x;
		double boxY = y + dY;
		double boxW = 5;
		double boxH = 5 + dY / 2;

		return new Rectangle((int) boxX, (int) boxY, (int) boxW, (int) boxH);
    }

    /**
     * Collision() method for bullet collision.
     * Remove the bullet if it collides with a wall.
     * Remove the bullet and the enemy if it collides with an enemy.
     * Increases the player score when the enemy dies.
     * Plays an enemyDamaged sound effect when the enemy is hit.
     */
    private void Collision() {
        for (int i = 0; i < assetController.asset.size(); i++) {
            Asset tempAsset = assetController.asset.get(i);
            if (tempAsset.getID() == ID.Wall) {
                if (hitBox().intersects(tempAsset.hitBox()) || hitBox2().intersects(tempAsset.hitBox())) {
                    assetController.removeAsset(this);
                }
            }
            if (tempAsset.getID() == ID.Enemy) {
                if (hitBox().intersects(tempAsset.hitBox()) || hitBox2().intersects(tempAsset.hitBox())) {
                    ImageLoader.enemyCount--;
                    assetController.removeAsset(this);
                    assetController.removeAsset(tempAsset);
                    Player.increaseScore();
                    Sound enemyDamaged = new Sound("ZombieDeath.wav");
                    enemyDamaged.start();
                }
            }
        }
    }
}
