import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

/**
 * Floor class is the class created to add floors into the game. Each pixel with
 * the corresponding Floor color will be turned into a 32x32 "floor"
 * 
 * @author Ethan Hubbell
 */
public class Floor extends Asset {
	//Initializing wall and updater
	private static BufferedImage image;
	public static int count = 0;
    public static int countF = 0;
	public static int xFloor = 0;
	public static int yFloor = 0;	
	

	/**
	 * Overloading constructor to create an object of the Floor class
	 * 
	 * @param x  - x position of the floor
	 * @param y  - y position of the floor
	 * @param id - ID value of the floor (ID.Floor)
	 */
	public Floor(int x, int y, ID id) {
		super(x, y, id);

		if(count == 0) {
			try {
				Random randGen = new Random();
				int floor = randGen.nextInt(2);
				switch(floor){
					case 0:
						image = ImageIO.read(getClass().getResource("/images/Floor_Tile.png"));
						break;
					case 1:
						image = ImageIO.read(getClass().getResource("/images/Floor_Tile_Tinted.png"));
						break;
                    default:
                        image = ImageIO.read(getClass().getResource("/images/Floor_Tile_Tinted.png"));
                        break;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			count = 1;
		}

	}

	/**
	 * update method to update the floors. There is no movement so there is no code.
	 */
	public void update() {
		
	}
	
	/**
	 * render method to render the floors into the game.
	 */
	public void render(Graphics g) {
            g.drawImage(image, x, y, null);
	}
	
	/**
	 * hitBox method to return a rectangle with the Floor's hit box (Same size as the wall)
	 */
	public Rectangle hitBox() {
		return new Rectangle(x, y, 32, 32);
	}

}