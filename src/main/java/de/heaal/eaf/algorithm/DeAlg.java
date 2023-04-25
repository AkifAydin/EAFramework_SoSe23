package de.heaal.eaf.algorithm;

import de.heaal.eaf.base.*;
import de.heaal.eaf.evaluation.ComparatorIndividual;

import java.util.Arrays;
import java.util.Comparator;

public class DeAlg extends Algorithm {

    private final float[] min;
    private final float[] max;
    private final ComparatorIndividual terminationCriterion;
    private final IndividualFactory indFac;

    private float stepSize;
    private float crossoverRate;
    private final int populationSize;
    private final Population newPopulation;
    private int dimensions;

    public DeAlg(
            Comparator<Individual> comparator,
            float[] min, float[] max,
            ComparatorIndividual terminationCriterion,
            float stepSize,
            float crossoverRate,
            int populationSize) {
        super(comparator, null);

        this.min = min;
        this.max = max;
        this.terminationCriterion = terminationCriterion;
        this.indFac = new GenericIndividualFactory(min, max);
        this.stepSize = stepSize;
        this.crossoverRate = crossoverRate;
        this.populationSize = populationSize;
        this.newPopulation = new Population(populationSize);
    }

    @Override
    protected boolean isTerminationCondition() {
        population.sort(comparator);
        return comparator.compare(population.get(0), terminationCriterion) > 0;
    }

    @Override
    public void run() {
        initialize(indFac, populationSize);
        dimensions = population.get(0).getGenome().len();

        int numOfGenerations = 0;


        while (!isTerminationCondition()) {
//        while (numOfGenerations++ < 20) {
            nextGeneration();
            System.out.println(population.get(0).getCache());
        }

        System.out.println(Arrays.toString(population.get(0).getGenome().array()));
    }

    @Override
    protected void nextGeneration() {
        super.nextGeneration();

        // Sanity check
        if (population.size() != populationSize) {
            throw new RuntimeException("Something went very wrong...");
        }

        stepSize = rng.nextFloat(0.4f, 0.9f);
        crossoverRate = rng.nextFloat(0.1f, 1.0f);

        // TODO Art der Zufälligkeit (True Random, Best Random)

        for (int i = 0; i < populationSize; i++) {
            // TODO True random oder normalverteilt Random
            int r1 = getRandomInt(dimensions, i);
            int r2 = getRandomInt(dimensions, i, r1);
            int r3 = getRandomInt(dimensions, i, r1, r2);

            Individual xi = population.get(i);
            Individual xr1 = population.get(r1);
            Individual xr2 = population.get(r2);
            Individual xr3 = population.get(r3);

            // vi <- xr1 + F(xr2 - xr3) (mutant vector)
            Individual xr2DiffXr3 = xr2.copy();
            xr2DiffXr3.sub(xr3).mul(stepSize);
            Individual vi = xr1.copy().add(xr2DiffXr3);

            Individual ui = xi.copy();
            int jr = getRandomInt(dimensions);

            for (int k = 0; k < dimensions; k++) {
                float rck = rng.nextFloat();
                if (rck < crossoverRate || k == jr) {
                    // uij <- vij
                    ui.getGenome().array()[k] = vi.getGenome().array()[k];
                }
            }

            newPopulation.add(ui);
        }

        for (int i = 0; i < populationSize; i++) {
            if (comparator.compare(population.get(i), newPopulation.get(i)) < 0) { // TODO FIXME
                population.set(i, newPopulation.get(i));
            }
        }

        newPopulation.clear();
    }

    private int getRandomInt(int n, int... excluded) {
        int randomInt = rng.nextInt(n + 1); // generates a random integer between 0 and N (inclusive)

        while (contains(excluded, randomInt)) { // checks if the random integer is in the excluded array
            randomInt = rng.nextInt(n + 1); // generates a new random integer if the previous one was excluded
        }

        return randomInt;
    }

    private boolean contains(int[] arr, int val) {
        // checks if the array contains the given value
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == val) {
                return true;
            }
        }
        return false;
    }
}
