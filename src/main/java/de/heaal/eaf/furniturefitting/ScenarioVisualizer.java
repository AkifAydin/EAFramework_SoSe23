package de.heaal.eaf.furniturefitting;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ScenarioVisualizer extends JPanel {
    private List<Polygon> roomObjects = new ArrayList<>();
    private MovableObject movableObject = null;

    public void addRoomObject(Polygon p) {
        roomObjects.add(p);
    }

    public void setMovableObject(MovableObject mo) {
        this.movableObject = mo;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (Polygon p : roomObjects) {
            g.setColor(Color.gray);
            g.fillPolygon(p);
        }

        if (movableObject != null) {
            g.setColor(Color.pink);
            g.fillPolygon(movableObject);
        }

        BufferedImage goalImg ;
        try {
            goalImg = ImageIO.read(new File("src\\main\\java\\de\\heaal\\eaf\\furniturefitting\\images\\green_flag.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Point2D destination = ScenarioObjectGenerator.INSTANCE.getDestination();
        g.drawImage(goalImg,(int)destination.getX(), (int)destination.getY(),null);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(900, 600);
    }
}
