package de.heaal.eaf.trainsimulatorold;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ZugSpiel extends JPanel {

    private int zugX = 50; // Anfangsposition des Zuges auf der x-Achse
    private int zugY = 200; // Anfangsposition des Zuges auf der y-Achse
    private int goalX = 500; // Zielposition auf der x-Achse
    private int goalY = 200; // Zielposition auf der y-Achse
    private BufferedImage trainStationImg;
    private BufferedImage startStationImg;
    private BufferedImage trainImg;

    //private BufferedImage trainstationImg;

    public void bewegeZug() {
        if (zugX < goalX) {
            zugX += 1; // Zug nach rechts bewegen
        } else if (zugX > goalX) {
            zugX -= 1; // Zug nach links bewegen
        }

        if (zugY < goalY) {
            zugY += 1; // Zug nach unten bewegen
        } else if (zugY > goalY) {
            zugY -= 1; // Zug nach oben bewegen
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //g.setColor(Color.GREEN);
        //g.fillOval(zielX, zielY, 20, 20); // Ziel zeichnen

        //Railroad
        g.setColor(Color.GRAY);
        g.drawLine(0, 235, getWidth(), 235);
        g.drawLine(0, 237, getWidth(), 237);

        try {
            trainStationImg = ImageIO.read(new File("C:\\Users\\akifn\\IdeaProjects\\EAFramework_SoSe23\\src\\main\\java\\de\\heaal\\eaf\\trainsimulator\\images\\trainStation.png"));
            startStationImg = ImageIO.read(new File("C:\\Users\\akifn\\IdeaProjects\\EAFramework_SoSe23\\src\\main\\java\\de\\heaal\\eaf\\trainsimulator\\images\\startStation.png"));
            trainImg = ImageIO.read(new File("C:\\Users\\akifn\\IdeaProjects\\EAFramework_SoSe23\\src\\main\\java\\de\\heaal\\eaf\\trainsimulator\\images\\trainSmall.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //g.setColor(Color.RED);
        //g.fillRect(zugX, zugY, 50, 20); // Zug zeichnen
        g.drawImage(trainStationImg,500, 150, null); // draw trainstation
        g.drawImage(startStationImg,50, 140, null); // draw trainstation
        g.drawImage(trainImg,zugX, zugY, null); // draw train

    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Zug Spiel");
        ZugSpiel zugSpiel = new ZugSpiel();
        frame.add(zugSpiel);
        frame.setSize(800, 400);
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
