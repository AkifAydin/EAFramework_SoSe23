package de.heaal.eaf.hatesting;

import java.awt.*;
import java.awt.geom.Area;

public class TwoDimCollision {
    public static void main(String[] args) {
        // Create two polygons
        Polygon polygon1 = new Polygon();
        polygon1.addPoint(100, 100);
        polygon1.addPoint(200, 100);
        polygon1.addPoint(200, 200);

        Polygon polygon2 = new Polygon();
        polygon2.addPoint(150, 150);
        polygon2.addPoint(250, 150);
        polygon2.addPoint(250, 250);

        // Check if the polygons are touching
        if (polygonsIntersect(polygon1, polygon2)) {
            System.out.println("Polygons are touching");
        } else {
            System.out.println("Polygons are not touching");
        }
    }

    public static boolean polygonsIntersect(Polygon polygon1, Polygon polygon2) {
        Area area1 = new Area(polygon1);
        Area area2 = new Area(polygon2);
        area1.intersect(area2);
        return !area1.isEmpty();
    }
}
