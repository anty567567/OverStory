import java.awt.*;

public class Tile {
    private boolean passable;
    private Rectangle square;
    private Image sprite;
    private String type;

    public Tile(boolean passable, Rectangle square, Image sprite, String type){
        this.passable = passable;
        this.square = square;
        this.sprite = sprite;
        this.type = type;
    }

    public boolean isPassable() {
        return passable;
    }

    public void setPassable(boolean passable) {
        this.passable = passable;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Rectangle getSquare() {
        return square;
    }

    public void setSquare(Rectangle square) {
        this.square = square;
    }

    public Image getSprite() {
        return sprite;
    }

    public void setSprite(Image sprite) {
        this.sprite = sprite;
    }
}
