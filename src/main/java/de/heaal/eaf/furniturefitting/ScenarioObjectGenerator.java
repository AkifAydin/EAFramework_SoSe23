package de.heaal.eaf.furniturefitting;

import java.awt.*;
import java.awt.geom.Point2D;

public enum ScenarioObjectGenerator {
    INSTANCE;

    private static boolean SCENARIO_ONE = true;
    private static boolean SCENARIO_TWO = false;


    // Room 1
    private static Polygon room;
    private static Point2D destination;
    private static final int[] xPointsRoom_ONE = new int[]{200, 1720, 1720, 1050, 1050, 1690, 1690, 230, 230, 850, 850, 200, 200};
    private static final int[] yPointsRoom_ONE = new int[]{150, 150, 730, 730, 700, 700, 180, 180, 700, 700, 730, 730, 150};

    private static final int[] getxPointsOBJ_ONE = new int[]{850, 900, 900, 1050, 1050, 1000, 1000, 850};
    private static final int[] getyPointsOBJ_ONE = new int[]{750, 750, 850, 850, 1000, 1000, 900, 900};


    // Room 2
    private static final int[] xPointsRoom_TWO = new int[]{200, 930, 1720, 1720, 860, 860, 930, 930, 960, 960, 1690, 1690, 960, 960, 930, 930, 230, 230, 660, 660, 200, 200};
    private static final int[] yPointsRoom_TWO = new int[]{150, 150, 150, 730, 730, 700, 700, 500, 500, 700, 700, 180, 180, 300, 300, 180, 180, 700, 700, 730, 730, 150};

    private static final int[] getxPointsOBJ_TWO = new int[]{650, 700, 700, 850, 850, 800, 800, 650};
    private static final int[] getyPointsOBJ_TWO = new int[]{750, 750, 850, 850, 1000, 1000, 900, 900};


    public MovableObject getNewMovableObject() {
        if (SCENARIO_ONE) {
            return new MovableObject(new int[]{850, 900, 900, 1050, 1050, 1000, 1000, 850}, new int[]{750, 750, 850, 850, 1000, 1000, 900, 900}, new Point2D.Double(850, 750));
        } else if (SCENARIO_TWO) {
            //second room
            return new MovableObject(new int[]{650, 700, 700, 850, 850, 800, 800, 650}, new int[]{750, 750, 850, 850, 1000, 1000, 900, 900}, new Point2D.Double(650, 750));
        }
        return null;
    }

    public Polygon getRoom() {
        if (room == null) {
            if (SCENARIO_ONE) {
                room = new Polygon(xPointsRoom_ONE, yPointsRoom_ONE, xPointsRoom_ONE.length);
            } else if (SCENARIO_TWO) {
                room = new Polygon(xPointsRoom_TWO, yPointsRoom_TWO, xPointsRoom_TWO.length);
            }
        }

        return room;
    }

    public Point2D getDestination() {
        if (destination == null) {
            if (SCENARIO_ONE) {
                return new Point2D.Double(910, 400);
//                destination = getNewMovableObject().getCenterPoint();
//                destination = new Point2D.Double(destination.getX(), destination.getY());
            } else if (SCENARIO_TWO) {
                return new Point2D.Double(1300, 400);
//                destination = getNewMovableObject().getCenterPoint();
//                destination = new Point2D.Double(destination.getX(), destination.getY());
            }
        }

        return destination;
    }
}
