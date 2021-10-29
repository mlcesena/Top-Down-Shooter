import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

//FIXME
public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;
	// Declaring variables to test if the game is running and create a new thread
	// Thread allows multiple tasks to run at the same time
	// AssetController is a class to update and render all objects created
	private boolean gameRunning = false;
	private Thread thread;
	private AssetController assetController;

	/**
	 * Default Game constructor. When this is called, a new window is created, the
	 * game is started, the appropriate variables are declared, and the level is
	 * loaded.
	 */
	public Game() {
		new Window(1900, 1000, "Top Down Shooter", this);
		start();

		assetController = new AssetController();
		this.addKeyListener(new KeyInput(assetController));

		ImageLoader imageLoader = new ImageLoader(assetController);
		imageLoader.loadLevel();

	}

	/**
	 * Starts the game, creates a thread of this run.
	 */
	private void start() {
		gameRunning = true;
		thread = new Thread(this);
		thread.start();
	}
	
	/**
	 *  Pause the game.
	 */
	private void pause() {
		gameRunning = false;
	}
	
	/**
	 *  Resumes the game.
	 */
	private void resume() {
		gameRunning = true;
	}

	/**
	 * Stops the game, waits for the thread to stop
	 */
	private void stop() {
		gameRunning = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * run method is a method inherited from Runnable interface. It requests the
	 * system's focus to allow key inputs and creates a system to update everything
	 * in the window 60 times per second and constantly renders the graphics in the
	 * window. When the time difference gets to 1 (1/updatesPerSecond seconds),
	 * update the objects with update()
	 */
	public void run() {

		this.requestFocus();

		long startTime = System.currentTimeMillis();
		double updatesPerSecond = 60.0;
		double milliSeconds = 1000 / updatesPerSecond;
		double timeChange = 0;

		while (gameRunning) {
			long currentTime = System.currentTimeMillis();
			timeChange += (currentTime - startTime) / milliSeconds;
			startTime = currentTime;
			while (timeChange >= 1) {
				update();
				timeChange--;
			}
			render();
		}
		stop();
	}

	/**
	 * update method updates every object in the game using the assetController
	 */
	public void update() {
		assetController.update();
	}

	/**
	 * render method renders every object in the game using a Buffer Strategy to
	 * help in the process. The BufferStrategy(3) means it will load the next two
	 * frames while it is still on the current one. Disposes of prior used graphics
	 * and makes the next frame available
	 */
	public void render() {
		BufferStrategy buffer = this.getBufferStrategy();
		if (buffer == null) {
			this.createBufferStrategy(3);
			return;
		}

		Graphics g = buffer.getDrawGraphics();
		/* ========================================== */
		/* \/ DRAW HERE \/ */
		/* ========================================== */

		g.setColor(Color.ORANGE);
		g.fillRect(0, 0, 1900, 1000);

		assetController.render(g);

		/* ========================================== */
		/* ^ DRAW HERE ^ */
		/* ========================================== */

		g.dispose();
		buffer.show();

	}

	/**
	 * The main game class. Simply creates a new Game when the program is ran.
	 */
	public static void main(String args[]) {
		new Game();
	}

}