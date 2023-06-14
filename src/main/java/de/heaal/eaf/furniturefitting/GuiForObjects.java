package de.heaal.eaf.furniturefitting;

import javax.swing.*;
import java.awt.*;

public class GuiForObjects extends JPanel {
    private Polygon polygon;

    public GuiForObjects(Polygon polygon) {
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
        int[] xPoints1 = {200, 400, 400, 200};
        int[] yPoints1 = {200, 200, 400, 400};
        MovableObject m1 = new MovableObject(xPoints1, yPoints1, new Point(200, 200));

        // Create a polygon with some points
        int[] xPoints2 = {300, 500, 500, 300};
        int[] yPoints2 = {300, 300, 500, 500};
        MovableObject m2 = new MovableObject(xPoints2, yPoints2, new Point(300, 300));

        Polygon movableObject = CollisionTestField.getTrailOfPolygons(m1, m2);
//        Polygon movableObject = CollisionTestField.connectTwoPolygons(m1, m2);

        // Create a JFrame and add the movableObject visualization panel to it
        JFrame frame = new JFrame("Polygon Visualization");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new GuiForObjects(movableObject));
        frame.pack();
        frame.setVisible(true);

//        Thread.sleep(2000);
//        movableObject.turn(new Point(175, 125), 45);
//        frame.repaint();
//
//        for (int i = 0; i < 153; i++) {
//            Thread.sleep(20);
//            movableObject.turn(new Point(175, 125), 45);
//            frame.repaint();
//        }
    }
}
