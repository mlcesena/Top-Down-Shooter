import java.awt.Graphics;
import java.awt.Rectangle;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
/**
 * MedKit class to allow player to pick up med kits to heal themselves for 40 health.
 * 
 * @author Tyler Battershell
 */

public class MedKit extends Asset{

	private BufferedImage image;

    /**
	 * Overloading constructor to create an object of the Power class
	 * 
	 * @param x  - x position of the zone
	 * @param y  - y position of the zone
	 * @param id - ID value of the power zone (ID.Power)
	 */
    public MedKit(int x, int y, ID id) {
        super(x, y, id);
		try {
			image = ImageIO.read(getClass().getResource("/images/MedKit.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    /**
	 * update method to update the powerups. There is no movement so there is no code.
	 */
    public void update() {
	}

    /**
	 * render method to render the powerup into the game
	 */
    public void render(Graphics g) {
        g.drawImage(image, x, y, null);
	}

    /**
	 * hitBox method to return a rectangle with a similar size to the powerup.
	 */
    public Rectangle hitBox() {
		return new Rectangle(x, y, 64, 48);
	}

}