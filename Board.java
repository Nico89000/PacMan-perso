import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;

public class Board extends JPanel {

    /** Indique si le jeu a commencé ou pas */
    private static boolean ready = false;
    /** Pacman */
    private static Pac pacman = new Pac();
    /** Hitbox de Pacman */
    private static Rectangle pacHitBox = new Rectangle(pacman.getX(), pacman.getY(), pacman.getWidth(),
            pacman.getHeight());
    /** Animation de Pacman */
    private static int pacAnimationState = 0;
    /**
     * Compte le nombre de frames passées sur une animation pour que le défilement
     * ne soit pas trop rapide
     */
    private static int countAnimFrames = 0;
    /** Direction du sprite de Pacman */
    private static int pacSpriteDirection = 1;
    /** Image de background */
    private static BufferedImage bg;
    /** Image de background 2 */
    private static BufferedImage bg2;
    /** Image de Pacman */
    private static BufferedImage pacImg;
    /** Image de Blinky */
    private static BufferedImage blinkyImg;
    /** Image des points à ramasser */
    private static BufferedImage dotImg;
    /** Image des super points */
    private static BufferedImage superDotImg;
    /** Image du ready! */
    private static BufferedImage readyImg;
    /** Image des vies */
    private static BufferedImage lifeImg;
    /** Image de mort */
    private static BufferedImage deathImg;
    /** Image Game Over */
    private static BufferedImage gameOverImg;
    /** Array qui stocke les points à ramasser */
    private static ArrayList<Dot> dots = new ArrayList<Dot>();
    /** Array qui stocke les murs infranchissables */
    private static ArrayList<Wall> walls = new ArrayList<Wall>();
    /** Array qui stocke les espaces vides */
    private static ArrayList<EmptySpace> emptySpaces = new ArrayList<EmptySpace>();
    /** Array qui stocke les fantômes */
    private static ArrayList<Ghost> ghosts = new ArrayList<Ghost>();
    /** Téléportation quand on sort à gauche */
    private static Rectangle teleport1 = new Rectangle(-20, 276, 7, 7);
    /** Téléportation quand on sort à droite */
    private static Rectangle teleport2 = new Rectangle(458, 276, 7, 7);
    /** Booléen qui indique si le movement désiré est possible ou pas */
    private static boolean movementAllowed = true;
    /** Score */
    private static int score = 0;
    /** Vies */
    private static int vies = 2;
    /** Indique si PacMan est mort ou pas */
    private static boolean dead = false;
    /** Indique si game over ou pas */
    private static boolean gameOver = false;
    /** Coordonnées de départ X de Pacman */
    private static final int DEPART_PAC_X = 213;
    /** Coordonnées de départ Y de Pacman */
    private static final int DEPART_PAC_Y = 412;
    /** Timer d'update de l'écran de jeu */
    private static Timer timerUpdate = new Timer(25, null);
    /** Timer pour jouer l'animation de mort */
    private static Timer timerDeath = new Timer(100, null);
    /** Compte les sprites de l'animation de mort */
    private static int deathSpriteCount = 0;
    /** Tableau représentant le jeu */
    private static int[][] gameBoard = {
            { 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3 },
            { 3, 3, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 3, 3, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 3, 3 },
            { 3, 3, 1, 3, 3, 3, 3, 1, 3, 3, 3, 3, 3, 1, 3, 3, 1, 3, 3, 3, 3, 3, 1, 3, 3, 3, 3, 1, 3, 3 },
            { 3, 3, 2, 3, 3, 3, 3, 1, 3, 3, 3, 3, 3, 1, 3, 3, 1, 3, 3, 3, 3, 3, 1, 3, 3, 3, 3, 2, 3, 3 },
            { 3, 3, 1, 3, 3, 3, 3, 1, 3, 3, 3, 3, 3, 1, 3, 3, 1, 3, 3, 3, 3, 3, 1, 3, 3, 3, 3, 1, 3, 3 },
            { 3, 3, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 3, 3 },
            { 3, 3, 1, 3, 3, 3, 3, 1, 3, 3, 1, 3, 3, 3, 3, 3, 3, 3, 3, 1, 3, 3, 1, 3, 3, 3, 3, 1, 3, 3 },
            { 3, 3, 1, 3, 3, 3, 3, 1, 3, 3, 1, 3, 3, 3, 3, 3, 3, 3, 3, 1, 3, 3, 1, 3, 3, 3, 3, 1, 3, 3 },
            { 3, 3, 1, 1, 1, 1, 1, 1, 3, 3, 1, 1, 1, 1, 3, 3, 1, 1, 1, 1, 3, 3, 1, 1, 1, 1, 1, 1, 3, 3 },
            { 3, 3, 3, 3, 3, 3, 3, 1, 3, 3, 3, 3, 3, 0, 3, 3, 0, 3, 3, 3, 3, 3, 1, 3, 3, 3, 3, 3, 3, 3 },
            { 3, 3, 3, 3, 3, 3, 3, 1, 3, 3, 3, 3, 3, 0, 3, 3, 0, 3, 3, 3, 3, 3, 1, 3, 3, 3, 3, 3, 3, 3 },
            { 3, 3, 3, 3, 3, 3, 3, 1, 3, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 3, 1, 3, 3, 3, 3, 3, 3, 3 },
            { 3, 3, 3, 3, 3, 3, 3, 1, 3, 3, 0, 3, 3, 4, 0, 0, 4, 3, 3, 0, 3, 3, 1, 3, 3, 3, 3, 3, 3, 3 },
            { 3, 3, 3, 3, 3, 3, 3, 1, 3, 3, 0, 3, 3, 4, 0, 0, 4, 3, 3, 0, 3, 3, 1, 3, 3, 3, 3, 3, 3, 3 },
            { 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 5, 0, 0, 0, 0, 0, 5, 3, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0 },
            { 3, 3, 3, 3, 3, 3, 3, 1, 3, 3, 0, 3, 3, 3, 3, 3, 3, 3, 3, 0, 3, 3, 1, 3, 3, 3, 3, 3, 3, 3 },
            { 3, 3, 3, 3, 3, 3, 3, 1, 3, 3, 0, 3, 3, 3, 3, 3, 3, 3, 3, 0, 3, 3, 1, 3, 3, 3, 3, 3, 3, 3 },
            { 3, 3, 3, 3, 3, 3, 3, 1, 3, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 3, 1, 3, 3, 3, 3, 3, 3, 3 },
            { 3, 3, 3, 3, 3, 3, 3, 1, 3, 3, 0, 3, 3, 3, 3, 3, 3, 3, 3, 0, 3, 3, 1, 3, 3, 3, 3, 3, 3, 3 },
            { 3, 3, 3, 3, 3, 3, 3, 1, 3, 3, 0, 3, 3, 3, 3, 3, 3, 3, 3, 0, 3, 3, 1, 3, 3, 3, 3, 3, 3, 3 },
            { 3, 3, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 3, 3, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 3, 3 },
            { 3, 3, 1, 3, 3, 3, 3, 1, 3, 3, 3, 3, 3, 1, 3, 3, 1, 3, 3, 3, 3, 3, 1, 3, 3, 3, 3, 1, 3, 3 },
            { 3, 3, 1, 3, 3, 3, 3, 1, 3, 3, 3, 3, 3, 1, 3, 3, 1, 3, 3, 3, 3, 3, 1, 3, 3, 3, 3, 1, 3, 3 },
            { 3, 3, 2, 1, 1, 3, 3, 1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 3, 3, 1, 1, 2, 3, 3 },
            { 3, 3, 3, 3, 1, 3, 3, 1, 3, 3, 1, 3, 3, 3, 3, 3, 3, 3, 3, 1, 3, 3, 1, 3, 3, 1, 3, 3, 3, 3 },
            { 3, 3, 3, 3, 1, 3, 3, 1, 3, 3, 1, 3, 3, 3, 3, 3, 3, 3, 3, 1, 3, 3, 1, 3, 3, 1, 3, 3, 3, 3 },
            { 3, 3, 1, 1, 1, 1, 1, 1, 3, 3, 1, 1, 1, 1, 3, 3, 1, 1, 1, 1, 3, 3, 1, 1, 1, 1, 1, 1, 3, 3 },
            { 3, 3, 1, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 1, 3, 3, 1, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 1, 3, 3 },
            { 3, 3, 1, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 1, 3, 3, 1, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 1, 3, 3 },
            { 3, 3, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 3, 3 },
            { 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3 }, };

