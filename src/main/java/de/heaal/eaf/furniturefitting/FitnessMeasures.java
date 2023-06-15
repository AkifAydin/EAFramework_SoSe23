package de.heaal.eaf.furniturefitting;

/**
 * IMPORTANT: Wall touches and remaining distance is calculated in the {@link MovableObjectFitnessFunction}.
 * Everything else will be set by the {@link MovableObject} itself.
 */
public class FitnessMeasures {

    public static final double REMAINING_DISTNACE_TOLLERANCE = 1.0;
    public static final double WEIGHT_REMAINING_DISTANCE = 3.0;
    public static final double WEIGHT_REMAINING_DISTANCE_CONST = 500.0;
    public static final double WEIGHT_TRAVELED_DISTANCE = 1.2;
    public static final double WEIGHT_MOVES = 1.6; // TODO Another way to reduce the tree?
    public static final double WEIGHT_TURNS = 1.7;
    public static final double WEIGHT_WALL_TOUCHES_CONST = 1.0;
    public static final double WEIGHT_WALL_TOUCHES = 1.0;

    private double traveledDistance = 0.0;
    private int numberOfMoves = 0;
    private int numberOfTurns = 0;
    private int numberOfWallTouches = 0;

    public void addTraveledDistance(double distToAdd) {
        this.traveledDistance += distToAdd;
    }

    public void incrementMoves() {
        this.numberOfMoves++;
    }

    public void incrementTurns() {
        this.numberOfTurns++;
    }

    public void incrementWallTouches() {
        this.numberOfWallTouches++;
    }

    public double getTraveledDistance() {
        return traveledDistance;
    }

    public int getNumberOfMoves() {
        return numberOfMoves;
    }

    public int getNumberOfTurns() {
        return numberOfTurns;
    }

    public int getNumberOfWallTouches() {
        return numberOfWallTouches;
    }
}
