package de.heaal.eaf.trainsimulator;

import javax.swing.*;
import java.awt.*;

public class ZugSpiel extends JPanel {

    private int zugX = 50; // Anfangsposition des Zuges auf der X-Achse
    private int zugY = 200; // Anfangsposition des Zuges auf der Y-Achse
    private int zielX = 500; // Zielposition auf der X-Achse
    private int zielY = 200; // Zielposition auf der Y-Achse

    public void bewegeZug() {
        if (zugX < zielX) {
            zugX += 1; // Zug nach rechts bewegen
        } else if (zugX > zielX) {
            zugX -= 1; // Zug nach links bewegen
        }

        if (zugY < zielY) {
            zugY += 1; // Zug nach unten bewegen
        } else if (zugY > zielY) {
            zugY -= 1; // Zug nach oben bewegen
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.GREEN);
        g.fillOval(zielX, zielY, 20, 20); // Ziel zeichnen
        g.setColor(Color.RED);
        g.fillRect(zugX, zugY, 50, 20); // Zug zeichnen
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Zug Spiel");
        ZugSpiel zugSpiel = new ZugSpiel();
        frame.add(zugSpiel);
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        while (true) {
            zugSpiel.bewegeZug();
            zugSpiel.repaint();

            try {
                Thread.sleep(10); // Eine kurze Pause, um die Bewegung sichtbar zu machen
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


}
