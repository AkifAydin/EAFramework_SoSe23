package de.heaal.eaf.trainsimulator;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class testTrain extends JPanel implements ActionListener {


    private static final int SCREEN_WIDTH = 800;
    private static final int SCREEN_HEIGHT = 600;
    private static final int TRAIN_WIDTH = 50;
    private static final int TRAIN_HEIGHT = 20;
    private static final int TRAIN_SPEED = 5;

    private int trainX;
    private int aimX;
    private int cameraX;
    private BufferedImage trainStationImg;
    private BufferedImage startStationImg;
    private BufferedImage trainImg;

    public testTrain() {
        trainX = SCREEN_WIDTH / 2 - TRAIN_WIDTH / 2; // Center train horizontally
        aimX = 2000; // Aim position (far away on the right side)
        cameraX = SCREEN_WIDTH / 2 - TRAIN_WIDTH / 2; // Initial camera position (centered)

        Timer timer = new Timer(10, this);
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Translate the graphics context based on camera position
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.translate(-cameraX, 0);


        try {
            trainStationImg = ImageIO.read(new File("C:\\Users\\akifn\\IdeaProjects\\EAFramework_SoSe23\\src\\main\\java\\de\\heaal\\eaf\\trainsimulator\\images\\trainStation.png"));
            startStationImg = ImageIO.read(new File("C:\\Users\\akifn\\IdeaProjects\\EAFramework_SoSe23\\src\\main\\java\\de\\heaal\\eaf\\trainsimulator\\images\\startStation.png"));
            trainImg = ImageIO.read(new File("C:\\Users\\akifn\\IdeaProjects\\EAFramework_SoSe23\\src\\main\\java\\de\\heaal\\eaf\\trainsimulator\\images\\trainSmall.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //draw Railroad
        g2d.setColor(Color.GRAY);
        g2d.drawLine(0, 326, SCREEN_WIDTH + aimX, 326);
        g2d.drawLine(0, 329, SCREEN_WIDTH + aimX, 329);

        // Draw aimStation (if visible)
        if (aimX >= cameraX && aimX <= cameraX + SCREEN_WIDTH) {
            g2d.drawImage(trainStationImg, aimX - 75, 240, null);
        }

        //draw start station
        g2d.drawImage(startStationImg, 290, 230, null);
        //draw train
        g2d.drawImage(trainImg, trainX - 65, SCREEN_HEIGHT / 2 - TRAIN_HEIGHT / 2, null);


        g2d.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Check if train has reached the aim
        if (trainX >= aimX) {
            // Stop the train and camera
            trainX = aimX;
            cameraX = aimX - SCREEN_WIDTH / 2 + TRAIN_WIDTH / 2;
        } else {
            // Move the train and camera towards the aim
            trainX += TRAIN_SPEED;
            cameraX = trainX - SCREEN_WIDTH / 2 + TRAIN_WIDTH / 2;
        }

        repaint();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Train Simulation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);

        testTrain panel = new testTrain();
        panel.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        frame.add(panel);

        frame.setVisible(true);
    }


}
