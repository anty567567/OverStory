import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;

import java.awt.Graphics;

/*
By Anthony De Luca
Make sure you press 'g' when the enemies attacks are coming at you
May have to use god mode to beat the game as it is horribly unbalanced
Thanks and Enjoy
 */
public class OverStoryMainGame extends JPanel implements KeyListener {

    public static JFrame screen = new JFrame();

    public static Image titleScreenIntro, titleScreenFull, skeleton, victory, textOne, options, textTwo, textThree, textFour, bubbleOne, bubbleTwo, heart, levelScreen, pause, bigHero, cornerBottomLeft, arrowRight, arrowLeft, instructionsOne, instructionsTwo, instructionsThree, cornerBottomRight, cornerTopLeft, cornerTopRight, transitionBottomLeft, transitionBottomRight, transitionTopRight, bat, grass, tree, slime, boss, sand, hero, transitionUp, transitionDown, transitionLeft, transitionRight, transitionTopLeft;

    public static final int size = 120, a = 10, b = 11, c = 12, d = 13, f = 14, g = 15, h = 16, l = 17, m = 18, n = 19, o = 20, p = 21, q = 22, r = 23, s = 24, t = 25, u = 26, v = 27, w = 28, x = 29, y = 30, z = 31;

    public static int mapPosX = 0, mapPosY = 0;

    public static boolean start = false, secondStart = false, bossDrawn = false, cutscene = false, win = false, pauseScreen = false, firstInstruction = false, secondInstruction = false, thirdInstruction = false, game = false, overWorld = false, moveLeft, moveRight, moveDown, moveUp, intersectingWall = false, levelUp;

    public static player player = new player();

    public int[][][][] fullMap = new int[5][5][][];

    //3 is top left, 1 is tree, 2 is top, 0 is sand, 4 is bottom 5 is right

    public static int[][] map = {
            {1, 1, 1, 1, 1, 1, 1, 3, 5, 1, 1, 1, 1, 1, 1, 1},
            {1, g, g, g, g, 6, 2, c, b, 2, 7, g, g, g, g, 1},
            {1, g, g, g, g, 3, 0, 0, 0, 0, 5, g, g, g, g, 1},
            {1, g, g, g, g, 3, 0, 0, 0, 0, 5, g, g, g, g, 1},
            {1, g, g, g, g, 3, 0, 0, 0, 0, 5, g, g, g, g, 1},
            {1, g, g, g, g, 3, 0, 0, 0, 0, 5, g, g, g, g, 1},
            {1, g, g, g, g, 3, 0, 0, 0, 0, 5, g, g, g, g, 1},
            {1, g, g, g, g, 8, 4, 4, 4, 4, a, g, g, g, g, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
    }, mapOne = {
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 6, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 7, 1},
            {1, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 1},
            {1, 3, 0, 0, 0, 0, 0, x, x, 0, 0, 0, 0, 0, 5, 1},
            {1, 3, 0, 0, 0, 0, 0, x, x, 0, 0, 0, 0, 0, 5, 1},
            {1, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 1},
            {1, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 1},
            {1, 8, 4, 4, 4, 4, 4, h, d, 4, 4, 4, 4, 4, a, 1},
            {1, 1, 1, 1, 1, 1, 1, 3, 5, 1, 1, 1, 1, 1, 1, 1}
    }, mapTwo = {
            {1, 1, 1, 1, 1, 1, 1, 3, 5, 1, 1, 1, 1, 1, 1, 1},
            {1, g, g, g, g, 6, 2, c, b, 2, 7, g, g, g, g, 1},
            {1, g, g, y, g, 3, 0, 0, 0, 0, 5, g, g, g, g, 1},
            {y, g, g, g, g, 3, 0, 0, 0, 0, 5, g, g, g, g, 1},
            {y, g, g, g, g, 3, 0, 0, z, 0, 5, g, g, y, g, 1},
            {y, g, g, g, g, 3, z, 0, 0, 0, 5, g, g, g, g, 1},
            {1, g, g, g, g, 3, 0, 0, 0, 0, 5, g, g, g, g, 1},
            {1, g, g, g, g, 8, 4, 4, 4, 4, a, g, g, g, g, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
    }, mapThree = {
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, g, g, g, g, 6, 2, 2, 2, 2, 7, g, g, g, g, 1},
            {1, g, g, g, g, 3, 0, z, 0, 0, 5, g, g, g, g, 1},
            {1, g, g, y, g, 3, 0, 0, 0, 0, b, 2, 2, 2, 2, 2},
            {1, g, g, g, g, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {1, g, g, g, g, 3, 0, 0, z, 0, d, 4, 4, 4, 4, 4},
            {1, g, y, g, g, 3, z, 0, 0, 0, 5, g, g, g, g, 1},
            {1, g, g, g, g, 8, 4, h, d, 4, a, g, y, g, g, 1},
            {1, 1, 1, 1, 1, 1, 1, 3, 5, 1, 1, 1, 1, 1, 1, 1}
    }, mapFour = {
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, g, g, g, g, g, g, g, g, g, g, g, g, g, 9, 1},
            {1, g, g, g, g, g, g, g, g, g, g, g, g, g, g, 1},
            {2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 7, g, g, g, 1},
            {z, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, g, g, g, 1},
            {4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, a, g, g, g, 1},
            {1, g, g, g, g, g, g, g, g, g, g, g, g, g, g, 1},
            {1, g, g, g, g, g, g, g, g, g, g, g, g, g, g, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
    };

    public static Tile[][] tileMap = new Tile[map.length][map[0].length];

    public static void main(String[] args) {
        OverStoryMainGame constructor = new OverStoryMainGame();
    }

    public static void delay(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException ie) {

        }
    }

