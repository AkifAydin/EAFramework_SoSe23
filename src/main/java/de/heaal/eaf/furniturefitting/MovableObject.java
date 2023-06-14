package de.heaal.eaf.furniturefitting;

import java.awt.*;

public class MovableObject extends Polygon {

    private Point centerPoint;

    public MovableObject(int[] xPoints, int[] yPoints, Point centerPoint) {
        super(xPoints, yPoints, xPoints.length);
        this.centerPoint = centerPoint;
    }

    public double getDistanceToPoint(Point point) {
        return centerPoint.distance(point);
    }

    public void moveToByAngleAndDist(double angleDegrees, double distance) {
        // Convert angle from degrees to radians
        double angleRadians = Math.toRadians(angleDegrees);

        // Calculate the horizontal and vertical components
        long horizontalComponent = Math.round(distance * Math.cos(angleRadians));
        long verticalComponent = Math.round(distance * Math.sin(angleRadians));

        for (int i = 0; i < npoints; i++) {
            // Calculate the new coordinates
            xpoints[i] = (int) (xpoints[i] + horizontalComponent);
            ypoints[i] = (int) (ypoints[i] + verticalComponent);
        }

        centerPoint.x = (int) (centerPoint.x + horizontalComponent);
        centerPoint.y = (int) (centerPoint.y + verticalComponent);
    }

    public void moveToByDelta(double deltaX, double deltaY) {
        for (int i = 0; i < npoints; i++) {
            // Calculate the new coordinates
            xpoints[i] += (int) deltaX;
            ypoints[i] += (int) deltaY;
        }

        centerPoint.x += (int) deltaX;
        centerPoint.y += (int) deltaY;
    }

    public void turn(double refX, double refY, double angle) {
        Point referencePoint = new Point((int) refX, (int) refY);

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


}
