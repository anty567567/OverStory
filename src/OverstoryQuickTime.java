import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Timer;

public class OverstoryQuickTime extends JPanel implements KeyListener {

    private ArrayList<Projectile> enemyProjectiles1 = new ArrayList<>();
    private ArrayList<Projectile> projectiles2 = new ArrayList<>();
    private ArrayList<Projectile> projectiles3 = new ArrayList<>();
    private ArrayList<Projectile> enemyHeals = new ArrayList<>();
    private player playerStats;
    private JFrame screen = new JFrame();

    private int healthPotions = 0;
    private int swiftnessPotions = 0;
    private int strengthPotions = 0;

    private boolean run = false;


    private Projectile characterDefending = new Projectile(1430, 980, new Rectangle(1430, 980, 20, 20));
    private int counter = 0, xpGained = 0;

    //Needle
    private Projectile character = new Projectile(520, 440, new Rectangle(520, 855, 8, 90));

    //Menu System
    private int yFightPos = 1, xFightPos = 1;
    private int enemyPotion = 0;
    private double damageMultiplier = 0;
    private double accum = 0.5;

    //Run Integer
    private int runClicks = 0;
    private int runClickMax = 0;
    private int numberEnemies = 0;

    //Time for running
    private int time = 0;

    //Enemy attack velocities
    private int enemyXVelocity = 1;
    private int enemyYVelocity = -1;

    //Combat Bar
    private Rectangle hitRect = new Rectangle(520, 850, 900, 100);
    private Image Top_Left_Highlighted, Heart, text, Top_Right_Highlighted, Bottom_Left_Highlighted, Bottom_Right_Highlighted, smallSkeleton, smallBat, smallBoss, bubbleOne, Boss, Arrow, Battle_Options_Fight_Highlight, Battle_Options_Run_Highlight, Battle_Option_Items_Highlight, Battle_Option_Defend_Highlight, Background, Skeleton, Bat_Idle, Axe, Bow, CryoStaff, LongSword, Player, SwiftnessPotion, StrengthPotion, HealthPotion;
    private boolean start, godMode = false, canDamagePlayer = false, fight = false, bossFight = false, heals = false, playerAlive, attackFinished = false, playerHealthBar = false, boomerangSlam = false, butterscotchChipitDeluxe = false, bungHoleTickler = false, skeletonAlive = false, batAlive = false, drawBackground = false, moveLeftAttack = false, moveRightAttack = false, moveLeftDefend = false, moveRightDefend = false, moveUpDefend = false, moveDownDefend = false, inItems = false, defend = false, isClicked = false, chooseEnemy = false, allowed = false, attackSkeleton = false, attackBat = false, chooseWeapon = false, longsword = false, bow = false, axe = false, cryoStaff = false, skeletonHealthBar = false, batHealthBar = false, attackChosen = false, engaged = true;

    private int attacking;

    OverstoryQuickTime(player playerStats, String enemy) {
        this.playerStats = playerStats;
        if (enemy.equals("boss")) {
            bossFight = true;
        }
        initializeImages();
        createFrame();
        enemySpawn(enemy);
        start = true;
        playerAlive = true;
        drawBackground = true;
        playerHealthBar = true;

        while (engaged) {
            delay(1);
            if (checkWin()) {
                winScreen();
            }
            while (fight && playerAlive) {
                delay(1);
                movement();
                checkEnemyHealth();
                checkPlayerHealth();
            }
            character.getRect().x = 520;
            while (defend && playerAlive) {
                //Changing delay changes the apparent speed of the character
                if (!attackChosen) {
                    decideEnemyAttack((int) (Math.random() * 3 + 1));
                }
                delay(2);
                checkCollide();
                checkCollisionProjectiles();
                defenciveMovement();
                enemyAttackMovement();
                checkPlayerHealth();
                damageBoost();
            }
            boomerangSlam = false;
            butterscotchChipitDeluxe = false;
            bungHoleTickler = false;
            attackFinished = false;
            attackChosen = false;
            enemyProjectiles1.clear();
            projectiles2.clear();
            projectiles3.clear();
            heals = false;
            enemyHeals.clear();
            while (chooseEnemy) {
                delay(1);
                if (!engaged) {
                    chooseEnemy = false;
                }
            }
        }
        screen.setVisible(false);
    }

    private void decideEnemyAttack(int attackChoice) {
        attackChosen = true;
        if (attackChoice == 1) {
            boomerangSlam = true;
            initializeAttack(1);
        }
        if (attackChoice == 2) {
            butterscotchChipitDeluxe = true;
            initializeAttack(2);
        }
        if (attackChoice == 3) {
            bungHoleTickler = true;
            initializeAttack(3);
        }

    }

