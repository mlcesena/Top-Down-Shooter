/**
 * Camera class is a class that helps translate the assets in the game, making
 * it appear as though the camera is following the player
 */
public class Camera {

    private double x, y;

    /**
     * Constructor (Overloading)
     * 
     * @param x - x position of the camera
     * @param y - y position of the camera
     */
    public Camera(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * update method changes the x and y values of the camera based on the x and y
     * values of an asset passed in. Does not allow the camera to show outside the
     * map boundaries.
     * 
     * @param asset
     */
    public void update(Asset asset) {

        x += ((asset.getX() - x) - 950) * 0.3f;
        y += ((asset.getY() - y) - 500) * 0.3f;

        if (x <= 0)
            x = 0;
        if (x >= 1566)
            x = 1566;
        if (y <= 0)
            y = 0;
        if (y >= 714)
            y = 714;
        if(ImageLoader.level == 1) {
            x = 0;
            y = 0;
        }


    }

    /**
     * Getters and setters for x and y values
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
