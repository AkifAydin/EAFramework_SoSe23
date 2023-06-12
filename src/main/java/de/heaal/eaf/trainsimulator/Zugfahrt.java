package de.heaal.eaf.trainsimulator;

import io.jenetics.Genotype;
import io.jenetics.Mutator;
import io.jenetics.engine.Engine;
import io.jenetics.engine.EvolutionResult;
import io.jenetics.engine.Limits;
import io.jenetics.ext.SingleNodeCrossover;
import io.jenetics.ext.util.TreeNode;
import io.jenetics.prog.ProgramChromosome;
import io.jenetics.prog.ProgramGene;
import io.jenetics.prog.op.Const;
import io.jenetics.prog.op.MathOp;
import io.jenetics.prog.op.Op;
import io.jenetics.util.ISeq;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static de.heaal.eaf.trainsimulator.FitnessFunctions.calcDfltFitness;
import static de.heaal.eaf.trainsimulator.FitnessFunctions.executeFunctionOnTrain;

/**
 * @author chris
 */
public class Zugfahrt {

    public static final int MAX_NODES = 100;
    public static final int GENERATIONS = 100;
    public static final int TARGET_DISTANCE = 1000;
    public static final double EXTRA_MISSED_TARGET_FITNESS_COST = 500;
    public static final double WEIGHT_DIST = 1.0;
    public static final double WEIGHT_ENERGY = 0.5;
    public static final double WEIGHT_TIME = 0.1;


    public static void main(String[] args) {
        final ISeq<Op<Double>> operations = ISeq.of(
                new Zug.SetSpeed(), // TODO Doesn't have to be the tick function be in here?
                new Zug.IfElse(), // TODO Why an If Else?
                MathOp.ADD//, MathOp.SUB // TODO Why ADD and SUB? If translated to irl what could be SUBtracted?
        );

        final ISeq<Op<Double>> terminals = ISeq.of(
                // new Zug.GetSpeed(),
                // new Zug.GetDistance(), // Why distance?
                Const.of(0.0), Const.of(1.0), Const.of(2.0), Const.of(3.0), Const.of(4.0), Const.of(5.0),
                Const.of(6.0), Const.of(7.0), Const.of(8.0), Const.of(9.0), Const.of(10.0), Const.of(11.0),
                Const.of(12.0), Const.of(13.0), Const.of(14.0), Const.of(15.0), Const.of(16.0), Const.of(17.0)
                // , Const.of(-2.0), Const.of(-3.0) // Add some Constants // TODO Why going backwards?
        );

        // Create an initial chromosome/program
        final ProgramChromosome<Double> program = ProgramChromosome.of(10, operations, terminals);

        final Engine<ProgramGene<Double>, Double> engine = Engine
                .builder(FitnessFunctions::calcDfltFitness /* Fitness function*/, program /* First initial chromosome*/)
                .minimizing() // Say that the fitness function has to be minimized
                .alterers(
                        new SingleNodeCrossover<>(0.9),
                        new Mutator<>(0.1))
                .populationSize(50)
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

        List<Op<Double>> first100Nodes = new ArrayList<>(100);
        getFirstNElementsFromTree(first100Nodes, tree, 100);

        System.out.println("Generations:      " + result.totalGenerations());
        System.out.println("Function:         " + first100Nodes);
        System.out.println("Error:            " + calcDfltFitness(pop.get(0)));
        Zug z = executeFunctionOnTrain(pop.get(0), MAX_NODES);
        System.out.println("Zug Entfehrnung:  " + z.getEntfernung());
        System.out.println("Zug Energie:      " + z.getEnergie());
        System.out.println("Zug Elapsed Time: " + z.getElapsedTime());
    }

    public static void getFirstNElementsFromTree(List<Op<Double>> result, TreeNode<Op<Double>> node, int n) {
        if (result.size() >= n) {
            return;
        }

        result.add(node.value());

        for (int i = 0; i < node.childCount(); i++) {
            getFirstNElementsFromTree(result, node.childAt(i), n);
        }
    }
}