    /** Load les sprites et les murs */
    public Board() {
        // Initialisation de différents éléments du jeu
        loadSprites();
        addWalls();
        initTimers();
        initGhosts();

        // Ajout de la police d'écriture
        try {
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            Font emulogic = Font.createFont(Font.TRUETYPE_FONT, new File("images/emulogic.ttf")).deriveFont(14f);
            ge.registerFont(emulogic);
            setFont(emulogic);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
    }

    /** Permet de redessiner la fenêtre de jeu */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw le background
        g.drawImage(bg, 0, 0, bg.getWidth(this) * 2, bg.getHeight(this) * 2, this);

        // Affichage du score
        g.setColor(Color.WHITE);
        g.drawString(("Score : " + score), 10, 32);

        // Affichage des vies
        g.drawString("Lives : ", 10, 567);
        for (int i = 0; i < vies + 1; i++) {
            g.drawImage(lifeImg, 120 + i * 25, 548, 23, 23, this);
        }

        // Si le jeu n'est pas lancé ou si on vient de mourir, on affiche ready!
        if (!ready) {
            g.drawImage(readyImg, 180, 322, (int) readyImg.getWidth() * 2, readyImg.getHeight() * 2, this);
            goReady();
        }

        // Affichage des points à manger
        for (Dot var : dots) {
            if (var instanceof SuperDot) {
                g.drawImage(superDotImg, var.getX(), var.getY(), (int) (superDotImg.getWidth() * 1.5),
                        (int) (superDotImg.getHeight() * 1.5), this);

            } else {
                g.drawImage(dotImg, var.getX(), var.getY(), (int) (dotImg.getWidth() * 1.5),
                        (int) (dotImg.getHeight() * 1.5), this);
            }
        }

        // for (EmptySpace var : emptySpaces) {
        // g.fillRect(var.getX(), var.getY(), var.getWidth(), var.getHeight());
        // }

        // Si il n'y a pas Game Over
        if (!gameOver) {

            if (!dead) {
                // Affichage des fantômes
                for (Ghost var : ghosts) {
                    g.drawImage(blinkyImg, var.getX(), var.getY(), var.getWidth(), var.getHeight(), this);
                }

                // Affichage de Pacman
                g.drawImage(pacImg, pacman.getX(), pacman.getY(), pacman.getWidth(), pacman.getHeight(), this);

            } else {
                g.drawImage(deathImg, pacman.getX(), pacman.getY(), pacman.getWidth(), pacman.getHeight(), this);
            }
        } else {
            g.drawImage(gameOverImg, 155, 322, (int) gameOverImg.getWidth() * 2, gameOverImg.getHeight() * 2, this);
        }

        // Affiche les hitbox pour debug
        // for (Wall var : walls) {
        // g.fillRect(var.getX(), var.getY(), var.getWidth(), var.getHeight());
        // }
        // g.fillRect((int) pacHitBox.getX(), (int) pacHitBox.getY(), (int)
        // pacHitBox.getWidth(),
        // (int) pacHitBox.getHeight());

    }

