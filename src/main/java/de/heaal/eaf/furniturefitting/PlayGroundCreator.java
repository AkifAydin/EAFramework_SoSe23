package de.heaal.eaf.furniturefitting;

import java.awt.*;

import javax.swing.*;


public class PlayGroundCreator extends JFrame {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 700;

    private int[] xPoints;
    private int[] yPoints;
    private int[] xPointsRaum;
    private int[] yPointsRaum;

    public PlayGroundCreator() {
        setSize(WIDTH, HEIGHT);  //Size of the Frame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        createLShape();
    }

    private void createLShape() {
        //xPoints = new int[]{200, 225, 225, 275, 300, 300, 200};
        xPoints = new int[]{100, 125, 125, 175, 200, 200, 100};
        //yPoints = new int[]{200, 200, 225, 225, 225, 250, 250};
        yPoints = new int[]{610, 610, 635, 635, 635, 660, 660};
        //first
        xPointsRaum = new int[]{  9, 375, 300, 300, 300, 200, 300, 300,   9,   9, 150,
                9,   9, 400, 300, 300, 300, 300,     9,  9, 300, 300, 300, 400, 400, 400,
                600, 600, 600, 790, 790, 600, 600, 600, 570, 600, 600, 500, 500, 500, 450,
                500, 500, 530, 400, 400, 400, 500, 500, 500, 600, 600, 600, 790, 790, 600,
                600, 600, 500, 500, 500, 425, 790, 790,   9,   9};
        yPointsRaum = new int[]{575, 575, 575, 400, 450, 450, 450, 575, 575, 450, 450,
                450, 250, 250, 250, 350, 150, 250, 250, 35,  35, 100,  35,  35,  80,  35,
                 35, 100,  35,  35, 250, 250, 150, 180, 180, 180, 250, 250, 250, 250, 250,
                250, 180, 180, 180, 120, 180, 180, 350, 250, 250, 450, 250, 250, 575, 575,
                500, 575, 575, 400, 575, 575, 575, 690, 690, 575};
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        Graphics2D g2d = (Graphics2D) g;

        // Set a thicker stroke
        Stroke stroke = new BasicStroke(3f); // Adjust the line thickness as needed
        g2d.setStroke(stroke);

        g2d.setColor(Color.BLACK);
        g2d.drawPolygon(xPointsRaum, yPointsRaum, xPointsRaum.length);
        g2d.drawPolygon(xPoints, yPoints, xPoints.length);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(PlayGroundCreator::new);
    }
}
