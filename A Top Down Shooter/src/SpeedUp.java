import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
/**
 * SpeedUp class to create speed boost powerups to increase the player's speed
 * 
 * @author Ethan Hubbell / Tyler Battershell
 */

public class SpeedUp extends Asset{
    /**
	 * Overloading constructor to create an object of the Power class
	 * 
	 * @param x  - x position of the zone
	 * @param y  - y position of the zone
	 * @param id - ID value of the power zone (ID.Power)
	 */
    public SpeedUp(int x, int y, ID id) {
        super(x, y, id);
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
        g.setColor(Color.GRAY);
		g.fillOval(x, y, 64, 64);
	}

    /**
	 * hitBox method to return a rectangle with a similar size to the powerup.
	 */
    public Rectangle hitBox() {
		return new Rectangle(x, y, 64, 64);
	}

}
