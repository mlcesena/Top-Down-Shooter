import java.awt.Graphics;
import java.awt.Rectangle;

/**
 * The class Asset is a superclass that is used by every entity in the game. It
 * contains methods for getting and setting position values (x & y), movement
 * values (dX & dY), and the ID field
 * 
 * @author Tyler Battershell
 */
public abstract class Asset {

	protected int x, y;
	protected double dX = 0, dY = 0;
	protected ID id;

	/**
	 * Overloading constructor used every time an asset is created through any of
	 * its subclass method calls
	 * 
	 * @param x  - The x location of the asset
	 * @param y  - The y location of the asset
	 * @param id - They ID of the asset. Determines how it interacts with others
	 */
	public Asset(int x, int y, ID id) {
		this.x = x;
		this.y = y;
		this.id = id;
	}

	/**
	 * These are all methods that are going to be required to implement in any
	 * subclass of Asset.
	 */
	public abstract void update();

	public abstract void render(Graphics g);

	public abstract Rectangle hitBox();

	/**
	 * These are all getters and setters for all the variables in the Asset class.
	 */
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public double getdX() {
		return dX;
	}

	public void setdX(double dX) {
		this.dX = dX;
	}

	public double getdY() {
		return dY;
	}

	public void setdY(double dY) {
		this.dY = dY;
	}

	public ID getID() {
		return id;
	}

	public void setID(ID id) {
		this.id = id;
	}
}
