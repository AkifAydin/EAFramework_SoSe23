package de.heaal.eaf.furniturefitting;

import de.heaal.eaf.trainsimulator.Zug;
import io.jenetics.Genotype;
import io.jenetics.prog.ProgramGene;
import io.jenetics.prog.op.Op;

import java.awt.*;

import static de.heaal.eaf.trainsimulator.Zugfahrt.*;

public class MovableObjectFitnessFunction {

    public static final double WEIGHT_REMAINING_DISTANCE = 0.0;
    public static final double WEIGHT_TRAVELED_DISTANCE = 0.0;
    public static final double WEIGHT_MOVES = 0.0; // TODO Another way to reduce the tree?
    public static final double WEIGHT_TURNS = 0.0;
    public static final double WEIGHT_WALL_TOUCHES_CONST = 0.0;
    public static final double WEIGHT_WALL_TOUCHES = 0.0;

    public static double calcFitness(Genotype<ProgramGene<Double>> ind) {
//        Zug zug = executeFunctionOnTrain(ind, MAX_NODES);
//
//        return calcFitnessWithWeights(zug); //  * WEIGHT_DIST + zug.getEnergie() * WEIGHT_ENERGY + zug.getElapsedTime() * WEIGHT_TIME



        return 0.0;
    }

    public static FitnessMeasures executeFunctionOnMovableObjectAndRoom(Genotype<ProgramGene<Double>> ind, int maxNodes) {
        MovableObject mo = SzenarioObjectGenerator.INSTANCE.getNewMovableObject();
        Polygon room = SzenarioObjectGenerator.INSTANCE.getRoom();

        // Setze die Zug instance bei allen Operationen und Terminalen
        for (Op<Double> op : ind.gene().operations()) {
            if (op instanceof MoveableObjectOperation operation) {
                operation.setMovableObject(mo);
            }
        }

        for (Op<Double> op : ind.gene().terminals()) {
            if (op instanceof MoveableObjectOperation operation) {
                operation.setMovableObject(mo);
            }
        }

        for (int i = 0; i < maxNodes && i < ind.geneCount(); i++) {
            ProgramGene<Double> gene = ind.get(0).get(i);

            gene.eval();

            if (mo.getDistanceToPoint(SzenarioObjectGenerator.INSTANCE.getDestination()) < 0.1) {
                break;
            }
        }

        return null; // TODO Return fitness measures
    }

    public static double calcFitnessWithWeights(Zug zug) {
        if (zug.getEntfernung() != TARGET_DISTANCE) {
            return EXTRA_MISSED_TARGET_FITNESS_COST + Math.abs(zug.getEntfernung() - TARGET_DISTANCE) * WEIGHT_DIST +
                    zug.getElapsedTime() * WEIGHT_TIME + zug.getEnergie() * WEIGHT_ENERGY;

        } else {
            return zug.getElapsedTime() * WEIGHT_TIME + zug.getEnergie() * WEIGHT_ENERGY;
        }
    }

    private class FitnessMeasures {

    }

}