    private void initializeAttack(int attack) {
        if (attack == 1) {
            enemyProjectiles1.add(new Projectile(0, 760, new Rectangle(0, 760, 10, 200)));
        } else if (attack == 2) {
            for (int i = 0; i < 100; i++) {
                enemyProjectiles1.add(new Projectile(0, 760, new Rectangle((int) (Math.random() * 2000 - 2000), (int) (Math.random() * 235 + 770), 3, 3)));
            }
        } else if (attack == 3) {
            for (int i = 0; i < 15; i++) {
                enemyProjectiles1.add(new Projectile(0, 760, new Rectangle(-i * 300, 750, 5, 120)));
                projectiles2.add(new Projectile(0, 760, new Rectangle(1920 + i * 300, 880, 5, 120)));
            }
            for (int i = 0; i < 7; i++) {
                projectiles3.add(new Projectile(0, 760, new Rectangle(2420 + i * 600, 750, 5, 250)));
            }
        }
        if (heals) {
            for (int i = 0; i < 9; i++) {
                enemyHeals.add(new Projectile(0, 760, new Rectangle((int) (Math.random() * 600 - 650), (int) (Math.random() * 235 + 770), 3, 3)));
            }
        }
    }

    private ArrayList<Enemy> enemies = new ArrayList<>();

    private void enemySpawn(String enemy) {
        if (enemy.equals("enemy")) {
            for (int i = (int) (Math.random() * 4 + 1); i > 0; i--) {
                numberEnemies++;
                int randomEnemyChoice = (int) (Math.random() * 2 + 1);
                if (randomEnemyChoice == 1) {
                    enemies.add(new Enemy("skeleton", playerStats.getLevel(), Skeleton, new Rectangle(0, 0, 30, 30), smallSkeleton));
                } else if (randomEnemyChoice == 2) {
                    enemies.add(new Enemy("bat", playerStats.getLevel(), Bat_Idle, new Rectangle(0, 0, 30, 30), smallBat));
                }
            }
            for (int i = 4 - enemies.size(); i > 0; i--) {
                enemies.add(new Enemy(Bat_Idle));
            }
        } else if (enemy.equals("boss")) {
            enemies.add(new Enemy("boss", 100, Boss, new Rectangle(0, 0, 30, 30), smallBoss));
            enemies.add(new Enemy("skeleton", playerStats.getLevel(), Skeleton, new Rectangle(0, 0, 30, 30), smallSkeleton));
            enemies.add(new Enemy("bat", playerStats.getLevel(), Bat_Idle, new Rectangle(0, 0, 30, 30), smallBat));
            enemies.add(new Enemy("bat", playerStats.getLevel(), Bat_Idle, new Rectangle(0, 0, 30, 30), smallBat));
        }
    }

    private void enemyAttackMovement() {
        delay(1);
        if (enemyProjectiles1.get(enemyProjectiles1.size() - 1).getRect().x > 1920) {
            defend = false;
            time = 0;
            xFightPos = 1;
            yFightPos = 1;
            allowed = false;
            start = true;
        }
        if (boomerangSlam) {
            for (int i = 0; i < enemyProjectiles1.size(); i++) {
                enemyProjectiles1.get(i).getRect().x += enemyXVelocity;
                enemyProjectiles1.get(i).getRect().y += enemyYVelocity;

            }
            if (enemyProjectiles1.get(0).getRect().y > 850) {
                enemyYVelocity = -enemyYVelocity;
            }
            if (enemyProjectiles1.get(0).getRect().y < 750) {
                enemyYVelocity = -enemyYVelocity;
            }
            if (enemyProjectiles1.get(0).getRect().x > 1140 && !attackFinished) {
                enemyXVelocity = -enemyXVelocity;
            }
            if (enemyProjectiles1.get(0).getRect().x < 750 && enemyXVelocity == -1) {
                enemyXVelocity = -enemyXVelocity;
                attackFinished = true;
            }
        }
        if (butterscotchChipitDeluxe) {
            for (int i = 0; i < enemyProjectiles1.size(); i++) {
                enemyProjectiles1.get(i).getRect().x += enemyXVelocity;
            }
        }
        if (bungHoleTickler) {
            for (int i = 0; i < enemyProjectiles1.size(); i++) {
                enemyProjectiles1.get(i).getRect().x += enemyXVelocity;
            }
            for (int i = 0; i < projectiles2.size(); i++) {
                projectiles2.get(i).getRect().x -= enemyXVelocity;
            }
            for (int i = 0; i < projectiles3.size(); i++) {
                projectiles3.get(i).getRect().x -= enemyXVelocity;
            }
        }
        if (heals) {
            for (int i = 0; i < enemyHeals.size(); i++) {
                enemyHeals.get(i).getRect().x++;
            }
        }
    }

    private void checkPlayerHealth() {
        if (playerStats.getHealth() <= 0) {
            lossScreen();
        }
    }

    private void checkEnemyHealth() {
        for (int i = 0; i < enemies.size(); i++) {
            if (enemies.get(i).getHealth() <= 0 && enemies.get(i).isAlive()) {
                killEnemy(enemies.get(i));
            }
            if (enemies.get(i).getHealth() > enemies.get(i).getMaxHealth()) {
                enemies.get(i).setHealth(enemies.get(i).getMaxHealth());
            }
        }
    }

    private void killEnemy(Enemy deadEnemy) {
        deadEnemy.setAlive(false);
        xpGained += deadEnemy.getXpGivenOnDeath();
    }

    private boolean checkWin() {
        for (int i = 0; i < enemies.size(); i++) {
            if (enemies.get(i).isAlive()) {
                return false;
            }
        }
        return true;
    }

    private void attemptRun() {
        //Code time
        if (runClickMax >= 890 && time < 890) {
            runScreen();
        }
    }

    private void lossScreen() {
        playerAlive = false;
        engaged = false;
    }

