package de.heaal.eaf.furniturefitting;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.PathIterator;

public class CollisionTestField {

    public static void main(String[] args) {
        // Create a polygon with some points
        int[] xPoints1 = {5, 10, 10, 5};
        int[] yPoints1 = {9, 9, 14, 14};

        MovableObject mo1 = new MovableObject(xPoints1, yPoints1, new Point(5, 9));

        // Create a polygon with some points
        int[] xPoints2 = {12, 17, 17, 12};
        int[] yPoints2 = {3, 3, 8, 8};

        MovableObject mo2 = new MovableObject(xPoints2, yPoints2, new Point(12, 3));

        // Create a polygon with some points
        int[] otherXPoints = {3, 11, 11};
        int[] otherYPoints = {8, 1, 8};
        Polygon object = new Polygon(otherXPoints, otherYPoints, 3);

        System.out.println(polygonsIntersect(mo2, object));
    }

    /**
     * IMPORTANT!: Both polygons have to be identical except for their position!
     */
    public static Polygon getTrailOfPolygons(Polygon from, Polygon to) {
        Polygon result = new Polygon();

        int lastIdx = from.npoints - 1;

        // To avoid Edge cases
        result = connectTwoPolygons(result, from);

        // Purposely avoid letting i == last idx of points because inside the loop we have i + 1
        for (int i = 0; i < lastIdx; i++) {
            Polygon polyPart = new Polygon();

            polyPart.addPoint(from.xpoints[i], from.ypoints[i]);
            polyPart.addPoint(from.xpoints[i + 1], from.ypoints[i + 1]);

            polyPart.addPoint(to.xpoints[i + 1], to.ypoints[i + 1]);
            polyPart.addPoint(to.xpoints[i], to.ypoints[i]);

            result = connectTwoPolygons(result, polyPart);
        }

        // Add the polygon part that is connected from the 0th to last idx of those vertices
        Polygon polyPart = new Polygon();

        polyPart.addPoint(from.xpoints[lastIdx], from.ypoints[lastIdx]);
        polyPart.addPoint(from.xpoints[0], from.ypoints[0]);

        polyPart.addPoint(to.xpoints[0], to.ypoints[0]);
        polyPart.addPoint(to.xpoints[lastIdx], to.ypoints[lastIdx]);

        result = connectTwoPolygons(result, polyPart);

        return result;
    }

    public static Polygon connectTwoPolygons(Polygon p1, Polygon p2) {
        Area area1 = new Area(p1);
        Area area2 = new Area(p2);
        area1.add(area2);
        PathIterator pi = area1.getPathIterator(null);

        Polygon result = new Polygon();

        while (!pi.isDone()) {
            double[] coordinates = new double[6];
            int type = pi.currentSegment(coordinates);
            if (type == PathIterator.SEG_LINETO || type == PathIterator.SEG_MOVETO) {
                result.addPoint((int) coordinates[0], (int) coordinates[1]);
            }
            pi.next();
        }

        return result;
    }

    public static boolean polygonsIntersect(Polygon polygon1, Polygon polygon2) {
        Area area1 = new Area(polygon1);
        Area area2 = new Area(polygon2);

        area1.intersect(area2);
        return !area1.isEmpty();
    }

}
