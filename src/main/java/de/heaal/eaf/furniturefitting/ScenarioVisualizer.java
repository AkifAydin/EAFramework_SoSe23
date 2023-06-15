package de.heaal.eaf.furniturefitting;

import javax.swing.*;
import java.awt.*;
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
            g.drawPolygon(p);
        }

        if (movableObject != null) {
            g.drawPolygon(movableObject);
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(900, 600);
    }
}
