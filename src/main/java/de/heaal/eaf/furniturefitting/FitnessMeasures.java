package de.heaal.eaf.furniturefitting;

/**
 * IMPORTANT: Wall touches and remaining distance is calculated in the {@link MovableObjectFitnessFunction}.
 * Everything else will be set by the {@link MovableObject} itself.
 */
public class FitnessMeasures {

    public static final int MAX_NODES = 300;

    public static final double REMAINING_DISTANCE_TOLERANCE = 50.0;
    public static final double WEIGHT_REMAINING_DISTANCE = 30.0;
    public static final double WEIGHT_REMAINING_DISTANCE_CONST = 0.0;
    public static final double WEIGHT_TRAVELED_DISTANCE = 0.0;
    public static final double WEIGHT_MOVES = 0.0;
    public static final double WEIGHT_TURNS = 0.0;
    public static final double WEIGHT_WALL_TOUCHES_CONST = 50_000.0;
    public static final double WEIGHT_WALL_TOUCHES = 0.0;

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

    public FitnessMeasures getCopy() {
        FitnessMeasures fm = new FitnessMeasures();
        fm.traveledDistance = traveledDistance;
        fm.numberOfMoves = numberOfMoves;
        fm.numberOfTurns = numberOfTurns;
        fm.numberOfWallTouches = numberOfWallTouches;

        return fm;
    }
}
