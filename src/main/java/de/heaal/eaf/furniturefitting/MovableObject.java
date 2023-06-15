package de.heaal.eaf.furniturefitting;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class MovableObject extends Polygon {

    /** A center point in the object, which has to align with a target point in the room */
    private Point centerPoint;

    /** To save values for the fitness calculation */
    private FitnessMeasures fitnessMeasures;

    /** A list with generated points on which turning operations can take place */
    private List<Point> turnPoints;


    public MovableObject(int[] xPoints, int[] yPoints, Point centerPoint) {
        super(xPoints, yPoints, xPoints.length);
        this.centerPoint = centerPoint;
        initTurnPoints();
    }

    public double getDistanceToPoint(Point point) {
        return centerPoint.distance(point);
    }

    public void moveToByAngleAndDist(double angleDegrees, double distance) {
        // Adjust fitness measures
        fitnessMeasures.incrementMoves();
        fitnessMeasures.addTraveldedDistance(distance);

        // Do the moving
        // Convert angle from degrees to radians
        double angleRadians = Math.toRadians(angleDegrees);

        // Calculate the horizontal and vertical components
        int deltaX = (int) Math.round(distance * Math.cos(angleRadians));
        int deltaY = (int) Math.round(distance * Math.sin(angleRadians));

        movePointsByDelta(deltaX, deltaY);
    }

    public void moveToByDelta(double deltaX, double deltaY) {
        int deltaXInt = (int) deltaX;
        int deltaYInt = (int) deltaY;

        // Adjust fitness measures
        fitnessMeasures.incrementMoves();
        double distance = centerPoint.distance(new Point(centerPoint.x + deltaXInt, centerPoint.y + deltaYInt));
        fitnessMeasures.addTraveldedDistance(distance);

        // Do the moving
        movePointsByDelta(deltaXInt, deltaYInt);
    }

    private void movePointsByDelta(int deltaX, int deltaY) {
        // Move the polygon points
        for (int i = 0; i < npoints; i++) {
            // Calculate the new coordinates
            xpoints[i] += deltaX;
            ypoints[i] += deltaY;
        }

        // Move the turn points
        for (Point p : turnPoints) {
            p.x += deltaX;
            p.y += deltaY;
        }

        // Move the center point
        centerPoint.x += deltaX;
        centerPoint.y += deltaY;
    }

    public void turn(double turnPointIdx, double angle) {
        fitnessMeasures.incrementTurns();

        Point referencePoint = turnPoints.get((int) turnPointIdx);

        double theta = angle * (Math.PI / 180); // Convert angle to radians

        int pointLen = xpoints.length;

        for (int i = 0; i < pointLen; i++) {
            int dx = xpoints[i] - referencePoint.x;
            int dy = ypoints[i] - referencePoint.y;

            long newX = Math.round(referencePoint.x + dx * Math.cos(theta) - dy * Math.sin(theta));
            long newY = Math.round(referencePoint.y + dy * Math.cos(theta) + dx * Math.sin(theta));

            xpoints[i] = (int) newX;
            ypoints[i] = (int) newY;
        }
    }

    public FitnessMeasures getFitnessMeasures() {
        return this.fitnessMeasures;
    }

    public Polygon getMinimalCopy() {
        return new Polygon(Arrays.copyOf(this.xpoints, this.npoints), Arrays.copyOf(this.ypoints, this.npoints), this.npoints);
    }

    /**
     * Initializes the turn points.
     *
     * TODO Think about caching this. It is always the same in the beginning.
     */
    private void initTurnPoints() {
        Rectangle bounds = this.getBounds();

        int xGridSize = bounds.width / 6;
        int yGridSize = bounds.height / 6;

        // Iterate over the points in the bounding rectangle with the given grid size
        for (int x = bounds.x; x <= bounds.x + bounds.width; x += xGridSize) {
            for (int y = bounds.y; y <= bounds.y + bounds.height; y += yGridSize) {
                Point point = new Point(x, y);

                // Check if the point is within the polygon
                if (this.contains(point)) {
                    turnPoints.add(point);
                }
            }
        }

        if(turnPoints.size() == 0) {
            throw new RuntimeException("Turn Point init failed.");
        }
    }

}
