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


}
