package de.heaal.eaf.furniturefitting;

import java.awt.*;

public class MovableObject extends Polygon {

    public MovableObject(int[] xPoints, int[] yPoints, int numPoints) {
        super(xPoints, yPoints, numPoints);
    }

    public void moveTo(double angleDegrees, double distance) {
        // Convert angle from degrees to radians
        double angleRadians = Math.toRadians(angleDegrees);

        // Calculate the horizontal and vertical components
        long horizontalComponent = Math.round(distance * Math.cos(angleRadians));
        long verticalComponent = Math.round(distance * Math.sin(angleRadians));

        int pointLen = xpoints.length;

        for (int i = 0; i < pointLen; i++) {
            // Calculate the new coordinates
            xpoints[i] = (int) (xpoints[i] + horizontalComponent);
            ypoints[i] = (int) (ypoints[i] + verticalComponent);
        }
    }

    public void moveTo(int onXAxis, int onYAxis) {
        // Convert angle from degrees to radians
//        double angleRadians = Math.toRadians(angleDegrees);
//
//        // Calculate the horizontal and vertical components
//        long horizontalComponent = Math.round(distance * Math.cos(angleRadians));
//        long verticalComponent = Math.round(distance * Math.sin(angleRadians));
//
//        int pointLen = xpoints.length;
//
//        for (int i = 0; i < pointLen; i++) {
//            // Calculate the new coordinates
//            xpoints[i] = (int) (xpoints[i] + horizontalComponent);
//            ypoints[i] = (int) (ypoints[i] + verticalComponent);
//        }
    }

    public void turn(Point referencePoint, double angle) {
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
