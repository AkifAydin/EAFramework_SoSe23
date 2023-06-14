package de.heaal.eaf.furniturefitting;

import java.awt.*;

public enum SzenarioObjectGenerator {
    INSTANCE;

    private static final int[] xPointsObject = new int[]{100, 125, 125, 175, 200, 200, 100};
    private static final int[] yPointsObject = new int[]{610, 610, 635, 635, 635, 660, 660};
    private static final Point centerPoint = new Point(100, 610);


    private static Polygon room;
    private static final int[] xPointsRoom = new int[]{  9, 375, 300, 300, 300, 200, 300, 300,   9,   9, 150,
            9,   9, 400, 300, 300, 300, 300,     9,  9, 300, 300, 300, 400, 400, 400,
            600, 600, 600, 790, 790, 600, 600, 600, 570, 600, 600, 500, 500, 500, 450,
            500, 500, 530, 400, 400, 400, 500, 500, 500, 600, 600, 600, 790, 790, 600,
            600, 600, 500, 500, 500, 425, 790, 790,   9,   9};
    private static final int[] yPointsRoom = new int[]{575, 575, 575, 400, 450, 450, 450, 575, 575, 450, 450,
            450, 250, 250, 250, 350, 150, 250, 250, 35,  35, 100,  35,  35,  80,  35,
            35, 100,  35,  35, 250, 250, 150, 180, 180, 180, 250, 250, 250, 250, 250,
            250, 180, 180, 180, 120, 180, 180, 350, 250, 250, 450, 250, 250, 575, 575,
            500, 575, 575, 400, 575, 575, 575, 690, 690, 575};

    public static MovableObject getNewMovableObject() {
        return new MovableObject(xPointsObject, yPointsObject, centerPoint);
    }

    public static Polygon getRoom() {
        if (room != null) {
            return room;
        } else {
            room = new Polygon(xPointsRoom, yPointsRoom, xPointsRoom.length);
            return room;
        }
    }
}
