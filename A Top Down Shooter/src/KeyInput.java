import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * KeyInput class is created to "listen" to the keyboard and receive inputs from
 * the user. Allows the user to control their character.
 * 
 * @authors Tyler Battershell, Michael Cesena, and Ethan Hubbell
 */
public class KeyInput implements KeyListener {

	AssetController assetController;

	/**
	 * Overloading constructor to initialize the assetController, making sure there
	 * is only one controller for the entire game.
	 * 
	 * @param assetController - assetController to allow the assets to be updated
	 *                        and rendered.
	 */
	public KeyInput(AssetController assetController) {
		this.assetController = assetController;
	}

	/**
	 * keyPressed() method is called when a key on the keyboard is pressed while the
	 * game is running.
	 * Cycles through the assets to find an ID.Player.
	 * Updates the movement booleans for that asset.
	 * Creates bullet objects when necessary.
	 * Plays Reload sound when necessary.
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
						break;
					case KeyEvent.VK_R:
						if (Player.getAmmoCount() < 10) {
							Player.reload();
							Sound reload = new Sound("Reload.wav");
							reload.start();
						}
						break;
					case KeyEvent.VK_ENTER:
						if (ImageLoader.level == 0) {
							ImageLoader.enemyCount = 0;
							Game.imageLoader.loadLevel();
						}
						break;
					case KeyEvent.VK_SPACE:
						if (Player.getAmmoCount() == 0) {
							Window.requestReload();
						} else {
							assetController.addAsset(new Bullet(assetController.asset.get(i).getX(),
									assetController.asset.get(i).getY(), ID.Bullet, assetController));
							Player.setAmmo();
						}
						break;

					// Testing Keys
					// case KeyEvent.VK_0:
					// //Subtracts player health to test levels and enemies
					// Player.subtractHealth();
					// break;
					// case KeyEvent.VK_9:
					// //Currently changes levels by setting enemy count to 0.
					// ImageLoader.enemyCount = 0;
					// break;
				}
			}
		}
	}

	/**
	 * keyReleased() method is called when a key on the keyboard is released while the game is running.
	 * Cycles through the assets to find an ID.Player.
	 * Updates the movement booleans for that asset.
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
				}
			}
		}
	}

	/**
	 * keyTyped() method is blank. Just needed for KeyListener interface.
	 */
	public void keyTyped(KeyEvent e) {
	}
}
