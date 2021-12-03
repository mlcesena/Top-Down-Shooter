import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

/**
 * ImageLoader class is created to load images from a .png file. This is used to
 * import the map key, maps, and sprites to the game.
 * 
 * @author Tyler Battershell
 */
public class ImageLoader {
	//Changable level to implament stage changes (WIP).
	public static int enemyCount;
	public static int level = 0;
	AssetController assetController;

	/**
	 * Overloading constructor to initialize the assetController, making sure there
	 * is only one controller for the entire game.
	 * 
	 * @param assetController - Passed from Game class. It is used to cycle through
	 *                        assets and create new ones
	 */
	public ImageLoader(AssetController assetController) {
		this.assetController = assetController;
	}

	/**
	 * getRGB is a method that reads a pixel of an image based on x and y values
	 * passed in, then turns that reading into an integer representation of its RGB.
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
	 * loadLevel is a method used to load in a specific level by reading images and
	 * scanning the entire image, comparing the RGB values of each pixel to the map
	 * key RGB values in order to create new objects based on the RGB. Player is
	 * always generated last.
	 */
	public void loadLevel() {

		BufferedImage mapLevel = null;
		BufferedImage mapColorKey = null;

		mapColorKey = readImage("/images/map_key.png");
		
		//Checks what level to take the map of (WIP).
		newLevel();
		switch(level) {
			case 0:
				mapLevel = readImage("/images/TempTitle.png");
				break;
			case 999:
				mapLevel = readImage("/images/Game_Over.png");
				break;
			default:
				Random randGen = new Random();
				int mapChooser = randGen.nextInt(3) + 1;
				switch(mapChooser){
					case 1: mapLevel = readImage("/images/level1.png"); break;
					case 2: mapLevel = readImage("/images/level2.png"); break;
					case 3: mapLevel = readImage("/images/level3.png"); break;
				}
		}
		

		int h = mapLevel.getHeight();
		int w = mapLevel.getWidth();
		int playerPixelX = 0;
		int playerPixelY = 0;
		int wallPixel = getRGB(0, 0, mapColorKey);
		int playerPixel = getRGB(1, 0, mapColorKey);
		int enemyPixel = getRGB(2, 0, mapColorKey);
		int powerPixel = getRGB(3, 0, mapColorKey);
		
		//rendering power -> wall -> enemy -> player
		for(int i = 0; i < 4; i++) {
			for (int imageX = 0; imageX < w; imageX++) {
				for (int imageY = 0; imageY < h; imageY++) {
					int pixel = getRGB(imageX, imageY, mapLevel);
					
					if (pixel == wallPixel & i == 1)
						assetController.addAsset(new Wall(imageX * 32, imageY * 32, ID.Wall, level));
					else if (pixel == playerPixel & i == 3) {
						playerPixelX = imageX;
						playerPixelY = imageY;
					} else if (pixel == enemyPixel & i == 2) {
						assetController.addAsset(new Enemy(imageX * 32, imageY * 32, ID.Enemy, assetController));
						enemyCount++;
					}
					else if (pixel == powerPixel & i == 0)
						assetController.addAsset(new SpeedUp(imageX * 32, imageY * 32, ID.SpeedUp));
					
				}
			}

		}
		assetController.addAsset(new Player(playerPixelX * 32, playerPixelY * 32, ID.Player, assetController));
	}

	/**
	 * readImage is a method that reads an image based off its path
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
	 * newLevel is a method that should be called each time a new level is loaded.
	 * Resets variables and removes all current assets.
	 */
	public void newLevel() {
		for (int i = 0; i < assetController.asset.size(); i++) {
			Asset tempAsset = assetController.asset.get(i);
			assetController.removeAsset(tempAsset);
		}
		Player.setHealth(200);
		//Window.healthBar.repaint();
		level++;
		Wall.count = 0;
		assetController.setAllFalse();
		assetController.newAssetList();
	}

}
