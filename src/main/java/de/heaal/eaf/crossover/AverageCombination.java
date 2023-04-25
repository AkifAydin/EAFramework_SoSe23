package de.heaal.eaf.crossover;

import de.heaal.eaf.base.Individual;

import java.util.Random;

public class AverageCombination implements Combination {
    private Random rng;

    public AverageCombination() {
        this.rng = new Random();
    }

    @Override
    public void setRandom(Random rng) {
        this.rng = rng;
    }

    @Override
    public Individual combine(Individual[] parents) {
        if (parents.length != 2) {
            throw new RuntimeException("parents.length has to be == 2!");
        }

        Individual motherInd = parents[0];
        Individual fatherInd = parents[1];

        float[] motherGenomeArr = motherInd.getGenome().array();
        float[] fatherGenomeArr = fatherInd.getGenome().array();
        int arrSize = motherGenomeArr.length;

        Individual child = parents[0].copy();

        for (int i = 0; i < arrSize; i++) {
            float avgOfTwo = (motherGenomeArr[i] + fatherGenomeArr[i]) / 2;
            child.getGenome().array()[i] = avgOfTwo;
        }

        return child;
    }
}