    private void winScreen() {
        playerStats.setXp(playerStats.getXp() + xpGained);
        engaged = false;
    }

    private void runScreen() {
        engaged = false;
    }

    private void createFrame() {
        screen.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        screen.add(this);
        screen.addKeyListener(this);
        screen.setResizable(true);
        screen.setUndecorated(true);
        screen.setExtendedState(JFrame.MAXIMIZED_BOTH);
        screen.setVisible(true);
    }

    private void initializeImages() {
        try {

            //Player
            Player = ImageIO.read(getClass().getResourceAsStream("Resources/Player.png")).getScaledInstance(220, 220, Image.SCALE_SMOOTH);

            //CombatUI
            Battle_Options_Fight_Highlight = ImageIO.read(getClass().getResourceAsStream("Resources/CombatUI/Battle_Options_Fight_Highlight.png")).getScaledInstance(900, 520, Image.SCALE_SMOOTH);
            Battle_Options_Run_Highlight = ImageIO.read(getClass().getResourceAsStream("Resources/CombatUI/Battle_Options_Run_Highlight.png")).getScaledInstance(900, 520, Image.SCALE_SMOOTH);
            Battle_Option_Items_Highlight = ImageIO.read(getClass().getResourceAsStream("Resources/CombatUI/Battle_Options_Items_Highlight.png")).getScaledInstance(900, 520, Image.SCALE_SMOOTH);
            Battle_Option_Defend_Highlight = ImageIO.read(getClass().getResourceAsStream("Resources/CombatUI/Battle_Options_Defend_Highlight.png")).getScaledInstance(900, 520, Image.SCALE_SMOOTH);
            Arrow = ImageIO.read(getClass().getResourceAsStream("Resources/CombatUI/Arrow.png")).getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            Heart = ImageIO.read(getClass().getResourceAsStream("Resources/CombatUI/Heart.png")).getScaledInstance(25, 25, Image.SCALE_SMOOTH);
            HealthPotion = ImageIO.read(getClass().getResourceAsStream("Resources/HealthPotion.png")).getScaledInstance(60, 50, Image.SCALE_SMOOTH);
            SwiftnessPotion = ImageIO.read(getClass().getResourceAsStream("Resources/SwiftnessPotion.png")).getScaledInstance(50, 65, Image.SCALE_SMOOTH);
            StrengthPotion = ImageIO.read(getClass().getResourceAsStream("Resources/StrengthPotion.png")).getScaledInstance(50, 40, Image.SCALE_SMOOTH);
            text = ImageIO.read(getClass().getResourceAsStream("Resources/BoxHighlights/sprite_2.png")).getScaledInstance(560, 50, 0);
            //Backgrounds
            Background = ImageIO.read(getClass().getResourceAsStream("Resources/Backgrounds/Background.png")).getScaledInstance(2000, 750, Image.SCALE_SMOOTH);

            //BoxHighlights
            Top_Left_Highlighted = ImageIO.read(getClass().getResourceAsStream("Resources/BoxHighlights/Top_Left_Highlighted.png")).getScaledInstance(675, 280, Image.SCALE_SMOOTH);
            Top_Right_Highlighted = ImageIO.read(getClass().getResourceAsStream("Resources/BoxHighlights/Top_Right_Highlighted.png")).getScaledInstance(675, 280, Image.SCALE_SMOOTH);
            Bottom_Left_Highlighted = ImageIO.read(getClass().getResourceAsStream("Resources/BoxHighlights/Bottom_Left_Highlighted.png")).getScaledInstance(675, 280, Image.SCALE_SMOOTH);
            Bottom_Right_Highlighted = ImageIO.read(getClass().getResourceAsStream("Resources/BoxHighlights/Bottom_Right_Highlighted.png")).getScaledInstance(675, 280, Image.SCALE_SMOOTH);
            bubbleOne = ImageIO.read(getClass().getResourceAsStream("Resources/Backgrounds/sprite_0.png")).getScaledInstance(230, 100, 0);
            //Enemies
            Skeleton = ImageIO.read(getClass().getResourceAsStream("Resources/Skeleton.png")).getScaledInstance(300, 250, Image.SCALE_SMOOTH);
            smallSkeleton = ImageIO.read(getClass().getResourceAsStream("Resources/Skeleton.png")).getScaledInstance(70, 70, Image.SCALE_DEFAULT);
            Bat_Idle = Toolkit.getDefaultToolkit().createImage(getClass().getResource("Resources/Bat_Idle.gif")).getScaledInstance(200, 150, Image.SCALE_DEFAULT);
            smallBat = ImageIO.read(getClass().getResourceAsStream("Resources/Bat_Idle.gif")).getScaledInstance(70, 70, Image.SCALE_DEFAULT);
            Boss = Toolkit.getDefaultToolkit().createImage(getClass().getResource("Resources/Floaty_Dude.gif")).getScaledInstance(500, 500, Image.SCALE_DEFAULT);
            smallBoss = ImageIO.read(getClass().getResourceAsStream("Resources/Floaty_Dude.gif")).getScaledInstance(70, 70, Image.SCALE_DEFAULT);
            //Weapons
            Axe = ImageIO.read(getClass().getResourceAsStream("Resources/Weapons/Axe.png")).getScaledInstance(130, 80, Image.SCALE_SMOOTH);
            Bow = ImageIO.read(getClass().getResourceAsStream("Resources/Weapons/Bow.png")).getScaledInstance(130, 80, Image.SCALE_SMOOTH);
            CryoStaff = ImageIO.read(getClass().getResourceAsStream("Resources/Weapons/CryoStaff.png")).getScaledInstance(130, 80, Image.SCALE_SMOOTH);
            LongSword = ImageIO.read(getClass().getResourceAsStream("Resources/Weapons/LongSword.png")).getScaledInstance(130, 80, Image.SCALE_SMOOTH);
        } catch (IOException ex) {
        }
    }

