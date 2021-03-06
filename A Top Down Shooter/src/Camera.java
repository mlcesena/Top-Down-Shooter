/**
 * Camera class is a class that helps translate the assets in the game, making
 * it appear as though the camera is following the player
 * 
 * @author Tyler Battershell
 */
public class Camera {

    private double x, y;

    /**
     * Overloading constructor to set the x and y position of the camera
     * 
     * @param x - x position of the camera
     * @param y - y position of the camera
     */
    public Camera(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * update() method changes the x and y values of the camera based on the x and y
     * values of an asset passed in. Does not allow the camera to show outside the
     * map boundaries.
     * 
     * @param asset - Pass in the Player asset.
     */
    public void update(Asset asset) {

        x += ((asset.getX() - x) - 950) * 0.3f;
        y += ((asset.getY() - y) - 500) * 0.3f;

        if (x <= 0)
            x = 0;
        if (x >= 1566)
            x = 1566;
        // if (x >= 1670)
        // x = 1670;
        if (y <= 0)
            y = 0;
        if (y >= 714)
            y = 714;
        // if (y >= 840)
        // y = 840;
        if (ImageLoader.level == 0) {
            x = 0;
            y = 0;
        }
    }

    /**
     * getX(), setX(), getY(), and setY() are getters and setters for the camera's x
     * and y position
     */
    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
}