    /** Initialisation des timers */
    public void initTimers() {
        timerDeath.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    deathImg = ImageIO.read(new File("images/pacman_died_" + deathSpriteCount + ".png"));
                    deathSpriteCount++;
                    if (deathSpriteCount > 13) {
                        vies--;
                        pacRevive();
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        timerUpdate.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                loadPacSprite();
                addDots();
                repaint();
            }
        });
        timerUpdate.start();
    }

    /** Load les sprites statiques utilisés */
    public void loadSprites() {
        try {
            bg = ImageIO.read(new File("images/background_0.png"));
            bg2 = ImageIO.read(new File("images/background_1.png"));
            dotImg = ImageIO.read(new File("images/food.png"));
            superDotImg = ImageIO.read(new File("images/powerBall.png"));
            readyImg = ImageIO.read(new File("images/ready.png"));
            lifeImg = ImageIO.read(new File("images/pacman_0_1.png"));
            gameOverImg = ImageIO.read(new File("images/gameover.png"));
            blinkyImg = ImageIO.read(new File("images/ghost_0_0.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** Load le sprite de Pacman de façon dynamique */
    public void loadPacSprite() {
        try {
            pacImg = ImageIO.read(new File("images/pacman_" + pacSpriteDirection + "_" + pacAnimationState + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initGhosts() {
        ghosts.add(new Ghost(213, 220));
    }

    /** Bouge Pacman vers la droite */
    public void moveRight() {
        // On bouge d'abord sa hitbox et on vérifie qu'elle ne coupe aucun mur
        pacHitBox.translate((int) pacman.getSpeed(), 0);
        for (Wall var : walls) {
            // Si elle coupe un mur on passe movementAllowed à faux
            if (var.getHitBox().intersects(pacHitBox)) {
                movementAllowed = false;
                break;
            }
        }

        // Si movementAllowed est à faux la hitbox revient à sa position de départ
        if (!movementAllowed) {
            pacHitBox.translate(-(int) pacman.getSpeed(), 0);
        } else {
            // Sinon on update la direction de Pacman pour l'affichage du sprite
            pacSpriteDirection = 0;
            updateAnimation();
            // On vérifie si sa nouvelle position est un des points de téléportation
            if (teleport2.intersects(pacHitBox)) {
                // Si s'en est un Pacman est téléporté
                pacman.setX((int) teleport1.getX());
                pacHitBox.setLocation((int) teleport1.getX(), (int) (pacHitBox.getY()));
            } else {
                // Sinon il bouge normalement
                pacman.setX(pacman.getX() + (int) pacman.getSpeed());
            }
            // S'il se déplace sur un point alors il mange le point
            for (Dot var : dots) {
                if (var.getHitBox().intersects(pacHitBox)) {
                    eatDot(var);
                }
            }
        }

        movementAllowed = true;
    }

    /** Bouge Pacman vers la gauche */
    public void moveLeft() {
        pacHitBox.translate(-(int) pacman.getSpeed(), 0);
        for (Wall var : walls) {
            if (var.getHitBox().intersects(pacHitBox)) {
                movementAllowed = false;
                break;
            }
        }
        if (!movementAllowed) {
            pacHitBox.translate((int) pacman.getSpeed(), 0);
        } else {
            pacSpriteDirection = 2;
            updateAnimation();
            if (teleport1.intersects(pacHitBox)) {
                pacman.setX((int) teleport2.getX());
                pacHitBox.setLocation((int) teleport2.getX(), (int) (pacHitBox.getY()));
            } else {
                pacman.setX(pacman.getX() - (int) pacman.getSpeed());
            }
            for (Dot var : dots) {
                if (var.getHitBox().intersects(pacHitBox)) {
                    eatDot(var);
                }
            }
        }
        movementAllowed = true;
    }

    /** Bouge Pacman vers le haut */
    public void moveUp() {
        pacHitBox.translate(0, -(int) pacman.getSpeed());
        for (Wall var : walls) {
            if (var.getHitBox().intersects(pacHitBox)) {
                movementAllowed = false;
                break;
            }
        }
        if (!movementAllowed) {
            pacHitBox.translate(0, (int) pacman.getSpeed());
        } else {
            pacSpriteDirection = 3;
            updateAnimation();
            pacman.setY(pacman.getY() - (int) pacman.getSpeed());
            for (Dot var : dots) {
                if (var.getHitBox().intersects(pacHitBox)) {
                    eatDot(var);
                }
            }
        }
        movementAllowed = true;
    }

    /** Bouge Pacman vers le bas */
    public void moveDown() {
        pacHitBox.translate(0, (int) pacman.getSpeed());
        for (Wall var : walls) {
            if (var.getHitBox().intersects(pacHitBox)) {
                movementAllowed = false;
                break;
            }
        }
        if (!movementAllowed) {
            pacHitBox.translate(0, -(int) pacman.getSpeed());
        } else {
            pacSpriteDirection = 1;
            updateAnimation();
            pacman.setY(pacman.getY() + (int) pacman.getSpeed());
            for (Dot var : dots) {
                if (var.getHitBox().intersects(pacHitBox)) {
                    eatDot(var);
                }
            }
        }
        movementAllowed = true;
    }

    /**
     * Update de l'animation du sprite en fonction de l'animation en cours et du
     * temps passé dessus
     */
    public void updateAnimation() {
        switch (pacAnimationState) {
        case 0:
            pacAnimationState = 1;
            break;
        case 1:
            if (countAnimFrames >= 1) {
                pacAnimationState = 2;
                countAnimFrames = -1;
            }
            countAnimFrames++;
            break;
        case 2:
            if (countAnimFrames >= 1) {
                pacAnimationState = 3;
                countAnimFrames = -1;
            }
            countAnimFrames++;
            break;
        case 3:
            if (countAnimFrames >= 1) {
                pacAnimationState = 0;
                countAnimFrames = -1;
            }
            countAnimFrames++;
            break;
        default:
            break;
        }
    }

    /** Ajoute les points en fonction du tableau gameBoard */
    public void addDots() {
        dots.clear();
        for (int i = 0; i < gameBoard.length; i++) {
            for (int j = 0; j < gameBoard[0].length; j++) {
                if (gameBoard[i][j] == 1) {
                    dots.add(new Dot(i, j));
                }
                if (gameBoard[i][j] == 2) {
                    dots.add(new SuperDot(i, j));
                }
            }
        }
    }

    /** Ce qu'il se passe quand Pacman mange un point */
    public void eatDot(Dot d) {
        // La case du point dans le tableau est set à 0 pour qu'il ne soit plus
        // déssiné lors du redraw()
        gameBoard[d.getI()][d.getJ()] = 0;
        // Incrémentation du score
        if (d instanceof SuperDot) {
            score += 20;
        } else {
            score += 10;
        }
    }

    /** Ajoute les murs et leur collision + cases vides */
    public void addWalls() {
        for (int i = 0; i < gameBoard.length; i++) {
            for (int j = 0; j < gameBoard[0].length; j++) {
                if (gameBoard[i][j] == 3) {
                    walls.add(new Wall(i, j));
                }
                if (gameBoard[i][j] == 4) {
                    walls.add(new Wall(i, j, 20));
                }
                if (gameBoard[i][j] == 5) {
                    walls.add(new Wall(i, j, 10));
                }
                if (gameBoard[i][j] == 0 || gameBoard[i][j] == 1 || gameBoard[i][j] == 2) {
                    emptySpaces.add(new EmptySpace(i, j));
                }
            }
        }
    }

    /**
     * Permet de vérifier si un mouvement est possible
     * 
     * @param d Mouvement désiré : right, left, down, up
     * @return true si le mouvement est possible, false sinon
     */
    public boolean isMovePossible(String d) {
        boolean r = true;
        switch (d) {
        case "right":
            pacHitBox.translate((int) pacman.getSpeed(), 0);
            for (Wall var : walls) {
                if (var.getHitBox().intersects(pacHitBox)) {
                    r = false;
                }
            }
            pacHitBox.translate(-(int) pacman.getSpeed(), 0);
            break;
        case "left":
            pacHitBox.translate(-(int) pacman.getSpeed(), 0);
            for (Wall var : walls) {
                if (var.getHitBox().intersects(pacHitBox)) {
                    r = false;
                }
            }
            pacHitBox.translate((int) pacman.getSpeed(), 0);
            break;
        case "down":
            pacHitBox.translate(0, (int) pacman.getSpeed());
            for (Wall var : walls) {
                if (var.getHitBox().intersects(pacHitBox)) {
                    r = false;
                }
            }
            pacHitBox.translate(0, -(int) pacman.getSpeed());
            break;
        case "up":
            pacHitBox.translate(0, -(int) pacman.getSpeed());
            for (Wall var : walls) {
                if (var.getHitBox().intersects(pacHitBox)) {
                    r = false;
                }
            }
            pacHitBox.translate(0, (int) pacman.getSpeed());
            break;
        default:
            break;
        }
        return r;
    }

    /**
     * Indique si le jeu est lancé ou non
     * 
     * @return true s'il est lancé, false sinon
     */
    public boolean isReady() {
        return ready;
    }

    /** Méthode chargée du démarrage du jeu */
    public void goReady() {
        if (!ready) {
            Timer tReady = new Timer(2000, null);
            tReady.setRepeats(false);
            tReady.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    ready = true;
                }
            });
            tReady.start();
        }
    }

    /** Fait mourir Pacman pour debug */
    public void setDead() {
        dead = true;
    }

    /** Ce qu'il se passe quand Pacman meurt */
    public void pacDeath() {
        timerDeath.start();
    }

    public void pacRevive() {
        timerDeath.stop();
        if (vies > -1) {
            deathSpriteCount = 0;
            pacman.setX(DEPART_PAC_X);
            pacman.setY(DEPART_PAC_Y);
            pacHitBox.setLocation(DEPART_PAC_X, DEPART_PAC_Y);
            countAnimFrames = 0;
            pacSpriteDirection = 1;
            pacAnimationState = 0;
            dead = false;
            ready = false;
        } else {
            gameOver = true;
        }
    }

    /**
     * Indique si Pacman est mort
     * 
     * @return true si mort, false sinon
     */
    public boolean isPacDead() {
        return dead;
    }

    /**
     * Indique si Game Over
     * 
     * @return true si Game Over, false sinon
     */
    public boolean isGameOver() {
        return gameOver;
    }

    public void ghostPathFinding() {
        for (Ghost var : ghosts) {

        }
    }

}
