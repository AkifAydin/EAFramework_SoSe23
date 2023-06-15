package de.heaal.eaf.furniturefitting;

import java.awt.*;
import java.awt.geom.Point2D;

public enum ScenarioObjectGenerator {
    INSTANCE;

    // Movable Object
    private static final int[] xPointsObject = new int[]{60, 80, 80, 120, 120, 100, 100, 60};
    private static final int[] yPointsObject = new int[]{150, 150, 180, 180, 230, 230, 200, 200};
    private static final Point2D centerPoint = new Point2D.Double(90.0, 190.0);


    // Room
    private static Polygon room;
    private static Point2D destination;
    private static final int[] xPointsRoom = new int[]{0, 160, 160, 100, 100, 150, 150, 10, 10, 50, 50, 0};
    private static final int[] yPointsRoom = new int[]{0, 0, 120, 120, 110, 110, 10, 10, 110, 110, 120, 120};

    public MovableObject getNewMovableObject() {
        return new MovableObject(
                new int[]{60, 80, 80, 120, 120, 100, 100, 60},
                new int[]{150, 150, 180, 180, 230, 230, 200, 200},
                new Point2D.Double(90.0, 190.0));
    }

    public Polygon getRoom() {
        if (room == null) {
            room = new Polygon(xPointsRoom, yPointsRoom, xPointsRoom.length);
        }

        return room;
    }

    public Point2D getDestination() {
        if (destination == null) {
            destination = new Point2D.Double(80.0, 50.0);
        }

        return destination;
    }
}
