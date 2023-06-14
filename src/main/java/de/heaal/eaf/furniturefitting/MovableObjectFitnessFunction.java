package de.heaal.eaf.furniturefitting;

import io.jenetics.Genotype;
import io.jenetics.prog.ProgramGene;

import java.awt.*;
import java.util.List;


public class MovableObjectFitnessFunction {

    public static double calcFitness(Genotype<ProgramGene<Double>> ind) {
//        Zug zug = executeFunctionOnTrain(ind, MAX_NODES);
//
//        return calcFitnessWithWeights(zug); //  * WEIGHT_DIST + zug.getEnergie() * WEIGHT_ENERGY + zug.getElapsedTime() * WEIGHT_TIME



        return 0.0;
    }

    public static void executeFunctionOnMovableObjectAndRoom(Genotype<ProgramGene<Double>> ind, List<Polygon> room, int maxNodes) {


    }


}
