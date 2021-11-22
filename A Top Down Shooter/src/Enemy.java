import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Color;

public class Enemy extends Asset {
    
    AssetController assetController;
    private int diffX, diffY;
    private double distance;

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
        super(x, y, id);
        this.assetController = assetController;
    }

    public void update() {
        x += dX;
        y += dY;

        for(int i = 0; i < assetController.asset.size(); i++) {
			if(assetController.asset.get(i).getID() == ID.Player) {
                diffX = x - assetController.asset.get(i).getX(); // calculates the difference between the enemy's x and the player's x
                diffY = y - assetController.asset.get(i).getY(); // calculates the difference between the enemy's y and the player's y
                distance = Math.sqrt(Math.pow(diffX, 2) + Math.pow(diffY, 2)); // Calculates total distance
                }
        }
        // Enemy only moves within 640 pixels of the player
        if (distance <= 640) {
            if (diffX <= 0) {
                dX = 1;
            }
            else if (diffX >= 0){
                dX = -1;
            }

            if (diffY <= 0) {
                dY = 1;
            }
            else if (diffY >= 0){
                dY = -1;
            }
        } else {
            dX = 0;
            dY = 0;
        }

        //Collision();
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
            }
        }
    }

    public void render(Graphics g) {
        g.setColor(Color.red);
		g.fillRect(x, y, 32, 48);
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