    private int[][] enemyPositions = {{1400, 450}, {1520, 400}, {1640, 430}, {1760, 410}};

    protected void paintComponent(Graphics g) {
        if (playerAlive) {
            super.paintComponents(g);
            g.clearRect(900, 500, 1000, 1000);
            g.setColor(Color.BLACK);
            g.fillRect(1, 750, 1950, 400);

            if (drawBackground) {
                g.drawImage(Background, 1, 1, this);
            }

            if (playerAlive) {
                g.drawImage(Player, 440, 480, this);
            }

            if (playerHealthBar) {
                g.setColor(Color.BLACK);
                g.fillRect(475, 710, 110, 20);
                g.setColor(Color.RED);
                g.fillRect(480, 715, (int) (100 * (playerStats.getHealth() / (double) (playerStats.getMaxHealth()))), 10);
            }

            for (int i = 0; i < enemies.size(); i++) {
                int[] pos = enemyPositions[i];
                if (enemies.get(i).isAlive()) {
                    if (enemies.get(i).getImage().equals(Boss)) {
                        g.drawImage(Boss, pos[0] - 200, pos[1] - 300, this);
                        g.drawImage(bubbleOne, 1150, 100, this);
                        g.drawImage(text, 1160, 120, this);
                    } else {
                        g.drawImage(enemies.get(i).getImage(), pos[0], pos[1], this);
                    }
                    if (engaged) {
                        g.setColor(Color.BLACK);
                        if (enemies.get(i).getImage().equals(Boss)) {
                            g.fillRect(pos[0], pos[1] + 260, 110, 20);
                        } else {
                            g.fillRect(pos[0] + 60, pos[1] + 260, 110, 20);
                        }
                        g.setColor(Color.RED);
                        if (enemies.get(i).getImage().equals(Boss)) {
                            g.fillRect(pos[0] + 5, pos[1] + 265, (int) (100 * (enemies.get(i).getHealth() / (double) (enemies.get(i).getMaxHealth()))), 10);
                        } else {
                            g.fillRect(pos[0] + 65, pos[1] + 265, (int) (100 * (enemies.get(i).getHealth() / (double) (enemies.get(i).getMaxHealth()))), 10);
                        }
                    }
                }
            }

            if (start) {
                if (xFightPos == 1 && yFightPos == 1) {
                    g.drawImage(Battle_Options_Fight_Highlight, 530, 590, this);
                }

                if (xFightPos == 2 && yFightPos == 1) {
                    g.drawImage(Battle_Option_Items_Highlight, 530, 590, this);
                }

                if (xFightPos == 1 && yFightPos == 2) {
                    g.drawImage(Battle_Option_Defend_Highlight, 530, 590, this);
                }

                if (xFightPos == 2 && yFightPos == 2) {
                    g.drawImage(Battle_Options_Run_Highlight, 530, 590, this);
                }
            }

            float pct = 0;
            if (hitRect.contains(character.getRect())) {
                float x = (float) character.getRect().getCenterX() - hitRect.x;
                float cx = (float) hitRect.getCenterX() - hitRect.x;
                if (x < cx) {
                    pct = x / cx;
                }
                if (x > cx) {
                    pct = 1 - (x - cx) / cx;
                }
            }

            //Border
            if (chooseEnemy) {
                if (xFightPos == 1 && yFightPos == 1) {
                    g.drawImage(Top_Left_Highlighted, 655, 765, this);
                    if (enemies.get(0).getImage().equals(Boss)) {
                        g.drawImage(Arrow, 1430, 150, 40, 40, this);
                    } else if (enemies.get(0).isAlive()) {
                        g.drawImage(Arrow, 1470, 420, 40, 40, this);
                    }
                } else if (xFightPos == 2 && yFightPos == 1) {
                    g.drawImage(Top_Right_Highlighted, 655, 765, this);
                    if (enemies.get(1).isAlive()) {
                        g.drawImage(Arrow, 1600, 400, 40, 40, this);
                    }

                } else if (xFightPos == 1 && yFightPos == 2) {
                    g.drawImage(Bottom_Left_Highlighted, 655, 765, this);
                    if (enemies.get(2).isAlive())
                        g.drawImage(Arrow, 1730, 430, 40, 40, this);

                } else if (xFightPos == 2 && yFightPos == 2) {
                    g.drawImage(Bottom_Right_Highlighted, 655, 765, this);
                    if (enemies.get(3).isAlive())
                        g.drawImage(Arrow, 1860, 400, 40, 40, this);
                }
                for (int j = 0; j < enemies.size(); j++) {
                    if (enemies.get(j).isAlive()) {
                        g.drawImage(enemies.get(j).getSmallImage(), 820 + 260 * (j % 2), 808 + 78 * (j / 2), this);
                    }
                }
            }

            if (chooseWeapon) {
                //Draw the 4 rectangles with the choices
                if (attackSkeleton) {
                    g.drawImage(Arrow, 1490, 430, this);
                }
                if (attackBat) {
                    g.drawImage(Arrow, 1590, 350, this);
                }
                if (xFightPos == 1 && yFightPos == 1) {
                    g.drawImage(Top_Left_Highlighted, 655, 765, this);
                }

                if (xFightPos == 2 && yFightPos == 1) {
                    g.drawImage(Top_Right_Highlighted, 655, 765, this);
                }

                if (xFightPos == 1 && yFightPos == 2) {
                    g.drawImage(Bottom_Left_Highlighted, 655, 765, this);
                }

                if (xFightPos == 2 && yFightPos == 2) {
                    g.drawImage(Bottom_Right_Highlighted, 655, 765, this);
                }
                g.drawImage(LongSword, 785, 802, this);
                g.drawImage(Bow, 1045, 810, this);
                g.drawImage(Axe, 785, 885, this);
                g.drawImage(CryoStaff, 1045, 885, this);
            }

            if (fight) {
                g.setColor(Color.getHSBColor(25, 25, 25));
                g.fillRect(hitRect.x - 5, hitRect.y - 5, hitRect.width + 10, hitRect.height + 10);

                g.setColor(new Color((1 - pct), pct, 0f));
                g.fillRect(hitRect.x, hitRect.y, hitRect.width, hitRect.height);

                g.setColor(Color.black);
                g.fillRect(character.getRect().x, character.getRect().y, 8, 90);
            }

            if (defend) {
                g.setColor(Color.getHSBColor(25, 25, 25));
                g.drawImage(Heart, characterDefending.getRect().x, characterDefending.getRect().y, this);

                for (int j = 0; j < enemyProjectiles1.size(); j++) {
                    g.fillRect(enemyProjectiles1.get(j).getRect().x, enemyProjectiles1.get(j).getRect().y, enemyProjectiles1.get(j).getRect().width, enemyProjectiles1.get(j).getRect().height);
                    if (bungHoleTickler) {
                        g.fillRect(projectiles2.get(j).getRect().x, projectiles2.get(j).getRect().y, projectiles2.get(j).getRect().width, projectiles2.get(j).getRect().height);
                    }
                }

                if (bungHoleTickler) {
                    g.setColor(Color.BLUE);
                    for (int j = 0; j < projectiles3.size(); j++) {
                        g.fillRect(projectiles3.get(j).getRect().x, projectiles3.get(j).getRect().y, projectiles3.get(j).getRect().width, projectiles3.get(j).getRect().height);
                    }
                }

                g.setColor(Color.green);
                for (int k = 0; k < enemyHeals.size(); k++)
                    g.fillRect(enemyHeals.get(k).getRect().x, enemyHeals.get(k).getRect().y, enemyHeals.get(k).getRect().width, enemyHeals.get(k).getRect().height);

                g.setColor(Color.getHSBColor(25, 25, 25));
                g.drawRect(750, 770, 410, 240);
            }

            if (inItems) {
                if (xFightPos == 1 && yFightPos == 1) {
                    g.drawImage(Top_Left_Highlighted, 663, 765, this);
                }

                if (xFightPos == 2 && yFightPos == 1) {
                    g.drawImage(Top_Right_Highlighted, 663, 765, this);
                }

                if (xFightPos == 1 && yFightPos == 2) {
                    g.drawImage(Bottom_Left_Highlighted, 663, 765, this);
                }

                if (xFightPos == 2 && yFightPos == 2) {
                    g.drawImage(Bottom_Right_Highlighted, 663, 765, this);
                }
                g.drawImage(HealthPotion, 820, 820, this);
                g.drawImage(SwiftnessPotion, 1090, 815, this);
                g.drawImage(StrengthPotion, 825, 905, this);
            }

            if (run) {
                runClickMax = runClicks * (playerStats.getSpeed() - numberEnemies * enemies.get(0).getSpeed());
                attemptRun();
                //Time trial rectangle
                g.setColor(Color.getHSBColor(25, 25, 25));
                g.fillRect(520, 810, 900, 70);
                g.setColor(Color.BLACK);
                g.fillRect(525, 815, time, 60);

                //Clicking Rectangle
                g.setColor(Color.getHSBColor(25, 25, 25));
                g.fillRect(520, 900, 900, 70);
                g.setColor(Color.BLACK);
                g.fillRect(525, 905, runClickMax, 60);
                if (time < 890) {
                    delay(5);
                    time++;
                    if (time == 890) {
                        run = false;
                        start = true;
                        time = 0;
                        runClicks = 0;
                    }
                }
            }
            if (playerAlive) {
                repaint();
            }
        }
    }

