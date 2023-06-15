package de.heaal.eaf.furniturefitting;

import io.jenetics.Genotype;
import io.jenetics.prog.ProgramGene;
import io.jenetics.prog.op.Op;

import java.awt.*;

import static de.heaal.eaf.furniturefitting.FitnessMeasures.*;

public class MovableObjectFitnessFunction {

    private static final int MAX_NODES = 100; // TODO To many constants at different locations. Put into file!

    public static double calcFitness(Genotype<ProgramGene<Double>> ind) {
        MovableObject mo = executeFunctionOnMovableObjectAndRoom(ind);
        return calcFitnessWithWeights(mo);
    }

    public static MovableObject executeFunctionOnMovableObjectAndRoom(Genotype<ProgramGene<Double>> ind) {
        MovableObject mo = ScenarioObjectGenerator.INSTANCE.getNewMovableObject();
        Polygon room = ScenarioObjectGenerator.INSTANCE.getRoom();

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

        // TODO Let the Program run maxNodes VALID operations.
        for (int i = 0; i < MAX_NODES && i < ind.geneCount(); i++) {
            ProgramGene<Double> gene = ind.get(0).get(i);

            Polygon before = mo.getMinimalCopy();
            gene.eval();
            Polygon after = mo.getMinimalCopy();

            // TODO If the move was invalid delete it from the tree!

            Polygon trail = CollisionUtil.getTrailOfPolygons(before, after);
            if (CollisionUtil.polygonsIntersect(trail, room)) {
                mo.getFitnessMeasures().incrementWallTouches();
            }

            if (mo.getDistanceToPoint(ScenarioObjectGenerator.INSTANCE.getDestination()) < REMAINING_DISTNACE_TOLLERANCE) {
                break;
            }
        }

        return mo;
    }

    public static double calcFitnessWithWeights(MovableObject mo) {
        double fitnessResult = 0.0;
        FitnessMeasures fitMes = mo.getFitnessMeasures();

        fitnessResult += fitMes.getNumberOfMoves() + WEIGHT_MOVES;
        fitnessResult += fitMes.getNumberOfTurns() + WEIGHT_TURNS;
        fitnessResult += fitMes.getTraveledDistance() + WEIGHT_TRAVELED_DISTANCE; // TODO Not traveled dist but traveled dist - |start -> end|

        // Fitness of remaining distance. Only applicable if goal not reached.
        double distanceToDestination = mo.getDistanceToPoint(ScenarioObjectGenerator.INSTANCE.getDestination());
        if (distanceToDestination > REMAINING_DISTNACE_TOLLERANCE) {
            fitnessResult += distanceToDestination * WEIGHT_REMAINING_DISTANCE;
            fitnessResult += WEIGHT_REMAINING_DISTANCE_CONST;
        }

        // Fitness of wall touches. Only applicable if wall was touched.
        int numberOfWallTouches = fitMes.getNumberOfWallTouches();
        if (numberOfWallTouches > 0) {
            fitnessResult += numberOfWallTouches * WEIGHT_WALL_TOUCHES;
            fitnessResult += WEIGHT_WALL_TOUCHES_CONST;
        }

        return fitnessResult;
    }
}