    public void initializeMap(int[][] map) {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] == 0) {
                    tileMap[i][j] = new Tile(true, new Rectangle(j * size, i * size, size, size), sand, "empty");
                } else if (map[i][j] == 1) {
                    tileMap[i][j] = new Tile(false, new Rectangle(j * size, i * size, size, size), tree, "wall");
                } else if (map[i][j] == z) {
                    tileMap[i][j] = new Tile(true, new Rectangle(j * size, i * size, size, size), skeleton, "enemy");
                } else if (map[i][j] == y) {
                    tileMap[i][j] = new Tile(true, new Rectangle(j * size, i * size, size, size), bat, "enemy");
                } else if (map[i][j] == x) {
                    tileMap[i][j] = new Tile(true, new Rectangle(j * size, i * size, size, size), boss, "boss");
                } else if (map[i][j] == 9) {
                    tileMap[i][j] = new Tile(true, new Rectangle(j * size, i * size, size, size), heart, "rest");
                } else if (map[i][j] == 2) {
                    tileMap[i][j] = new Tile(true, new Rectangle(j * size, i * size, size, size), transitionUp, "empty");
                } else if (map[i][j] == 3) {
                    tileMap[i][j] = new Tile(true, new Rectangle(j * size, i * size, size, size), transitionLeft, "empty");
                } else if (map[i][j] == 4) {
                    tileMap[i][j] = new Tile(true, new Rectangle(j * size, i * size, size, size), transitionDown, "empty");
                } else if (map[i][j] == 5) {
                    tileMap[i][j] = new Tile(true, new Rectangle(j * size, i * size, size, size), transitionRight, "empty");
                } else if (map[i][j] == 6) {
                    tileMap[i][j] = new Tile(true, new Rectangle(j * size, i * size, size, size), transitionTopLeft, "empty");
                } else if (map[i][j] == 7) {
                    tileMap[i][j] = new Tile(true, new Rectangle(j * size, i * size, size, size), transitionTopRight, "empty");
                } else if (map[i][j] == 8) {
                    tileMap[i][j] = new Tile(true, new Rectangle(j * size, i * size, size, size), transitionBottomLeft, "empty");
                } else if (map[i][j] == a) {
                    tileMap[i][j] = new Tile(true, new Rectangle(j * size, i * size, size, size), transitionBottomRight, "empty");
                } else if (map[i][j] == g) {
                    tileMap[i][j] = new Tile(true, new Rectangle(j * size, i * size, size, size), grass, "empty");
                } else if (map[i][j] == b) {
                    tileMap[i][j] = new Tile(true, new Rectangle(j * size, i * size, size, size), cornerBottomLeft, "empty");
                } else if (map[i][j] == c) {
                    tileMap[i][j] = new Tile(true, new Rectangle(j * size, i * size, size, size), cornerBottomRight, "empty");
                } else if (map[i][j] == d) {
                    tileMap[i][j] = new Tile(true, new Rectangle(j * size, i * size, size, size), cornerTopLeft, "empty");
                } else if (map[i][j] == h) {
                    tileMap[i][j] = new Tile(true, new Rectangle(j * size, i * size, size, size), cornerTopRight, "empty");
                }
            }
        }
    }

    /*
  __  __           _             __  __          _     _                   _
 |  \/  |   __ _  (_)  _ __     |  \/  |   ___  | |_  | |__     ___     __| |
 | |\/| |  / _` | | | | '_ \    | |\/| |  / _ \ | __| | '_ \   / _ \   / _` |
 | |  | | | (_| | | | | | | |   | |  | | |  __/ | |_  | | | | | (_) | | (_| |
 |_|  |_|  \__,_| |_| |_| |_|   |_|  |_|  \___|  \__| |_| |_|  \___/   \__,_|

     */

    public OverStoryMainGame() {
        fullMap[0][0] = map;
        fullMap[0][1] = mapOne;
        fullMap[1][1] = mapTwo;
        fullMap[1][2] = mapThree;
        fullMap[2][2] = mapFour;
        createFrame();
        initializeImages();
        initializeMap(fullMap[mapPosX][mapPosY]);
        introduction();
        while (true) {
            delay(2);
            checkCollisions();
            checkMovement();
        }
    }

    public void introduction() {
        start = true;
        delay(2000);
        start = false;
        secondStart = true;
    }

    public void checkCollisions() {
        if (mapPosX == 0 && mapPosY == 1 && player.getRect().y == 1080 - player.getRect().height && player.getLevel() == 1){
            cutscene = true;
            player.getRect().y--;
            player.getBottom().y--;
            player.getTop().y--;
            player.getLeft().y--;
            player.getRight().y--;
            moveUp = false;
        }
        if (player.getRect().y < 0 - player.getRect().height) {
            mapPosY++;
            initializeMap(fullMap[mapPosX][mapPosY]);
            player.getRect().y = 1079;
            player.getBottom().y = 1079 + player.getRect().height;
            player.getTop().y = 1079 - 1;
            player.getLeft().y = 1079;
            player.getRight().y = 1079;
        } else if (player.getRect().y > 1080) {
            mapPosY--;
            initializeMap(fullMap[mapPosX][mapPosY]);
            player.getRect().y = 0 - player.getRect().height;
            player.getBottom().y = 0;
            player.getTop().y = 0 - player.getRect().height;
            player.getLeft().y = 0 - player.getRect().height;
            player.getRight().y = 0 - player.getRect().height;
        } else if (player.getRect().x > 1920) {
            mapPosX++;
            initializeMap(fullMap[mapPosX][mapPosY]);
            player.getRect().x = 0 - player.getRect().width;
            player.getBottom().x = 0 - player.getRect().width;
            player.getTop().x = 0 - player.getRect().width;
            player.getLeft().x = 0 - player.getRect().width - 1;
            player.getRight().x = 0;
        } else if (player.getRect().x < 0 - player.getRect().width) {
            mapPosX--;
            initializeMap(fullMap[mapPosX][mapPosY]);
            player.getRect().x = 1920;
            player.getBottom().x = 1920;
            player.getTop().x = 1920;
            player.getLeft().x = 1920 - 1;
            player.getRight().x = 1920 + player.getRect().width;
        }
        for (int i = 0; i < tileMap.length; i++) {
            for (int j = 0; j < tileMap[i].length; j++) {
                if (tileMap[i][j].getType().equals("enemy") && player.getRect().intersects(tileMap[i][j].getSquare())) {
                    fight("enemy");
                    if (tileMap[i][j].getSprite().equals(skeleton)) {
                        tileMap[i][j].setSprite(sand);
                    } else {
                        tileMap[i][j].setSprite(grass);
                    }
                    tileMap[i][j].setType("empty");
                } else if (tileMap[i][j].getType().equals("boss") && player.getRect().intersects(tileMap[i][j].getSquare())) {
                    fullMap[0][1][3][15] = g;
                    fullMap[0][1][4][15] = g;
                    fullMap[0][1][5][15] = g;
                    fight("boss");
                }
                if (!tileMap[i][j].isPassable()) {
                    if (player.getRight().intersects(tileMap[i][j].getSquare())) {
                        player.getRect().x -= 2;
                        player.getBottom().x -= 2;
                        player.getTop().x -= 2;
                        player.getLeft().x -= 2;
                        player.getRight().x -= 2;
                    }
                    if (player.getLeft().intersects(tileMap[i][j].getSquare())) {
                        player.getRect().x += 2;
                        player.getBottom().x += 2;
                        player.getTop().x += 2;
                        player.getLeft().x += 2;
                        player.getRight().x += 2;
                    }
                    if (player.getTop().intersects(tileMap[i][j].getSquare())) {
                        player.getRect().y += 2;
                        player.getBottom().y += 2;
                        player.getTop().y += 2;
                        player.getLeft().y += 2;
                        player.getRight().y += 2;
                    }
                    if (player.getBottom().intersects(tileMap[i][j].getSquare())) {
                        player.getRect().y -= 2;
                        player.getBottom().y -= 2;
                        player.getTop().y -= 2;
                        player.getLeft().y -= 2;
                        player.getRight().y -= 2;
                    }
                }
            }
        }
    }

    public void checkMovement() {
        if (moveLeft) {
            player.getRect().x--;
            player.getTop().x--;
            player.getRight().x--;
            player.getBottom().x--;
            player.getLeft().x--;
        }
        if (moveRight) {
            player.getRect().x++;
            player.getTop().x++;
            player.getRight().x++;
            player.getBottom().x++;
            player.getLeft().x++;
        }
        if (moveDown) {
            player.getRect().y++;
            player.getTop().y++;
            player.getRight().y++;
            player.getBottom().y++;
            player.getLeft().y++;
        }
        if (moveUp) {
            player.getRect().y--;
            player.getTop().y--;
            player.getRight().y--;
            player.getBottom().y--;
            player.getLeft().y--;
        }
    }

    public void createFrame() {
        screen.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        screen.add(this);
        screen.addKeyListener(this);
        screen.setResizable(true);
        screen.setUndecorated(true);
        screen.setExtendedState(JFrame.MAXIMIZED_BOTH);
        screen.setVisible(true);

    }

    public void initializeImages() {
        try {
            titleScreenIntro = ImageIO.read(getClass().getResourceAsStream("Resources/Backgrounds/TitleScreenV.png")).getScaledInstance(1920, 1080, 0);
            titleScreenFull = ImageIO.read(getClass().getResourceAsStream("Resources/Backgrounds/TitleScreenVe.png")).getScaledInstance(1920, 1080, 0);
            skeleton = ImageIO.read(getClass().getResourceAsStream("Resources/Skeleton.png")).getScaledInstance(size, size, 0);
            bat = Toolkit.getDefaultToolkit().createImage(getClass().getResource("Resources/Bat_Idle.gif"));
            transitionUp = ImageIO.read(getClass().getResourceAsStream("Resources/Tileset/tileset_03.png")).getScaledInstance(size, size, 0);
            transitionDown = ImageIO.read(getClass().getResourceAsStream("Resources/Tileset/tileset_21.png")).getScaledInstance(size, size, 0);
            transitionLeft = ImageIO.read(getClass().getResourceAsStream("Resources/Tileset/tileset_12.png")).getScaledInstance(size, size, 0);
            transitionRight = ImageIO.read(getClass().getResourceAsStream("Resources/Tileset/tileset_14.png")).getScaledInstance(size, size, 0);
            transitionBottomLeft = ImageIO.read(getClass().getResourceAsStream("Resources/Tileset/tileset_20.png")).getScaledInstance(size, size, 0);
            transitionBottomRight = ImageIO.read(getClass().getResourceAsStream("Resources/Tileset/tileset_22.png")).getScaledInstance(size, size, 0);
            transitionTopLeft = ImageIO.read(getClass().getResourceAsStream("Resources/Tileset/tileset_02.png")).getScaledInstance(size, size, 0);
            transitionTopRight = ImageIO.read(getClass().getResourceAsStream("Resources/Tileset/tileset_04.png")).getScaledInstance(size, size, 0);
            sand = ImageIO.read(getClass().getResourceAsStream("Resources/Tileset/tileset_13.png")).getScaledInstance(size, size, 0);
            cornerBottomRight = ImageIO.read(getClass().getResourceAsStream("Resources/Tileset/tileset_00.png")).getScaledInstance(size, size, 0);
            cornerBottomLeft = ImageIO.read(getClass().getResourceAsStream("Resources/Tileset/tileset_01.png")).getScaledInstance(size, size, 0);
            cornerTopRight = ImageIO.read(getClass().getResourceAsStream("Resources/Tileset/tileset_10.png")).getScaledInstance(size, size, 0);
            cornerTopLeft = ImageIO.read(getClass().getResourceAsStream("Resources/Tileset/tileset_11.png")).getScaledInstance(size, size, 0);
            tree = ImageIO.read(getClass().getResourceAsStream("Resources/Tileset/tileset_18.png")).getScaledInstance(size, size, 0);
            grass = ImageIO.read(getClass().getResourceAsStream("Resources/Tileset/tileset_19.png")).getScaledInstance(size, size, 0);
            boss = Toolkit.getDefaultToolkit().createImage(getClass().getResource("Resources/Floaty_Dude.gif")).getScaledInstance(size * 2, size * 2, 0);
            slime = ImageIO.read(getClass().getResourceAsStream("Resources/Slime_idle.gif")).getScaledInstance(size, size, 0);
            hero = ImageIO.read(getClass().getResourceAsStream("Resources/Player.png")).getScaledInstance(size, size, 0);
            bigHero = ImageIO.read(getClass().getResourceAsStream("Resources/Player.png")).getScaledInstance(550, 550, 0);
            instructionsOne = ImageIO.read(getClass().getResourceAsStream("Resources/instructions0.png")).getScaledInstance(1920, 1080, 0);
            instructionsTwo = ImageIO.read(getClass().getResourceAsStream("Resources/instructions1.png")).getScaledInstance(1920, 1080, 0);
            instructionsThree = ImageIO.read(getClass().getResourceAsStream("Resources/instructions2.png")).getScaledInstance(1920, 1080, 0);
            arrowRight = ImageIO.read(getClass().getResourceAsStream("Resources/RightArrow.png")).getScaledInstance(50, 50, 0);
            arrowLeft = ImageIO.read(getClass().getResourceAsStream("Resources/LeftArrow.png")).getScaledInstance(50, 50, 0);
            pause = ImageIO.read(getClass().getResourceAsStream("Resources/Pause.png")).getScaledInstance(1920, 1080, 0);
            heart = ImageIO.read(getClass().getResourceAsStream("Resources/Tileset/Healing.png")).getScaledInstance(120, 120, 0);
            levelScreen = ImageIO.read(getClass().getResourceAsStream("Resources/Level_Up.png")).getScaledInstance(1920, 1080, 0);
            textOne = ImageIO.read(getClass().getResourceAsStream("Resources/BoxHighlights/sprite_0.png")).getScaledInstance(1800, 100, 0);
            textTwo = ImageIO.read(getClass().getResourceAsStream("Resources/BoxHighlights/sprite_1.png")).getScaledInstance(1800, 100, 0);
            textThree = ImageIO.read(getClass().getResourceAsStream("Resources/BoxHighlights/sprite_2.png")).getScaledInstance(1800, 100, 0);
            textFour = ImageIO.read(getClass().getResourceAsStream("Resources/BoxHighlights/DeathMessage.png")).getScaledInstance(1500, 195, 0);
            bubbleOne = ImageIO.read(getClass().getResourceAsStream("Resources/Backgrounds/sprite_0.png")).getScaledInstance(2000, 195, 0);
            bubbleTwo = ImageIO.read(getClass().getResourceAsStream("Resources/Backgrounds/sprite_1.png")).getScaledInstance(1500, 195, 0);
            victory = ImageIO.read(getClass().getResourceAsStream("Resources/Victory.png")).getScaledInstance(625, 275, 0);
            options = ImageIO.read(getClass().getResourceAsStream("Resources/TitlescreenOptions.png")).getScaledInstance(625, 225, 0);
        } catch (IOException ex) {
        }
    }

    public void fight(String enemy) {
        this.setVisible(false);
        OverstoryQuickTime battle = new OverstoryQuickTime(player, enemy);
        if (player.getHealth() <= 0) {
            die();
        } else {
            if (enemy.equals("Boss")){
                win();
            }
            player.setXp(battle.getPlayerStats().getXp());
            while (checkLevelUp()) {
                levelUp();
            }
        }
        battle.setVisible(false);
        this.setVisible(true);
    }

    public void win() {
        game = false;
        levelUp = false;
        start = false;
        secondStart = false;
        win = true;
    }

    public void die() {
        mapPosX = 2;
        mapPosY = 2;
        initializeMap(fullMap[mapPosX][mapPosY]);
        cutscene = true;
        player.setHealth(player.getMaxHealth());
        player.setXp(0);
        player.getRect().x = 1680;
        player.getRect().y = 120;
        player.setLeft(new Rectangle(player.getRect().x - 1, player.getRect().y, 1, player.getRect().height));
        player.setRight(new Rectangle(player.getRect().x + player.getRect().width, player.getRect().y, 1, player.getRect().height));
        player.setTop(new Rectangle(player.getRect().x, player.getRect().y - 1, player.getRect().width, 1));
        player.setBottom(new Rectangle(player.getRect().x, player.getRect().y + player.getRect().height, player.getRect().width, 1));
    }

    public boolean checkLevelUp() {
        if (player.getXp() >= player.getXpForLevelUp()) {
            levelUp = true;
            return true;
        }
        return false;
    }

    public void levelUp() {
        overWorld = false;
        while (checkLevelUp()) {
            player.setLevel(player.getLevel() + 1);
            player.setXp(player.getXp() - player.getXpForLevelUp());
            player.setMaxHealth(player.getMaxHealth() + 5);
            player.setStrength(player.getStrength() + 1);
            player.setSpeed(player.getSpeed() + 1);
        }
        delay(1);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (start)
            g.drawImage(titleScreenIntro, 0, 0, this);
        else if (secondStart) {
            g.drawImage(titleScreenFull, 0, 0, this);
            g.drawImage(options, 760, 720, this);
        } else if (firstInstruction) {
            g.drawImage(instructionsOne, 0, 0, this);
            g.drawImage(arrowRight, 1850, 1010, this);
            g.drawImage(arrowLeft, 10, 1010, this);
        } else if (secondInstruction) {
            g.drawImage(instructionsTwo, 0, 0, this);
            g.drawImage(arrowLeft, 10, 1010, this);
            g.drawImage(arrowRight, 1850, 1010, this);
        } else if (thirdInstruction) {
            g.drawImage(instructionsThree, 0, 0, this);
            g.drawImage(arrowLeft, 10, 1010, this);
        } else if (game) {
            if (overWorld) {
                bossDrawn = false;
                for (int i = 0; i < tileMap.length; i++) {
                    for (int j = 0; j < tileMap[i].length; j++) {
                        if (tileMap[i][j].getType().equals("enemy")) {
                            if (tileMap[i][j].getSprite().equals(skeleton)) {
                                g.drawImage(sand, tileMap[i][j].getSquare().x, tileMap[i][j].getSquare().y, this);
                            } else {
                                g.drawImage(grass, tileMap[i][j].getSquare().x, tileMap[i][j].getSquare().y, this);
                            }
                            g.drawImage(tileMap[i][j].getSprite(), tileMap[i][j].getSquare().x, tileMap[i][j].getSquare().y, this);
                        } else if (tileMap[i][j].getType().equals("boss") && !bossDrawn) {
                            for (int k = 0; k < 2; k++) {
                                for (int l = 0; l < 2; l++) {
                                    g.drawImage(sand, tileMap[i][j].getSquare().x + size * k, tileMap[i][j].getSquare().y + size * l, this);
                                }
                            }
                            g.drawImage(boss, tileMap[i][j].getSquare().x, tileMap[i][j].getSquare().y, this);
                            bossDrawn = true;
                        } else if (tileMap[i][j].getSprite().equals(heart)) {
                            g.drawImage(grass, tileMap[i][j].getSquare().x, tileMap[i][j].getSquare().y, this);
                            g.drawImage(tileMap[i][j].getSprite(), tileMap[i][j].getSquare().x, tileMap[i][j].getSquare().y, this);
                        } else if (tileMap[i][j].getSprite() != boss) {
                            g.drawImage(tileMap[i][j].getSprite(), tileMap[i][j].getSquare().x, tileMap[i][j].getSquare().y, this);
                        }
                    }
                }
                g.drawImage(hero, player.getRect().x, player.getRect().y, this);
                if (cutscene){
                    if (mapPosX == 0 && mapPosY == 0){
                        g.drawImage(bubbleTwo, 200, 800, this);
                        g.drawImage(textOne, 220, 820, this);
                    }
                    else if (mapPosX == 0 && mapPosY == 1){
                        g.drawImage(bubbleOne, 90, 800, this);
                        g.drawImage(textTwo, 130, 830, this);
                    }
                    else if (mapPosX == 2 && mapPosY == 2){
                        g.drawImage(textFour, 200, 800, this);
                    }
                }
            } else if (pauseScreen) {
                g.drawImage(pause, 0, 0, this);
                g.drawImage(bigHero, 340, 320, this);
                g.setColor(Color.WHITE);
                g.drawString(Integer.toString(player.getMaxHealth()), 1425, 460);
                g.drawString(Integer.toString(player.getStrength()), 1425, 620);
                g.drawString(Integer.toString(player.getSpeed()), 1425, 775);
            } else if (levelUp) {
                g.drawImage(levelScreen, 0, 0, this);
                g.drawImage(bigHero, 340, 320, this);
                g.setColor(Color.WHITE);
                g.drawString(Integer.toString(player.getMaxHealth()), 1425, 460);
                g.drawString(Integer.toString(player.getStrength()), 1425, 620);
                g.drawString(Integer.toString(player.getSpeed()), 1425, 775);
                g.setColor(Color.green);
                g.drawString(Integer.toString(player.getMaxHealth() + 20), 1725, 460);
                g.drawString(Integer.toString(player.getStrength() + 3), 1725, 620);
                g.drawString(Integer.toString(player.getSpeed() + 3), 1725, 775);
            }
        } else if (win){
            g.drawImage(bigHero, 340, 320, this);
            g.drawImage(victory, 360, 500, this);
        }
        repaint();
    }

    public void save() {
        String fileName = "playerInfo.txt";

        try {
            FileWriter fileWriter = new FileWriter(fileName);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            bufferedWriter.write("Level:");
            bufferedWriter.newLine();
            bufferedWriter.write(Integer.toString(player.getLevel()));
            bufferedWriter.newLine();
            bufferedWriter.write("XP:");
            bufferedWriter.newLine();
            bufferedWriter.write(Integer.toString(player.getXp()));
            bufferedWriter.newLine();
            bufferedWriter.write("X Position:");
            bufferedWriter.newLine();
            bufferedWriter.write(Integer.toString(player.getRect().x));
            bufferedWriter.newLine();
            bufferedWriter.write("Y Position:");
            bufferedWriter.newLine();
            bufferedWriter.write(Integer.toString(player.getRect().y));
            bufferedWriter.newLine();
            bufferedWriter.write("Health:");
            bufferedWriter.newLine();
            bufferedWriter.write(Integer.toString(player.getHealth()));
            bufferedWriter.newLine();
            bufferedWriter.write("Max Health:");
            bufferedWriter.newLine();
            bufferedWriter.write(Integer.toString(player.getMaxHealth()));
            bufferedWriter.newLine();
            bufferedWriter.write("Strength:");
            bufferedWriter.newLine();
            bufferedWriter.write(Integer.toString(player.getStrength()));
            bufferedWriter.newLine();
            bufferedWriter.write("Speed:");
            bufferedWriter.newLine();
            bufferedWriter.write(Integer.toString(player.getSpeed()));
            bufferedWriter.newLine();
            bufferedWriter.write("Map Position X:");
            bufferedWriter.newLine();
            bufferedWriter.write(Integer.toString(mapPosX));
            bufferedWriter.newLine();
            bufferedWriter.write("Map Position Y:");
            bufferedWriter.newLine();
            bufferedWriter.write(Integer.toString(mapPosY));

            bufferedWriter.close();
        } catch (IOException ioe) {
        }
    }

    public void load() {
        String fileName = "playerInfo.txt";

        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            bufferedReader.mark(1);
            if (bufferedReader.readLine() != null) {
                bufferedReader.reset();

                bufferedReader.readLine();
                player.setLevel(Integer.parseInt(bufferedReader.readLine()));
                bufferedReader.readLine();
                player.setXp(Integer.parseInt(bufferedReader.readLine()));
                bufferedReader.readLine();
                player.getRect().x = Integer.parseInt(bufferedReader.readLine());
                bufferedReader.readLine();
                player.getRect().y = Integer.parseInt(bufferedReader.readLine());
                bufferedReader.readLine();
                player.setLeft(new Rectangle(player.getRect().x - 1, player.getRect().y, 1, player.getRect().height));
                player.setRight(new Rectangle(player.getRect().x + player.getRect().width, player.getRect().y, 1, player.getRect().height));
                player.setTop(new Rectangle(player.getRect().x, player.getRect().y - 1, player.getRect().width, 1));
                player.setBottom(new Rectangle(player.getRect().x, player.getRect().y + player.getRect().height, player.getRect().width, 1));
                player.setHealth(Integer.parseInt(bufferedReader.readLine()));
                bufferedReader.readLine();
                player.setMaxHealth(Integer.parseInt(bufferedReader.readLine()));
                bufferedReader.readLine();
                player.setStrength(Integer.parseInt(bufferedReader.readLine()));
                bufferedReader.readLine();
                player.setSpeed(Integer.parseInt(bufferedReader.readLine()));
                bufferedReader.readLine();
                mapPosX = Integer.parseInt(bufferedReader.readLine());
                bufferedReader.readLine();
                mapPosY = Integer.parseInt(bufferedReader.readLine());
                bufferedReader.close();
            }
        } catch (FileNotFoundException e) {

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_ESCAPE) {
            save();
            System.exit(-1);
        } else if (secondStart) {
            if (key == KeyEvent.VK_Z) {
                game = true;
                overWorld = true;
                secondStart = false;
                levelUp = false;
                load();
                initializeMap(fullMap[mapPosX][mapPosY]);
            } else if (key == KeyEvent.VK_X) {
                game = true;
                overWorld = true;
                cutscene = true;
                secondStart = false;
                levelUp = false;
            } else if (key == KeyEvent.VK_C) {
                firstInstruction = true;
                secondStart = false;
            }
        } else if (firstInstruction) {
            if (key == KeyEvent.VK_Z) {
                firstInstruction = false;
                secondInstruction = true;
                secondStart = false;
            } else if (key == KeyEvent.VK_X) {
                firstInstruction = false;
                secondStart = true;
            }
        } else if (secondInstruction) {
            if (key == KeyEvent.VK_Z){
                secondInstruction = false;
                thirdInstruction = true;
            }
            else if (key == KeyEvent.VK_X) {
                firstInstruction = true;
                secondInstruction = false;
                secondStart = false;
            }
        } else if (thirdInstruction) {
            if (key == KeyEvent.VK_X) {
                secondInstruction = true;
                thirdInstruction = false;
                secondStart = false;
            }
        } else if (game) {
            if (overWorld) {
                if (!cutscene) {
                    if (key == KeyEvent.VK_DOWN && !intersectingWall) {
                        moveDown = true;
                    } else if (key == KeyEvent.VK_UP && !intersectingWall) {
                        moveUp = true;
                    } else if (key == KeyEvent.VK_LEFT && !intersectingWall) {
                        moveLeft = true;
                    } else if (key == KeyEvent.VK_RIGHT && !intersectingWall) {
                        moveRight = true;
                    } else if (key == KeyEvent.VK_C) {
                        overWorld = false;
                        pauseScreen = true;
                    }
                } else {
                    if (key == KeyEvent.VK_Z) {
                        cutscene = false;
                    }
                }
            } else if (pauseScreen) {
                if (key == KeyEvent.VK_X) {
                    pauseScreen = false;
                    overWorld = true;
                }
            } else if (levelUp) {
                if (key == KeyEvent.VK_X) {
                    player.setStrength(player.getStrength() + 2);
                    levelUp = false;
                    overWorld = true;
                } else if (key == KeyEvent.VK_C) {
                    player.setSpeed(player.getSpeed() + 2);
                    levelUp = false;
                    overWorld = true;
                } else if (key == KeyEvent.VK_Z) {
                    player.setMaxHealth(player.getMaxHealth() + 15);
                    levelUp = false;
                    overWorld = true;
                }
            }
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_DOWN) {
            moveDown = false;
        }
        if (key == KeyEvent.VK_UP) {
            moveUp = false;
        }
        if (key == KeyEvent.VK_LEFT) {
            moveLeft = false;
        }
        if (key == KeyEvent.VK_RIGHT) {
            moveRight = false;
        }
    }
}
