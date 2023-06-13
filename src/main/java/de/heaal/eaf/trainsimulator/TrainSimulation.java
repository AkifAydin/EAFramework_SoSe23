package de.heaal.eaf.trainsimulator;

import io.jenetics.prog.op.Op;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class TrainSimulation extends JPanel implements ActionListener {


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
    private int distanceTraveled;


    public TrainSimulation(int zielEntfernung, List<Op<Double>> first100Nodes) {
        trainX = SCREEN_WIDTH / 2 - TRAIN_WIDTH / 2; // Center train horizontally
        aimX = zielEntfernung; // Aim position (far away on the right side)
        cameraX = SCREEN_WIDTH / 2 - TRAIN_WIDTH / 2; // Initial camera position (centered)
        distanceTraveled = SCREEN_WIDTH / 2; // Initialize the distance traveled to Screen_Width/2 bcs we start in the middle of the screen

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
            trainStationImg = ImageIO.read(new File("src\\main\\java\\de\\heaal\\eaf\\trainsimulator\\images\\trainStation.png"));
            startStationImg = ImageIO.read(new File("src\\main\\java\\de\\heaal\\eaf\\trainsimulator\\images\\startStation.png"));
            trainImg = ImageIO.read(new File("src\\main\\java\\de\\heaal\\eaf\\trainsimulator\\images\\trainSmall.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //draw Railroad
        g2d.setColor(Color.GRAY);
        g2d.drawLine(0, 326, SCREEN_WIDTH + aimX, 326);
        g2d.drawLine(0, 329, SCREEN_WIDTH + aimX, 329);

        // Draw distance traveled
        g2d.setColor(Color.BLACK);
        g2d.drawString("DistanceTraveled: " + distanceTraveled+"km", cameraX + 10, 20);
        g2d.drawString("Goal: " + aimX+"km", cameraX + 10, 40);


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
            //distanceTraveled = aimX; // Update the distance traveled
        } else {
            // Move the train and camera towards the aim
            trainX += TRAIN_SPEED;
            cameraX = trainX - SCREEN_WIDTH / 2 + TRAIN_WIDTH / 2;
            distanceTraveled = trainX; // Update the distance traveled
        }

        repaint();
    }

    public void simulate() {
        JFrame frame = new JFrame("Train Simulation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);

        TrainSimulation panel = new TrainSimulation(aimX, null);
        panel.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        frame.add(panel);

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Train Simulation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);

        TrainSimulation panel = new TrainSimulation(2000, null);
        panel.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        frame.add(panel);

        frame.setVisible(true);
    }

}
