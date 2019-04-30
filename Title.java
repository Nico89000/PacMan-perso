import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * Ecran d'intro du jeu
 */
public class Title extends JPanel {

    /** Coordonnées de départ du logo */
    static private int Y_COORD = -150;
    /** Timer pour l'animation du logo */
    static private Timer timerImg;
    /** Timer pour le clignotement du texte */
    static private Timer timerStart;
    /** Booléen pour le clignotement du texte */
    static private boolean isBlinking = false;
    /** Texte affiché au milieu de l'écrant */
    static private JLabel pressStart = new JLabel("Press space to start");
    /** Logo */
    static private BufferedImage titre;
    /** Largeur du logo */
    static private int imgWidth;
    /** Hauteur du logo */
    static private int imgHeight;
    /** Indique si l'animation du logo est finie */
    static private boolean titleDown = false;
    /** Pour gérer l'animation en fonction du delta temps */
    static private long startTime;

    /** Constructeur de l'écran d'intro */
    public Title() {
        try {
            // Layout pour afficher un élément au milieu de la fenêtre
            setLayout(new GridBagLayout());
            setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            // Ajout de la police d'écriture utilisée par le jeu
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            Font emulogic = Font.createFont(Font.TRUETYPE_FONT, new File("images/emulogic.ttf")).deriveFont(14f);
            ge.registerFont(emulogic);

            setBackground(Color.BLACK);
            pressStart.setFont(emulogic);
            pressStart.setForeground(Color.BLACK);
            add(pressStart);

            // On load le logo et on ajuste sa taille
            titre = ImageIO.read(new File("images/title.png"));
            imgWidth = titre.getWidth(this) * 2;
            imgHeight = titre.getHeight(this) * 2;

            // Ajustement de la position du texte
            pressStart.setLocation(pressStart.getLocation().x, 350);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
        titleDown();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(titre, this.getParent().getWidth() / 2 - imgWidth / 2, Y_COORD, imgWidth, imgHeight, this);
        if (titleDown) {
            g.setColor(Color.white);
        }

    }

    /** Animation du logo puis clignotement du texte */
    public void titleDown() {
        timerImg = new Timer(10, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                long time = System.nanoTime();
                long delta = (time - startTime) / 1000000;
                Y_COORD += (int) (delta * 0.25);
                startTime = System.nanoTime();
                repaint();
                if (Y_COORD >= 150) {
                    timerImg.stop();
                    titleDown = true;
                }
            }
        });
        startTime = System.nanoTime();
        timerImg.start();
        repaint();

        timerStart = new Timer(500, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ev) {
                if (titleDown) {
                    if (!isBlinking) {
                        pressStart.setForeground(Color.WHITE);
                        isBlinking = true;
                    } else {
                        pressStart.setForeground(Color.BLACK);
                        isBlinking = false;
                    }
                }
            }
        });
        timerStart.start();
    }

    /**
     * Indique si l'animation du logo est finie ou non
     * 
     * @return true si finie, false sinon
     */
    public boolean isTitleDown() {
        return titleDown;
    }

    /** Stop le timer de clignotement du texte */
    public void stopTimer() {
        timerStart.stop();
    }

}
