package de.heaal.eaf.trainsimulator;

public class TrainVerySimple {

    /**
     * As m/s
     */
    private final double maxSpeed;

    /**
     * As mliter or wh
     */
    private double powerLevel;

    /**
     * As mliter or wh per meter
     */
    private final double powerConsumption;

    private double elapsedSeconds = 0;
    private double elapsedMeters = 0;
    private double currentSpeed = 0;

    public TrainVerySimple(double maxSpeed, double powerLevel, double powerConsumption) {
        this.maxSpeed = maxSpeed;
        this.powerLevel = powerLevel;
        this.powerConsumption = powerConsumption;
    }

    public double getElapsedMeters() {
        return elapsedMeters;
    }

    public double getElapsedSeconds() {
        return elapsedSeconds;
    }

    public double getPowerLevel() {
        return powerLevel;
    }

    public double increaseSpeedBy(double mPerS) {
        double finalSpeed = currentSpeed + mPerS;

        if (finalSpeed > maxSpeed) {
            currentSpeed = maxSpeed;
        } else {
            currentSpeed = finalSpeed;
        }

        return finalSpeed;
    }

    public double deCreaseSpeedBy(double mPerS) {
        double finalSpeed = currentSpeed - mPerS;

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
    public double justDrive(double seconds) {
        double metersToDrive = currentSpeed * seconds;
        double powerConsumption = getPowerConsumptionPerMeter() * metersToDrive;

        if (powerLevel < powerConsumption) {
            throw new IllegalArgumentException("If you drive this much, you will run out of fuel!");
        }

        elapsedSeconds += seconds;
        elapsedMeters += metersToDrive;
        powerLevel -= powerConsumption;

        return powerLevel;
    }

}
