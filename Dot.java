import java.awt.Rectangle;

/**
 * Dot
 */
public class Dot {

    private int i, j, x, y;
    private Rectangle hitBox;
    private int width;
    private int height;

    public Dot(int xi, int xj) {
        width = 4;
        height = 4;
        i = xi;
        j = xj;
        x = j * 16 - 9;
        y = i * 16 + 56;
        hitBox = new Rectangle(x, y, width, height);
    }

    public Rectangle getHitBox() {
        return hitBox;
    }

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }

    public int getX() {
        return x;
    }

    public void setX(int n) {
        x = x + n;
    }

    public int getY() {
        return y;
    }

    public void setY(int n) {
        y = y + n;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
