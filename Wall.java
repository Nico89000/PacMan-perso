import java.awt.Rectangle;

/**
 * Wall
 */
public class Wall {

    private int x, y;
    private Rectangle hitBox;
    private static final int width = 6;
    private static final int height = 6;

    public Wall(int i, int j) {
        x = j * 16 - 10;
        y = i * 16 + 53;
        hitBox = new Rectangle(x, y, width, height);
    }

    /**
     * Pour des murs plus larges
     * 
     * @param i     position i dans le gameboard
     * @param j     position j dans le gameboard
     * @param width nouvelle largeur
     */
    public Wall(int i, int j, int newwidth) {
        x = j * 16 - (newwidth - 3);
        y = i * 16 + 53;
        hitBox = new Rectangle(x, y, newwidth, height);
    }

    public Rectangle getHitBox() {
        return hitBox;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
