import java.awt.Rectangle;

/**
 * Ghost
 */
public class Ghost {

    private int x, y;
    private final static int width = 24;
    private final static int height = 24;
    private final static double speed = 4;
    private Rectangle hitBox;
    private boolean isMoving;
    private String lastDirection;

    public Ghost(int nx, int ny) {
        x = nx;
        y = ny;
        hitBox = new Rectangle(x, y, width, height);
        isMoving = false;
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

    public String getLastDirection() {
        return lastDirection;
    }

    public boolean getIsMoving() {
        return isMoving;
    }

    public void setX(int newX) {
        x = newX;
    }

    public void setY(int newY) {
        y = newY;
    }

    public void updateHitBox() {
        hitBox.setLocation(x, y);
    }
}
