package de.heaal.eaf.trainsimulator;

import io.jenetics.Genotype;
import io.jenetics.prog.ProgramGene;

import static de.heaal.eaf.trainsimulator.Zugfahrt.*;

public class FitnessFunctions {

    public static double calcDfltFitness(Genotype<ProgramGene<Double>> ind) {
        Zug zug = executeFunctionOnTrain(ind, MAX_NODES);

        return calcFitnessWithWeights(zug); //  * WEIGHT_DIST + zug.getEnergie() * WEIGHT_ENERGY + zug.getElapsedTime() * WEIGHT_TIME
    }

    public static Zug executeFunctionOnTrain(Genotype<ProgramGene<Double>> ind, int maxNodes) {
        Zug zug = new Zug();

        // Setze die Zug instance bei allen Operationen und Terminalen
        for (var op : ind.gene().operations()) {
            if (op instanceof Zug.Operation operation) {
                operation.setZug(zug);
            }
        }

        for (var op : ind.gene().terminals()) {
            if (op instanceof Zug.Operation operation) {
                operation.setZug(zug);
            }
        }

        for (int i = 0; i < 100 && i < ind.geneCount(); i++) { // Maximal 100 Schritte
            ProgramGene<Double> gene = ind.get(0).get(i);
            // Wir rufen das Kontrollprogramm des Zugs auf.
            // Je nach Entfernung (und ggfls. aktueller Geschwindigkeit) soll
            // das Programm die neue Geschwindigkeit des Zuges setzen.
            // Der Kontroll-Loop wird durch die For-Schleife hier repräsentiert,
            // weil dies in GP schwierig als Operation darzustellen ist.
            gene.eval();

            // Wir lassen Entfernung und Energie aktualisieren
            zug.tick();
            if (zug.getEntfernung() - TARGET_DISTANCE >= 0) {
                break;
            }
        }
        return zug;
    }

    public static double calcFitnessWithWeights(Zug zug) {
        if (zug.getEntfernung() != TARGET_DISTANCE) {
            return EXTRA_MISSED_TARGET_FITNESS_COST + Math.abs(zug.getEntfernung() - TARGET_DISTANCE) * WEIGHT_DIST +
                    zug.getElapsedTime() * WEIGHT_TIME + zug.getEnergie() * WEIGHT_ENERGY;

        } else {
            return zug.getElapsedTime() * WEIGHT_TIME + zug.getEnergie() * WEIGHT_ENERGY;
        }
    }
}
