package de.heaal.eaf.furniturefitting;

import io.jenetics.prog.op.Const;
import io.jenetics.prog.op.Op;
import io.jenetics.util.ISeq;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MovableObject extends Polygon {

    /**
     * A center point in the object, which has to align with a target point in the room
     */
    protected Point2D centerPoint;

    /**
     * To save values for the fitness calculation
     */
    protected FitnessMeasures fitnessMeasures = new FitnessMeasures();

    /**
     * A list with generated points on which turning operations can take place
     */
    private List<Point2D.Double> turnPoints = new ArrayList<>();

    /**
     * A marker if the last move was valid. Important if a method was called with invalid parameter.
     */
    private boolean lastMoveWasValid = false; // TODO Integrate

    private MovableObjectCache movableObjectCache = null;

    protected double[] xDoublePoints;
    protected double[] yDoublePoints;

    private MovableObject() {}

    public MovableObject(int[] xPoints, int[] yPoints, Point2D centerPoint) {
        super(xPoints, yPoints, xPoints.length);
        this.centerPoint = centerPoint;
        this.xDoublePoints = Arrays.stream(xPoints).asDoubleStream().toArray();
        this.yDoublePoints = Arrays.stream(yPoints).asDoubleStream().toArray();
        initTurnPoints();

        movableObjectCache = new MovableObjectCache();
    }

    public double getDistanceToPoint(Point2D point) {
        return centerPoint.distance(point);
    }

    public void moveToByAngleAndDist(double angleDegrees, double distance) {
        // Adjust fitness measures
        fitnessMeasures.incrementMoves();
        fitnessMeasures.addTraveledDistance(distance);

        // Do the moving
        // Convert angle from degrees to radians
        double angleRadians = Math.toRadians(angleDegrees);

        // Calculate the horizontal and vertical components
        double deltaX = distance * Math.cos(angleRadians);
        double deltaY = distance * Math.sin(angleRadians);

        movePointsByDelta(deltaX, deltaY);
    }

    public void moveToByDelta(double deltaX, double deltaY) {
        // Adjust fitness measures
        fitnessMeasures.incrementMoves();
        double distance = centerPoint.distance(centerPoint.getX() + deltaX, centerPoint.getY() + deltaY);
        fitnessMeasures.addTraveledDistance(distance);

        // Do the moving
        movePointsByDelta(deltaX, deltaY);
    }

    private void movePointsByDelta(double deltaX, double deltaY) {
        cacheCurrentState();

        // Move the polygon points
        for (int i = 0; i < npoints; i++) {
            // Calculate the new coordinates
            xDoublePoints[i] += deltaX;
            yDoublePoints[i] += deltaY;
        }

        // Move the turn points
        for (Point2D p : turnPoints) {
            p.setLocation(p.getX() + deltaX, p.getY() + deltaY);
        }

        // Move the center point
        centerPoint.setLocation(centerPoint.getX() + deltaX, centerPoint.getY() + deltaY);

        adjustPointsInUnderlyingObject();
    }

    public void turn(double turnPointIdx, double angle) {
        // TODO Maybe let the index 0 be ignored because other operations
        // Convert the point index to an absolute value and check if the index exists in the list.
        int turnPointIdxInt = Math.abs((int) turnPointIdx);
        if (turnPointIdxInt >= turnPoints.size()) {
            return;
        }

        cacheCurrentState();
        fitnessMeasures.incrementTurns();
        Point2D.Double referencePoint = turnPoints.get(turnPointIdxInt);

        double centerX = referencePoint.getX();
        double centerY = referencePoint.getY();
        double radians = Math.toRadians(angle);
        double cosAngle = Math.cos(radians);
        double sinAngle = Math.sin(radians);

        // Move the polygon points
        for (int i = 0; i < xDoublePoints.length; i++) {
            double deltaX = xDoublePoints[i] - centerX;
            double deltaY = yDoublePoints[i] - centerY;

            // Apply rotation
            double rotatedX = (deltaX * cosAngle) - (deltaY * sinAngle);
            double rotatedY = (deltaX * sinAngle) + (deltaY * cosAngle);

            // Translate back to the original position
            xDoublePoints[i] = rotatedX + centerX;
            yDoublePoints[i] = rotatedY + centerY;
        }


        // Move the turn points
        for (Point2D.Double point : turnPoints) {
            double deltaX = point.getX() - centerX;
            double deltaY = point.getY() - centerY;

            // Apply rotation
            double rotatedX = (deltaX * cosAngle) - (deltaY * sinAngle);
            double rotatedY = (deltaX * sinAngle) + (deltaY * cosAngle);

            // Translate back to the original position
            point.setLocation(rotatedX + centerX, rotatedY + centerY);
        }

        // Move the center point
        double deltaX = centerPoint.getX() - centerX;
        double deltaY = centerPoint.getY() - centerY;

        // Apply rotation
        double rotatedX = (deltaX * cosAngle) - (deltaY * sinAngle);
        double rotatedY = (deltaX * sinAngle) + (deltaY * cosAngle);

        // Translate back to the original position
        centerPoint.setLocation(rotatedX + centerX, rotatedY + centerY);

        adjustPointsInUnderlyingObject();
    }

    private void adjustPointsInUnderlyingObject() {
        int len = this.xpoints.length;

        for (int i = 0; i < len; i++) {
            xpoints[i] = (int) Math.round(xDoublePoints[i]);
            ypoints[i] = (int) Math.round(yDoublePoints[i]);
        }
    }

    public FitnessMeasures getFitnessMeasures() {
        return this.fitnessMeasures;
    }

    public Polygon getMinimalCopy() {
        return new Polygon(Arrays.copyOf(this.xpoints, this.npoints), Arrays.copyOf(this.ypoints, this.npoints), this.npoints);
    }

    public Point2D getCenterPoint() {
        return centerPoint;
    }

    public static ISeq<Op<Double>> getPossibleTerminals() {
        List<Const<Double>> terminals = new ArrayList<>();

        for (int i = -15; i <= 15; i++) { // TODO HARDCODED
            terminals.add(Const.of((double) i));
        }

        return ISeq.of(terminals);
    }

    /**
     * Initializes the turn points.
     * <p>
     * TODO Think about caching this. It is always the same in the beginning.
     */
    private void initTurnPoints() {
        Rectangle bounds = this.getBounds();

        int xGridSize = bounds.width / 5; // TODO HARDCODED
        int yGridSize = bounds.height / 5;

        // Iterate over the points in the bounding rectangle with the given grid size
        for (int x = bounds.x; x <= bounds.x + bounds.width; x += xGridSize) {
            for (int y = bounds.y; y <= bounds.y + bounds.height; y += yGridSize) {
                Point2D.Double point = new Point2D.Double(x, y);

                // Check if the point is within the polygon
                if (this.contains(point)) {
                    turnPoints.add(point);
                }
            }
        }

        if (turnPoints.size() == 0) {
            throw new RuntimeException("Turn Point init failed.");
        }
    }

    public void revertLastStep() {
        xDoublePoints = Arrays.copyOf(movableObjectCache.xDoublePoints, this.xDoublePoints.length);
        yDoublePoints = Arrays.copyOf(movableObjectCache.yDoublePoints, this.yDoublePoints.length);
        centerPoint = new Point2D.Double(movableObjectCache.centerPoint.getX(), movableObjectCache.centerPoint.getY());
        adjustPointsInUnderlyingObject();

        fitnessMeasures = movableObjectCache.fitnessMeasures;
    }

    /**
     * Needed to revert the last step, that was executed on the object.
     */
    private void cacheCurrentState() {
        movableObjectCache.xDoublePoints = Arrays.copyOf(this.xDoublePoints, this.xDoublePoints.length);
        movableObjectCache.yDoublePoints = Arrays.copyOf(this.yDoublePoints, this.yDoublePoints.length);
        movableObjectCache.centerPoint = new Point2D.Double(centerPoint.getX(), centerPoint.getY());

        movableObjectCache.fitnessMeasures = fitnessMeasures.getCopy();
    }

    private class MovableObjectCache extends MovableObject {}
}
