import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * KeyInput class is created to "listen" to the keyboard and receive inputs from
 * the user. Allows the user to control their character.
 * 
 * @author Tyler Battershell
 */
public class KeyInput implements KeyListener {

	AssetController assetController;

	/**
	 * Overloading constructor to initialize the assetController, making sure there
	 * is only one controller for the entire game.
	 * 
	 * @param assetController - Passed from Game class. It is used to cycle through
	 *                        assets and create new ones
	 */
	public KeyInput(AssetController assetController) {
		this.assetController = assetController;
	}

	/**
	 * This method is called when a key on the keyboard is pressed while the game is
	 * running. It cycles through the assets to find an ID of player, then updates
	 * the movement boolean's for the controller.
	 */
	public void keyPressed(KeyEvent e) {

		for (int i = 0; i < assetController.asset.size(); i++) {

			if (assetController.asset.get(i).getID() == ID.Player) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_W:
					assetController.setUp(true);
					break;
				case KeyEvent.VK_S:
					assetController.setDown(true);
					break;
				case KeyEvent.VK_D:
					assetController.setRight(true);
					break;
				case KeyEvent.VK_A:
					assetController.setLeft(true);
					break;
				case KeyEvent.VK_SHIFT:
					assetController.setSprint(true);
					break;
				case KeyEvent.VK_0:
					Window.setPlayerHealth();
					break;
				case KeyEvent.VK_9:
					//Currently changes levels.
					ImageLoader.zombieCount = 0;
					Window.level++;
					break;
				}
			}
		}
	}

	/**
	 * This method is called when a key on the keyboard is released while the game is
	 * running. It cycles through the assets to find an ID of player, then updates
	 * the movement boolean's for the controller.
	 */
	public void keyReleased(KeyEvent e) {

		for (int i = 0; i < assetController.asset.size(); i++) {

			if (assetController.asset.get(i).getID() == ID.Player) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_W:
					assetController.setUp(false);
					break;
				case KeyEvent.VK_S:
					assetController.setDown(false);
					break;
				case KeyEvent.VK_D:
					assetController.setRight(false);
					break;
				case KeyEvent.VK_A:
					assetController.setLeft(false);
					break;
				case KeyEvent.VK_SHIFT:
					assetController.setSprint(false);
					break;
				}
			}
		}
	}

	/**
	 * Needed for KeyListener interface
	 */
	public void keyTyped(KeyEvent e) {
	}

}
