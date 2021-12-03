import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

/**
 * ImageLoader class is created to load images from a .png file. This is used to
 * import the map key, maps, and sprites to the game.
 * 
 * @author Tyler Battershell / Ethan Hubbell
 */
public class ImageLoader {
	//Changable level to implament stage changes (WIP).
	public static int enemyCount;
	public static int level = 0;
	public static int floorCount = 0;
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
		
		//Checks what level to take the map of.
		newLevel();
		switch(level) {
			case 1:
				//Title always called first and only at begining of game
				mapLevel = readImage("/images/Title.png");
				break;
			case 999:
				//End screen used to be called before new pop up was implemented.
				mapLevel = readImage("/images/Game_Over.png");
				break;
			default:
				//Randomly chooses the next level that will be displayed
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
		int floorPixel = getRGB(4, 0, mapColorKey);
		
		//rendering power -> wall -> enemy -> player coords
		for(int i = 0; i < 5; i++) {
			for (int imageX = 0; imageX < w; imageX++) {
				for (int imageY = 0; imageY < h; imageY++) {
					int pixel = getRGB(imageX, imageY, mapLevel);
					
					if (pixel == wallPixel & i == 2)
						assetController.addAsset(new Wall(imageX * 32, imageY * 32, ID.Wall, level));
					else if (pixel == playerPixel & i == 4) {
						assetController.addAsset(new Floor(imageX * 32, imageY * 32, ID.Floor));
						playerPixelX = imageX;
						playerPixelY = imageY;
					} else if (pixel == enemyPixel & i == 3) {
						assetController.addAsset(new Floor(imageX * 32, imageY * 32, ID.Floor));
						assetController.addAsset(new Enemy(imageX * 32, imageY * 32, ID.Enemy, assetController));
						enemyCount++;
					}
					else if (pixel == powerPixel & i == 1) {
						assetController.addAsset(new Floor(imageX * 32, imageY * 32, ID.Floor));
						assetController.addAsset(new SpeedUp(imageX * 32, imageY * 32, ID.SpeedUp));
					}
					else if (pixel == floorPixel & i == 0)
						assetController.addAsset(new Floor(imageX * 32, imageY * 32, ID.Floor));
					
				}
			}

		}
		//Creates player based on the coords given by level map.
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
		//Removes assets to free computer space
		for (int i = 0; i < assetController.asset.size(); i++) {
			Asset tempAsset = assetController.asset.get(i);
			assetController.removeAsset(tempAsset);
		}
		//Resets and updates player healthbar
		Player.setHealth(200);
		Player.reload();
		Window.updateHealthBar();
		
		//Adds level and resets wall count for new color
		level++;
		Wall.count = 0;

		//sets player movement to false to avoid held key issue
		assetController.setAllFalse();

		//Creates new asset controller to delete past assets and collisions for them
		assetController.newAssetList();
	}

}
