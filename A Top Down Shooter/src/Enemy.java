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
                distance = Math.sqrt((x - assetController.asset.get(i).getX()) * (x - assetController.asset.get(i).getX()) + (y - assetController.asset.get(i).getY()) * (y - assetController.asset.get(i).getY())); // calculates the total distance between the enemy and player
            }
        }
        // if (diffX < x) {
        //     dX = 1;
        // }
        // else if (diffX > x){
        //     dX = -1;
        // }

        // if (diffY < y) {
        //     dY = 1;
        // }
        // else if (diffY > y){
        //     dY = -1;
        // }
        dX = ((-1 / distance) * diffX); // sets enemy x velocity
        dY = ((-1 / distance) * diffY); // sets enemy y velocity
    }

    public void render(Graphics g) {
        g.setColor(Color.red);
		g.fillRect(x, y, 32, 48);
    }

    public Rectangle hitBox() {
        return new Rectangle(x, y, 32, 48);
    }
    
}