    private void checkClicked() {
        //This is where you deal the damage dealt to the Enemy
        int statusEffectDamage = 0;
        if (attacking > -1 && attacking < 4) {
            int damage = 0;
            if (longsword) {
                damage = (int) (playerStats.getAbility1Damage() * damageMultiplier);
            } else if (bow) {
                damage = (int) (playerStats.getAbility2Damage() * damageMultiplier);
            } else if (axe) {
                damage = (int) (playerStats.getAbility3Damage() * damageMultiplier);
            } else if (cryoStaff) {
                if (playerStats.getStatusEffectChance() == 1) {
                    statusEffectDamage = playerStats.getStatusEffectDamage();
                }
                damage = (int) ((playerStats.getAbility4Damage() * damageMultiplier) + statusEffectDamage);
            }

            enemies.get(attacking).setHealth(enemies.get(attacking).getHealth() - damage);
            checkEnemyHealth();
        }
        attacking = -1;
        isClicked = true;
        fight = false;
        canDamagePlayer = true;
        characterDefending.getRect().x = 940;
        characterDefending.getRect().y = 875;
        defend = true;

        /*
        int randomEnemyChoice = (int) (Math.random() * 2 + 1);

        if (randomEnemyChoice == 1 && enemyPotion < 5) {
            for (Enemy e : enemies) {
                if (e.getHealth() < e.getMaxHealth()) {
                    e.setHealth(e.getHealth() + 15);
                    enemyPotion++;
                }
            }
            start = true;
        } else {
            canDamagePlayer = true;
            characterDefending.getRect().x = 940;
            characterDefending.getRect().y = 875;
            defend = true;
        }*/


        delay(800);
        checkPlayerHealth();
    }

