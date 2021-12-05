import java.awt.Graphics;
import java.util.ArrayList;

/**
 * AssetController class is one of the most important classes in the
 * program. It is used throughout the code to manage the assets in the game,
 * allowing them to update and render correctly. It contains methods used
 * for adding and removing assets.
 * 
 * @author Tyler Battershell
 */
public class AssetController {

	public ArrayList<Asset> asset = new ArrayList<Asset>();
	private boolean up = false, down = false, left = false, right = false, sprint = false;

	/**
	 * update() method cycles through every asset in the game and updates it based
	 * on that objects' update() method.
	 */
	public void update() {
		for (int i = 0; i < asset.size(); i++)
			asset.get(i).update();
	}

	/**
	 * render() method cycles through every asset in the game and renders it based
	 * on that objects' reder() method.
	 */
	public void render(Graphics g) {
		for (int i = 0; i < asset.size(); i++)
			asset.get(i).render(g);
	}

	/**
	 * addAsset() method should be called to add an asset to the game.
	 * 
	 * @param tempAsset - The asset to be added.
	 */
	public void addAsset(Asset tempAsset) {
		asset.add(tempAsset);
	}

	/**
	 * removeAsset() method should be called to get rid of an asset that is
	 * in the game.
	 * 
	 * @param tempAsset - The asset to be removed.
	 */
	public void removeAsset(Asset tempAsset) {
		asset.remove(tempAsset);
	}

	/**
	 * newAssetList() method deletes all current assets in the list and creates a
	 * new list of assets.
	 */
	public void newAssetList() {
		asset = new ArrayList<Asset>();
	}

	/**
	 * isUp(), setUp(), isDown(), setDown() isLeft(), setLeft(), isRight(),
	 * setRight(), isSprint(), and setSprint() are all getters and setters for
	 * movement controls
	 */
	public boolean isUp() {
		return up;
	}

	public void setUp(boolean up) {
		this.up = up;
	}

	public boolean isDown() {
		return down;
	}

	public void setDown(boolean down) {
		this.down = down;
	}

	public boolean isLeft() {
		return left;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}

	public boolean isRight() {
		return right;
	}

	public void setRight(boolean right) {
		this.right = right;
	}

	public boolean isSprint() {
		return sprint;
	}

	public void setSprint(boolean sprint) {
		this.sprint = sprint;
	}

	/**
	 * setAllFalse() method sets all the movement controls to false to prevent
	 * movement from carrying over between runs/levels.
	 */
	public void setAllFalse() {
		up = false;
		down = false;
		left = false;
		right = false;
		sprint = false;
	}

}
