import java.awt.Graphics;
import java.awt.Rectangle;

public class HUD extends Asset {
    
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
    public HUD(int x, int y, ID id, AssetController assetController) {
        super(x, y, id);
        this.assetController = assetController;
    }

    @Override
    public void update() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void render(Graphics g) {
        
        
    }

    @Override
    public Rectangle hitBox() {
        // TODO Auto-generated method stub
        return null;
    }
}
