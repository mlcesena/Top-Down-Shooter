import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
/**
 * Power class to create areas in which the players speed is sped up.
 * Each pixel with power pixle will be turned into area of "Power".
 * 
 * @author Ethan Hubbell
 */

public class Power extends Asset{
    //variable to keep the zone underneith the player
    public int count = 0;

    /**
	 * Overloading constructor to create an object of the Power class
	 * 
	 * @param x  - x position of the zone
	 * @param y  - y position of the zone
	 * @param id - ID value of the power zone (ID.Power)
	 */
    public Power(int x, int y, ID id) {
        super(x, y, id);
    }

    /**
	 * update method to update the walls. There is no movement so there is no code.
	 */
    public void update() {
	}

    /**
	 * render method to render the power zones into the game.
     * used only once to keep power zone under player. (CHANGED)
	 */
    public void render(Graphics g) {
        g.setColor(Color.GRAY);
        g.fillRect(x, y, 32, 32);
	}

    /**
	 * hitBox method to return a rectangle with the power zones hit box (Same size as the power zone)
     * to deticate which area the players speed is sped up.
	 */
    public Rectangle hitBox() {
		return new Rectangle(x, y, 32, 32);
	}

}
