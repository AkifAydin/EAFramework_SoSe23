package de.heaal.eaf.trainsimulator;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class Train {

    /** As m/s */
    private final float maxSpeed;
    /** As m/s^2 */
    private final float acceleration;
    /** As liter */
    private float fuelLevel = -1.0f;

    private float elapsedSeconds = 0.0f;
    private float currentSpeed = 0.0f;
    private float powerConsumption = 0.0f;

    private BufferedImage trainImg;

    public Train(float maxSpeed, float acceleration, float fuelLevel) {
        this.maxSpeed = maxSpeed;
        this.fuelLevel = fuelLevel;
        this.acceleration = acceleration;
        //trainImg = ImageIO.read(new File("train.png"));
    }

    public void accelerate(float seconds) {
        elapsedSeconds += seconds;

        float finalSpeed = currentSpeed + (acceleration * seconds);

    }

    public void slowDown(float seconds) {
        elapsedSeconds += seconds;
        float finalSpeed = currentSpeed + (acceleration * seconds);
    }

    public float powerConsumption(){
        return (float) (Math.pow(currentSpeed, 2) * powerConsumption);
    }

    //Name ideas:
    //inMotion
    //isRunning
    //isMoving
    //doNothing
    public void inMotion(float seconds) {
        elapsedSeconds += seconds;


    }



}
