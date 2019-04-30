import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.Timer;

/**
 * Contrôle de la fenêtre principale
 */
public class MainFrame extends JFrame implements KeyListener {

    private Title titre;
    private Board board;
    /** Indique si le board du jeu est affiché ou non */
    static private boolean boardReady = false;
    // Directions de Pacman
    static private boolean right = false;
    static private boolean left = false;
    static private boolean up = false;
    static private boolean down = false;
    /** Timer qui update le mouvement du jeu toutes les 25ms */
    static private Timer updt = new Timer(25, null);
    /** Pour redémarrer le timer d'update après la mort */
    static private Timer deathTimer = new Timer(10, null);
    /** Array où on stocke des keystrokes d'avance pour le mouvement */
    static private ArrayList<String> storedKeys = new ArrayList<String>();

    public MainFrame() {
        setTitle("Pacman");
        setBackground(Color.BLACK);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(461, 613);
        addKeyListener(this);
        setContentPane(titre = new Title());
        setLocationRelativeTo(null);
        setVisible(true);
        requestFocus();
        updt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (!board.isPacDead()) {
                    updateMovement();
                } else {
                    stopMovement();
                    board.pacDeath();
                    updt.stop();
                }
            }

        });

        deathTimer.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (!board.isPacDead()) {
                    updt.start();
                    deathTimer.stop();
                }
            }
        });
    }

    /** Actions lors des keystrokes */
    public void keyPressed(KeyEvent e) {
        // Si l'animation du titre est finie, que le jeu n'est pas déjà affiché, et
        // qu'on appuie sur espace :
        if (titre.isTitleDown() && e.getKeyCode() == KeyEvent.VK_SPACE && !boardReady) {
            System.out.println("Game start");
            boardReady = true;
            // On enlève le panel titre et on affiche le panel board
            remove(titre);
            setContentPane(board = new Board());
            revalidate();
            left = true;
            updt.start();
        }
        // Si le board est affiché et qu'on appuie sur une des touches de direction, un
        // booléen de direction est mis à true ou on stocke la direction si le
        // mouvement est impossible pour le moment
        if (boardReady && !board.isPacDead()) {
            switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                if (!(board.isMovePossible("up")) && storedKeys.size() < 1) {
                    storedKeys.add("up");
                } else {
                    if (!(board.isMovePossible("up")) && storedKeys.size() > 0) {
                        break;
                    } else {
                        storedKeys.clear();
                        right = false;
                        left = false;
                        down = false;
                        up = true;
                    }
                }
                break;
            case KeyEvent.VK_DOWN:
                if (!(board.isMovePossible("down")) && storedKeys.size() < 1) {
                    storedKeys.add("down");
                } else {
                    if (!(board.isMovePossible("down")) && storedKeys.size() > 0) {
                        break;
                    } else {
                        storedKeys.clear();
                        right = false;
                        left = false;
                        up = false;
                        down = true;
                    }
                }
                break;
            case KeyEvent.VK_LEFT:
                if (!(board.isMovePossible("left")) && storedKeys.size() < 1) {
                    storedKeys.add("left");
                } else {
                    if (!(board.isMovePossible("left")) && storedKeys.size() > 0) {
                        break;
                    } else {
                        storedKeys.clear();
                        up = false;
                        down = false;
                        right = false;
                        left = true;
                    }
                }
                break;
            case KeyEvent.VK_RIGHT:
                if (!(board.isMovePossible("right")) && storedKeys.size() < 1) {
                    storedKeys.add("right");
                } else {
                    if (!(board.isMovePossible("right")) && storedKeys.size() > 0) {
                        break;
                    } else {
                        storedKeys.clear();
                        up = false;
                        down = false;
                        left = false;
                        right = true;
                    }
                }
                break;
            case KeyEvent.VK_D:
                board.setDead();
            default:
                break;
            }
        }
    }

    public void keyReleased(KeyEvent e) {
    }

    public void keyTyped(KeyEvent e) {
    }

    /**
     * Bouge Pacman dans la direction indiquée par un des booléens de direction
     */
    public void updateMovement() {
        // Seulement si le jeu a démarré et Pacman est vivant
        if (board.isReady() && !board.isPacDead()) {
            // On vérifie si une direction a été stockée
            if (storedKeys.size() > 0) {
                // On vérifie si le mouvement stocké est possible, si oui le booléen de
                // direction est set pour cette direction
                if (board.isMovePossible(storedKeys.get(0))) {
                    switch (storedKeys.get(0)) {
                    case "up":
                        down = false;
                        right = false;
                        left = false;
                        up = true;
                        break;
                    case "down":
                        up = false;
                        right = false;
                        left = false;
                        down = true;
                        break;
                    case "right":
                        up = false;
                        down = false;
                        left = false;
                        right = true;
                        break;
                    case "left":
                        up = false;
                        down = false;
                        right = false;
                        left = true;
                        break;
                    default:
                        break;
                    }
                    storedKeys.remove(0);
                }
            }

            // On bouge dans la direction du booleen à true
            if (up) {
                board.moveUp();
            }

            if (down) {
                board.moveDown();
            }

            if (right) {
                board.moveRight();
            }
            if (left) {
                board.moveLeft();
            }
        }
    }

    public void stopMovement() {
        up = false;
        down = false;
        right = false;
        left = false;

        deathTimer.start();
    }

}
