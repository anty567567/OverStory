import java.awt.*;
import java.awt.image.BufferedImage;

public class Enemy {
    private int speed, maxHealth, health, level, xpGivenOnDeath;
    private String name;
    private Image image, smallImage;
    private Rectangle hitBox;
    private boolean alive;


    Enemy(String name, int level, Image image, Rectangle hitBox, Image smallImage) {
        this.name = name;
        this.level = level;
        this.image = image;
        this.hitBox = hitBox;
        this.maxHealth = level*15 + 35;
        this.health = this.maxHealth;
        this.xpGivenOnDeath = level*100;
        this.speed = level*2;
        this.alive = true;
        this.smallImage = smallImage;
    }
    Enemy(Image image) {
        this.name = "blank";
        this.level = 0;
        this.image = image;
        this.hitBox = new Rectangle(0,0,0,0);
        this.maxHealth = 0;
        this.health = 0;
        this.xpGivenOnDeath = 0;
        this.speed = 0;
        this.alive = false;
    }

    //Getters and setters


    public void setImage(Image image) {
        this.image = image;
    }

    public Image getSmallImage() {
        return smallImage;
    }

    public void setSmallImage(Image smallImage) {
        this.smallImage = smallImage;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getXpGivenOnDeath() {
        return xpGivenOnDeath;
    }

    public void setXpGivenOnDeath(int xpGivenOnDeath) {
        this.xpGivenOnDeath = xpGivenOnDeath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public Rectangle getHitBox() {
        return hitBox;
    }

    public void setHitBox(Rectangle hitBox) {
        this.hitBox = hitBox;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }
}

