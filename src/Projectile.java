import java.awt.*;

public class Projectile {

    private int xPosition, yPosition;
    private Rectangle Rect;

    public Projectile(int x, int y, Rectangle Rect) {
        this.xPosition = x;
        this.yPosition = y;
        this.Rect = Rect;
    }

    public int getxPosition() {
        return xPosition;
    }

    public void setxPosition(int xPosition) {
        this.xPosition = xPosition;
    }

    public int getyPosition() {
        return yPosition;
    }

    public void setyPosition(int yPosition) {
        this.yPosition = yPosition;
    }

    public Rectangle getRect() {
        return Rect;
    }

    public void setRect(Rectangle rect) {
        Rect = rect;
    }
}
