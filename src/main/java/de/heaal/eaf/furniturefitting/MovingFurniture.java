package de.heaal.eaf.furniturefitting;

import io.jenetics.Genotype;
import io.jenetics.Mutator;
import io.jenetics.engine.Engine;
import io.jenetics.engine.EvolutionResult;
import io.jenetics.engine.Limits;
import io.jenetics.ext.SingleNodeCrossover;
import io.jenetics.ext.util.TreeNode;
import io.jenetics.prog.ProgramChromosome;
import io.jenetics.prog.ProgramGene;
import io.jenetics.prog.op.Op;
import io.jenetics.util.ISeq;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static de.heaal.eaf.trainsimulator.FitnessFunctions.calcDfltFitness;

public class MovingFurniture {

    private static final int POP_SIZE = 35;
    private static final int GENERATIONS = 10;

    public static void main(String[] args) {

        final ISeq<Op<Double>> operations = ISeq.of(
                new MoveableObjectOperation.MoveToByAngleAndDist(),
                new MoveableObjectOperation.MoveToByDelta(),
//                new MoveableObjectOperation.Turn(),
                new MoveableObjectOperation.CombineTwoActions(),
                new MoveableObjectOperation.CombineThreeActions()
        );

        final ISeq<Op<Double>> terminals = MovableObject.getPossibleTerminals();

        // Create an initial chromosome/program
        final ProgramChromosome<Double> program = ProgramChromosome.of(10, operations, terminals);

        final Engine<ProgramGene<Double>, Double> engine = Engine
                .builder(MovableObjectFitnessFunction::calcFitness /* Fitness function*/, program /* First initial chromosome*/)
                .minimizing() // Say that the fitness function has to be minimized
                .alterers(
                        new SingleNodeCrossover<>(0.9),
                        new Mutator<>(0.1))
                .populationSize(POP_SIZE)
                .build();

        final var results = engine
                .stream()
                .limit(Limits.byFixedGeneration(GENERATIONS))
                .collect(Collectors.toList());

        EvolutionResult<ProgramGene<Double>, Double> result = results.get(results.size() - 1); // last generation
        List<Genotype<ProgramGene<Double>>> pop = new ArrayList<>(result.genotypes().asList());
        pop.sort((a, b) -> {
            double e1 = calcDfltFitness(a);
            double e2 = calcDfltFitness(b);
            if (e1 > e2) {
                return 1;
            } else if (e2 > e1) {
                return -1;
            } else {
                return 0;
            }
        });

        final TreeNode<Op<Double>> tree = result.bestPhenotype()
                .genotype().gene().toTreeNode();

        System.out.println("Generations:      " + result.totalGenerations());
        System.out.println("Function:         " + tree);
        System.out.println("Error:            " + calcDfltFitness(pop.get(0)));
        MovableObject mo = MovableObjectFitnessFunction.executeFunctionOnMovableObjectAndRoom(pop.get(0)); // TODO HARDCODED

        System.out.println(mo.getCenterPoint());
        System.out.println("[" + mo.xpoints[0] + "," + mo.ypoints[0] +  "]");

        ScenarioVisualizer sv = new ScenarioVisualizer();
        sv.addRoomObject(ScenarioObjectGenerator.INSTANCE.getRoom());
        sv.setMovableObject(mo);

        JFrame frame = new JFrame();
        frame.setSize(1500, 900);
        frame.getContentPane().add(sv);
        frame.setVisible(true);
    }
}
