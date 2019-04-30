/**
 * Pac
 */
public class Pac {

    private int x, y;
    private final static int width = 24;
    private final static int height = 24;
    private final static double speed = 4;

    public Pac() {
        x = 213;
        y = 412;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public double getSpeed() {
        return speed;
    }

    public void setX(int newX) {
        x = newX;
    }

    public void setY(int newY) {
        y = newY;
    }

}
