import java.awt.Rectangle;

/**
 * EmptySpace
 */
public class EmptySpace {

    private int x, y;
    private Rectangle hitBox;
    private static final int width = 24;
    private static final int height = 24;

    public EmptySpace(int i, int j) {
        x = j * 16 - 19;
        y = i * 16 + 45;
        hitBox = new Rectangle(x, y, width, height);
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
}