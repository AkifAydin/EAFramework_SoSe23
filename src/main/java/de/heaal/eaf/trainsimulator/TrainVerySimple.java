package de.heaal.eaf.trainsimulator;

public class TrainVerySimple {

    /**
     * As m/s
     */
    private final int maxSpeed;

    /**
     * As mliter or wh
     */
    private int powerLevel;

    /**
     * As mliter or wh per meter
     */
    private final int powerConsumption;

    private int elapsedSeconds = 0;
    private int elapsedMeters = 0;
    private int currentSpeed = 0;

    public TrainVerySimple(int maxSpeed, int powerLevel, int powerConsumption) {
        this.maxSpeed = maxSpeed;
        this.powerLevel = powerLevel;
        this.powerConsumption = powerConsumption;
    }

    public int getElapsedMeters() {
        return elapsedMeters;
    }

    public int getElapsedSeconds() {
        return elapsedSeconds;
    }

    public int getPowerLevel() {
        return powerLevel;
    }

    public int increaseSpeedBy(int mPerS) {
        int finalSpeed = currentSpeed + mPerS;

        if (finalSpeed > maxSpeed) {
            currentSpeed = maxSpeed;
        } else {
            currentSpeed = finalSpeed;
        }

        return finalSpeed;
    }

    public int deCreaseSpeedBy(int mPerS) {
        int finalSpeed = currentSpeed - mPerS;

        if (finalSpeed < 0) {
            currentSpeed = 0;
        } else {
            currentSpeed = finalSpeed;
        }

        return finalSpeed;
    }

    public int getPowerConsumptionPerMeter() {
        return (int) Math.round(Math.pow(currentSpeed, 2) * powerConsumption);
    }

    //Name ideas:
    //inMotion
    //isRunning
    //isMoving
    //doNothing
    public int justDrive(int seconds) {
        int metersToDrive = currentSpeed * seconds;
        int powerConsumption = getPowerConsumptionPerMeter() * metersToDrive;

        if (powerLevel < powerConsumption) {
            throw new IllegalArgumentException("If you drive this much, you will run out of fuel!");
        }

        elapsedSeconds += seconds;
        elapsedMeters += metersToDrive;
        powerLevel -= powerConsumption;

        return powerLevel;
    }

}
