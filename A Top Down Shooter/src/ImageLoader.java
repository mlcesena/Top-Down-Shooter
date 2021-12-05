import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;

/**
 * ImageLoader class loads images from a PNG file.
 * This is used to import the map key, maps, and sprites to the game.
 * 
 * @author Tyler Battershell / Ethan Hubbell
 */
public class ImageLoader {

	public static int enemyCount;
	public static int level = -1;
	public static int floorCount = 0;
	AssetController assetController;

	/**
	 * Overloading constructor to initialize the assetController, making sure there
	 * is only one controller for the entire game.
	 * 
	 * @param assetController - assetController to allow the assets to be updated,
	 *                        rendered, created, and removed.
	 */
	public ImageLoader(AssetController assetController) {
		this.assetController = assetController;
	}

	/**
	 * getRGB() method reads a pixel of an image based on x and y values passed in.
	 * Turns that reading into an integer representation of its RGB.
	 * 
	 * @param x     - The x location of the pixel
	 * @param y     - The y location of the pixel
	 * @param image - The image being looked at
	 * @return - returns an integer representation of an RGB.
	 */
	private int getRGB(int x, int y, BufferedImage image) {
		int pixel = image.getRGB(x, y);
		int red = (pixel >> 16) & 0xff;
		int green = (pixel >> 8) & 0xff;
		int blue = (pixel) & 0xff;
		int RGB = red + (green * 1000) + (blue * 1000000);
		return RGB;
	}

	/**
	 * loadLevel() method loads in a specific level by reading images and scanning
	 * the entire image, comparing the RGB values of each pixel to the map key RGB
	 * values in order to create new objects based on the RGB.
	 * Calls newLevel(). Loads the title level at the beginning.
	 * Randomly chooses a level to load after the title.
	 * Plays a roundWin sound when the new level is loaded in (After level 1).
	 * Creates the objects in the game through a tiered system to prevent any
	 * rendering issues.
	 */
	public void loadLevel() {

		BufferedImage mapLevel = null;
		BufferedImage mapColorKey = null;
		mapColorKey = readImage("/images/map_key.png");
		newLevel();

		switch (level) {
			case 0:
				mapLevel = readImage("/images/Title.png");
				break;
			default:
				Random randGen = new Random();
				int mapChooser = randGen.nextInt(9) + 1;

				if (level != 1) {
					Sound roundWin = new Sound("RoundWin.wav");
					roundWin.start();
				}

				switch (mapChooser) {
					case 1:
						mapLevel = readImage("/images/level1_1.png");
						break;
					case 2:
						mapLevel = readImage("/images/level2_1.png");
						break;
					case 3:
						mapLevel = readImage("/images/level3_1.png");
						break;
					case 4:
						mapLevel = readImage("/images/level1_2.png");
						break;
					case 5:
						mapLevel = readImage("/images/level2_2.png");
						break;
					case 6:
						mapLevel = readImage("/images/level3_2.png");
						break;
					case 7:
						mapLevel = readImage("/images/level1_3.png");
						break;
					case 8:
						mapLevel = readImage("/images/level2_3.png");
						break;
					case 9:
						mapLevel = readImage("/images/level3_3.png");
						break;
				}
		}

		int h = mapLevel.getHeight();
		int w = mapLevel.getWidth();
		int wallPixel = getRGB(0, 0, mapColorKey);
		int playerPixel = getRGB(1, 0, mapColorKey);
		int enemyPixel = getRGB(2, 0, mapColorKey);
		int speedPixel = getRGB(3, 0, mapColorKey);
		int floorPixel = getRGB(4, 0, mapColorKey);
		int medKitPixel = getRGB(5, 0, mapColorKey);

		for (int i = 0; i < 6; i++) {
			for (int imageX = 0; imageX < w; imageX++) {
				for (int imageY = 0; imageY < h; imageY++) {
					int pixel = getRGB(imageX, imageY, mapLevel);

					if (pixel == floorPixel & i == 0)
						assetController.addAsset(new Floor(imageX * 32, imageY * 32, ID.Floor));
					else if (pixel == wallPixel & i == 1)
						assetController.addAsset(new Wall(imageX * 32, imageY * 32, ID.Wall, level));
					else if (pixel == speedPixel & i == 2) {
						assetController.addAsset(new Floor(imageX * 32, imageY * 32, ID.Floor));
						assetController.addAsset(new SpeedUp(imageX * 32, imageY * 32, ID.SpeedUp));
					} else if (pixel == medKitPixel & i == 3) {
						assetController.addAsset(new Floor(imageX * 32, imageY * 32, ID.Floor));
						assetController.addAsset(new MedKit(imageX * 32, imageY * 32, ID.MedKit));
					} else if (pixel == enemyPixel & i == 4) {
						assetController.addAsset(new Floor(imageX * 32, imageY * 32, ID.Floor));
						assetController.addAsset(new Enemy(imageX * 32, imageY * 32, ID.Enemy, assetController));
						enemyCount++;
					} else if (pixel == playerPixel & i == 5) {
						assetController.addAsset(new Floor(imageX * 32, imageY * 32, ID.Floor));
						assetController.addAsset(new Player(imageX * 32, imageY * 32, ID.Player, assetController));
					}
				}
			}
		}
	}

	/**
	 * readImage() method reads an image based off its relative path
	 * 
	 * @param path - The file pathway to access the image
	 * @return - Returns a BufferedImage representation of the image file.
	 */
	public BufferedImage readImage(String path) {

		BufferedImage image = null;

		try {
			image = ImageIO.read(getClass().getResource(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return image;
	}

	/**
	 * newLevel() method should be called each time a new level is loaded.
	 * Resets variables and removes all current assets.
	 */
	public void newLevel() {

		for (int i = 0; i < assetController.asset.size(); i++) {
			Asset tempAsset = assetController.asset.get(i);
			assetController.removeAsset(tempAsset);
		}
		Player.setHealth(200);
		Player.reload();
		Window.updateHealthBar();
		
		level++;
		Window.updateLevel();
		Wall.count = 0;
		Floor.count = 0;

		assetController.setAllFalse();
		assetController.newAssetList();
	}

}