    private void delay(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException ie) {
        }
    }


    private void movement() {
        if (character.getRect().x == 520) {
            damageMultiplier = 0.5;
            moveLeftAttack = false;
            moveRightAttack = true;
        }

        if (character.getRect().x > 520 && character.getRect().x < 800) {
            if (moveRightAttack) {
                damageMultiplier = accum += 0.0010;
            }
            if (moveLeftAttack) {
                damageMultiplier = accum -= 0.0010;
            }
        }

        if (character.getRect().x > 800 && character.getRect().x < 960) {
            if (moveRightAttack) {
                damageMultiplier = accum += 0.0040;
            }
            if (moveLeftAttack) {
                damageMultiplier = accum -= 0.0040;
            }
        }

        if (character.getRect().x > 960 && character.getRect().x < 1120) {
            if (moveRightAttack) {
                damageMultiplier = accum -= 0.0040;
            }
            if (moveLeftAttack) {
                damageMultiplier = accum += 0.0040;
            }
        }

        if (character.getRect().x > 1120 && character.getRect().x < 1410) {
            if (moveRightAttack) {
                damageMultiplier = accum -= 0.0010;
            }
            if (moveLeftAttack) {
                damageMultiplier = accum += 0.0010;
            }
        }

        if (character.getRect().x == 1410) {
            damageMultiplier = 0.5;
            moveRightAttack = false;
            moveLeftAttack = true;
        }

        if (moveRightAttack) {
            character.getRect().x += 1;
        }

        if (moveLeftAttack) {
            character.getRect().x -= 1;
        }
    }

    private void defenciveMovement() {
        if (moveLeftDefend) {
            characterDefending.getRect().x -= 1;
        }
        if (moveRightDefend) {
            characterDefending.getRect().x += 1;
        }
        if (moveUpDefend) {
            characterDefending.getRect().y -= 1;
        }
        if (moveDownDefend) {
            characterDefending.getRect().y += 1;
        }
        checkCollide();
    }

    private void checkCollide() {
        if (characterDefending.getRect().x >= 715 && characterDefending.getRect().x <= 750) {
            moveLeftDefend = false;
        }
        if (characterDefending.getRect().x >= 1135 && characterDefending.getRect().x <= 1145) {
            moveRightDefend = false;
        }
        if (characterDefending.getRect().y >= 760 && characterDefending.getRect().y <= 770) {
            moveUpDefend = false;
        }
        if (characterDefending.getRect().y >= 985 && characterDefending.getRect().y <= 1000) {
            moveDownDefend = false;
        }
    }

    private void checkCollisionProjectiles() {

        for (int i = 0; i < enemyProjectiles1.size(); i++)
            if (characterDefending.getRect().intersects(enemyProjectiles1.get(i).getRect()) && canDamagePlayer && !godMode) {
                playerStats.setHealth(playerStats.getHealth() - enemies.get(0).getLevel());
                canDamagePlayer = false;
            }

        if (bungHoleTickler) {
            for (int i = 0; i < projectiles2.size(); i++) {
                if (characterDefending.getRect().intersects(projectiles2.get(i).getRect()) && canDamagePlayer && !godMode) {
                    playerStats.setHealth(playerStats.getHealth() - 10);
                    canDamagePlayer = false;
                }
            }
            for (int i = 0; i < projectiles3.size(); i++) {
                if (moveUpDefend || moveDownDefend || moveLeftDefend || moveRightDefend) {
                    if (characterDefending.getRect().intersects(projectiles3.get(i).getRect()) && canDamagePlayer && !godMode) {
                        playerStats.setHealth(playerStats.getHealth() - 10);
                        canDamagePlayer = false;
                    }
                }
            }
        }

        for (int i = 0; i < enemyHeals.size(); i++) {
            if (characterDefending.getRect().intersects(enemyHeals.get(i).getRect())) {
                playerStats.setHealth(playerStats.getHealth() + 2);
                enemyHeals.get(i).getRect().x = 100000;
                if (playerStats.getHealth() > 100) {
                    playerStats.setHealth(100);
                }
            }
        }
    }

    private void damageBoost() {
        if (!canDamagePlayer) {
            counter++;
        }
        if (counter == 50) {
            canDamagePlayer = true;
            counter = 0;
        }
    }


    private void checkChooseEnemy() {
        allowed = true;
    }

    public player getPlayerStats() {
        return playerStats;
    }

    public int getHealthPotions() {
        return healthPotions;
    }

    public void setHealthPotions(int healthPotions) {
        this.healthPotions = healthPotions;
    }

    public int getSwiftnessPotions() {
        return swiftnessPotions;
    }

    public void setSwiftnessPotions(int swiftnessPotions) {
        this.swiftnessPotions = swiftnessPotions;
    }

    public int getStrengthPotions() {
        return strengthPotions;
    }

    public void setStrengthPotions(int strengthPotions) {
        this.strengthPotions = strengthPotions;
    }

    public boolean isPlayerAlive() {
        return playerAlive;
    }

    public void setPlayerAlive(boolean playerAlive) {
        this.playerAlive = playerAlive;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        //Battle Option Choices
        if (start) {
            checkEnemyHealth();
            if (key == KeyEvent.VK_Z && xFightPos == 1 && yFightPos == 1) {
                start = false;
                chooseEnemy = true;
            } else if (key == KeyEvent.VK_Z && xFightPos == 2 && yFightPos == 1) {
                start = false;
                inItems = true;
            } else if (key == KeyEvent.VK_Z && xFightPos == 1 && yFightPos == 2) {
                start = false;
                characterDefending.getRect().x = 940;
                characterDefending.getRect().y = 875;
                moveUpDefend = false;
                moveDownDefend = false;
                moveLeftDefend = false;
                moveRightDefend = false;
                canDamagePlayer = true;
                defend = true;
                heals = true;
            } else if (key == KeyEvent.VK_Z && xFightPos == 2 && yFightPos == 2) {
                start = false;
                run = true;
            }

            if (key == KeyEvent.VK_DOWN) {
                if (yFightPos == 2) {
                    yFightPos--;
                } else {
                    yFightPos++;
                }
            } else if (key == KeyEvent.VK_UP) {
                if (yFightPos == 1) {
                    yFightPos++;
                } else {
                    yFightPos--;
                }
            } else if (key == KeyEvent.VK_RIGHT) {
                if (xFightPos == 2) {
                    xFightPos--;
                } else {
                    xFightPos++;
                }
            } else if (key == KeyEvent.VK_LEFT) {
                if (xFightPos == 1) {
                    xFightPos++;
                } else {
                    xFightPos--;
                }
            }
        } else if (chooseEnemy) {
            if (key == KeyEvent.VK_Z && xFightPos == 1 && yFightPos == 1 && allowed && enemies.get(0).isAlive()) {
                chooseEnemy = false;
                allowed = false;
                attacking = 0;
                chooseWeapon = true;
            } else if (key == KeyEvent.VK_Z && xFightPos == 2 && yFightPos == 1 && allowed && enemies.get(1).isAlive()) {
                attacking = 1;
                chooseEnemy = false;
                allowed = false;
                chooseWeapon = true;
            } else if (key == KeyEvent.VK_Z && xFightPos == 1 && yFightPos == 2 && allowed && enemies.get(2).isAlive()) {
                attacking = 2;
                chooseEnemy = false;
                allowed = false;
                chooseWeapon = true;
            } else if (key == KeyEvent.VK_Z && xFightPos == 2 && yFightPos == 2 && allowed && enemies.get(3).isAlive()) {
                attacking = 3;
                chooseEnemy = false;
                allowed = false;
                chooseWeapon = true;
            }

            if (key == KeyEvent.VK_X) {
                chooseEnemy = false;
                start = true;
            }

            if (key == KeyEvent.VK_DOWN) {
                if (yFightPos == 2) {
                    yFightPos--;
                } else {
                    yFightPos++;
                }
            } else if (key == KeyEvent.VK_UP) {
                if (yFightPos == 1) {
                    yFightPos++;
                } else {
                    yFightPos--;
                }
            } else if (key == KeyEvent.VK_RIGHT) {
                if (xFightPos == 2) {
                    xFightPos--;
                } else {
                    xFightPos++;
                }
            } else if (key == KeyEvent.VK_LEFT) {
                if (xFightPos == 1) {
                    xFightPos++;
                } else {
                    xFightPos--;
                }
            }
        } else if (inItems) {
            start = false;
            if (key == KeyEvent.VK_X) {
                inItems = false;
                start = true;
            }

            if (key == KeyEvent.VK_Z && xFightPos == 1 && yFightPos == 1 && allowed) {
                if (healthPotions < 4) {
                    playerStats.setHealth(playerStats.getHealth() + 15);
                    if (playerStats.getHealth() >= 100) {
                        playerStats.setHealth(100);
                    }
                    healthPotions++;
                }
                if (healthPotions == 4) {
                    inItems = false;
                    characterDefending.getRect().x = 940;
                    characterDefending.getRect().y = 875;
                    moveUpDefend = false;
                    moveDownDefend = false;
                    moveLeftDefend = false;
                    moveRightDefend = false;
                    defend = true;
                }
                inItems = false;
                characterDefending.getRect().x = 940;
                characterDefending.getRect().y = 875;
                moveUpDefend = false;
                moveDownDefend = false;
                moveLeftDefend = false;
                moveRightDefend = false;
                defend = true;
            } else if (key == KeyEvent.VK_Z && xFightPos == 2 && yFightPos == 1 && allowed) {
                if (swiftnessPotions < 2) {
                    playerStats.setSpeed(playerStats.getSpeed() + 10);
                    swiftnessPotions++;
                }
                if (swiftnessPotions == 2) {
                    inItems = false;
                    characterDefending.getRect().x = 940;
                    characterDefending.getRect().y = 875;
                    moveUpDefend = false;
                    moveDownDefend = false;
                    moveLeftDefend = false;
                    moveRightDefend = false;
                    defend = true;
                }
                inItems = false;
                characterDefending.getRect().x = 940;
                characterDefending.getRect().y = 875;
                moveUpDefend = false;
                moveDownDefend = false;
                moveLeftDefend = false;
                moveRightDefend = false;
                defend = true;
            } else if (key == KeyEvent.VK_Z && xFightPos == 1 && yFightPos == 2 && allowed) {
                if (strengthPotions < 3) {
                    playerStats.setSpeed(playerStats.getSpeed() + 10);
                    swiftnessPotions++;
                }
                if (swiftnessPotions == 3) {
                    inItems = false;
                    characterDefending.getRect().x = 940;
                    characterDefending.getRect().y = 875;
                    moveUpDefend = false;
                    moveDownDefend = false;
                    moveLeftDefend = false;
                    moveRightDefend = false;
                    defend = true;
                }
                inItems = false;
                characterDefending.getRect().x = 940;
                characterDefending.getRect().y = 875;
                moveUpDefend = false;
                moveDownDefend = false;
                moveLeftDefend = false;
                moveRightDefend = false;
                defend = true;
            }


            if (key == KeyEvent.VK_DOWN) {
                if (yFightPos == 2) {
                    yFightPos--;
                } else {
                    yFightPos++;
                }
            } else if (key == KeyEvent.VK_UP) {
                if (yFightPos == 1) {
                    yFightPos++;
                } else {
                    yFightPos--;
                }
            } else if (key == KeyEvent.VK_RIGHT) {
                if (xFightPos == 2) {
                    xFightPos--;
                } else {
                    xFightPos++;
                }
            } else if (key == KeyEvent.VK_LEFT) {
                if (xFightPos == 1) {
                    xFightPos++;
                } else {
                    xFightPos--;
                }
            }
        } else if (chooseWeapon) {
            if (key == KeyEvent.VK_X) {
                chooseWeapon = false;
                start = true;
            }
            if (key == KeyEvent.VK_Z && xFightPos == 1 && yFightPos == 1 && allowed) {
                chooseWeapon = false;
                longsword = true;
                fight = true;
            } else if (key == KeyEvent.VK_Z && xFightPos == 2 && yFightPos == 1 && allowed) {
                chooseWeapon = false;
                bow = true;
                fight = true;
            } else if (key == KeyEvent.VK_Z && xFightPos == 1 && yFightPos == 2 && allowed) {
                chooseWeapon = false;
                axe = true;
                fight = true;
            } else if (key == KeyEvent.VK_Z && xFightPos == 2 && yFightPos == 2 && allowed) {
                chooseWeapon = false;
                cryoStaff = true;
                fight = true;
            }

            if (key == KeyEvent.VK_DOWN) {
                if (yFightPos == 2) {
                    yFightPos--;
                } else {
                    yFightPos++;
                }
            } else if (key == KeyEvent.VK_UP) {
                if (yFightPos == 1) {
                    yFightPos++;
                } else {
                    yFightPos--;
                }
            } else if (key == KeyEvent.VK_RIGHT) {
                if (xFightPos == 2) {
                    xFightPos--;
                } else {
                    xFightPos++;
                }
            } else if (key == KeyEvent.VK_LEFT) {
                if (xFightPos == 1) {
                    xFightPos++;
                } else {
                    xFightPos--;
                }
            }
        } else if (fight) {
            if (key == KeyEvent.VK_Z) {
                checkClicked();
            }
        } else if (defend) {
            if (key == KeyEvent.VK_G) {
                godMode = true;
            } else if (key == KeyEvent.VK_BACK_SPACE) {
                godMode = false;
            }
            if (key == KeyEvent.VK_UP) {
                moveUpDefend = true;
            } else if (key == KeyEvent.VK_RIGHT) {
                moveRightDefend = true;
            }
            if (key == KeyEvent.VK_LEFT) {
                moveLeftDefend = true;
            }
            if (key == KeyEvent.VK_DOWN) {
                moveDownDefend = true;
            }
        }

        if (key == KeyEvent.VK_ESCAPE) {
            System.exit(-1);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_Z) {
            checkChooseEnemy();
        }

        if (defend) {
            if (key == KeyEvent.VK_UP) {
                moveUpDefend = false;
            }
            if (key == KeyEvent.VK_RIGHT) {
                moveRightDefend = false;
            }
            if (key == KeyEvent.VK_LEFT) {
                moveLeftDefend = false;
            }
            if (key == KeyEvent.VK_DOWN) {
                moveDownDefend = false;
            }
        }
        if (run) {
            if (key == KeyEvent.VK_Z) {
                runClicks++;
            }
        }
    }
}