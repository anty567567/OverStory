import java.awt.*;

public class player {

    private int level, health, xp, speed, strength, maxHealth, statusEffectDamage, statusEffectChance;
    private Rectangle characterRect, right, left, top, bottom;

    public player() {
        this.characterRect = new Rectangle(920, 480, 120, 120);
        this.right = new Rectangle(characterRect.x + characterRect.width, characterRect.y, 1, characterRect.height);
        this.left = new Rectangle(characterRect.x - 1, characterRect.y, 1, characterRect.height);
        this.top = new Rectangle(characterRect.x, characterRect.y - 1, characterRect.width, 1);
        this.bottom = new Rectangle(characterRect.x, characterRect.y + characterRect.height, characterRect.width, 1);
        this.maxHealth = 100;
        this.health = 100;
        this.level = 1;
        this.xp = 0;
        this.speed = 20;
        this.strength = 5;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public Rectangle getRect() {
        return characterRect;
    }

    public void setRect(Rectangle characterRect) {
        this.characterRect = characterRect;
    }

    public int getXpForLevelUp() {
        if (level < 8) {
            return 500 + 30 * level;
        }
        return (int) (0.02 * Math.pow(level, 3) + 3.06 * Math.pow(level, 2) + (105.6 * level) - 895);
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public void setStatusEffectChance(int statusEffectChance) {
        this.statusEffectChance = statusEffectChance;
    }

    public Rectangle getCharacterRect() {
        return characterRect;
    }

    public void setCharacterRect(Rectangle characterRect) {
        this.characterRect = characterRect;
    }

    public int getSpeed() {
        return speed;
    }

    public int getStrength() {
        return strength;
    }


    public int getStatusEffectChance() {
        int firstRandom = (int) (Math.random() * 4 + 1);
        int secondRandom = (int) (Math.random() * 4 + 1);
        if (firstRandom == secondRandom) {
            return 1;
        } else {
            return 0;
        }
    }

    public int getStatusEffectDamage() {
        int statusEffectDamage = (int) (Math.random() * 10 + 6);
        return statusEffectDamage;
    }

    public int getAbility1Damage() {
        return ((level + strength) / 2 + speed / 2);
    }

    public int getAbility2Damage() {
        return ((int) ((level + speed) * 0.80));
    }

    public int getAbility3Damage() {
        return (int) ((level + strength) * 1.30);
    }

    public int getAbility4Damage() {
        return (int) ((level) * 1.30);
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public void setStatusEffectDamage(int statusEffectDamage) {
        this.statusEffectDamage = statusEffectDamage;
    }

    public Rectangle getRight() {
        return right;
    }

    public Rectangle getLeft() {
        return left;
    }

    public Rectangle getTop() {
        return top;
    }

    public Rectangle getBottom() {
        return bottom;
    }

    public void setRight(Rectangle right) {
        this.right = right;
    }

    public void setLeft(Rectangle left) {
        this.left = left;
    }

    public void setTop(Rectangle top) {
        this.top = top;
    }

    public void setBottom(Rectangle bottom) {
        this.bottom = bottom;
    }
}
