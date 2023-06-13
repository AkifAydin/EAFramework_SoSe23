package de.heaal.eaf.furniturefitting;

import javax.swing.*;
import java.awt.*;

public class GuiForObjects extends JPanel {
    private MovableObject polygon;

    public GuiForObjects(MovableObject polygon) {
        this.polygon = polygon;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawPolygon(polygon);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(400, 400); // Set the preferred size of the panel
    }

    public static void main(String[] args) throws InterruptedException {
        // Create a polygon with some points
        int[] xPoints = {50, 150, 200, 100};
        int[] yPoints = {50, 100, 150, 200};
        int numPoints = 4;
        MovableObject polygon = new MovableObject(xPoints, yPoints, numPoints);

        // Create a JFrame and add the polygon visualization panel to it
        JFrame frame = new JFrame("Polygon Visualization");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new GuiForObjects(polygon));
        frame.pack();
        frame.setVisible(true);

        Thread.sleep(1000);
        polygon.moveTo(0, 150);
        frame.repaint();

        Thread.sleep(100);
        polygon.moveTo(45, 150);
        frame.repaint();

        Thread.sleep(100);
        polygon.moveTo(90, 150);
        frame.repaint();

        Thread.sleep(100);
        polygon.moveTo(135, 150);
        frame.repaint();

        Thread.sleep(100);
        polygon.moveTo(180, 150);
        frame.repaint();

        Thread.sleep(100);
        polygon.moveTo(225, 150);
        frame.repaint();

        Thread.sleep(100);
        polygon.moveTo(270, 150);
        frame.repaint();

        Thread.sleep(100);
        polygon.moveTo(315, 150);
        frame.repaint();

    }
}
