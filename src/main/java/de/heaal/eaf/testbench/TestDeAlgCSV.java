package de.heaal.eaf.testbench;

import de.heaal.eaf.algorithm.DeAlg;
import de.heaal.eaf.base.Individual;
import de.heaal.eaf.evaluation.ComparatorIndividual;
import de.heaal.eaf.evaluation.MinimizeFunctionComparator;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

public class TestDeAlgCSV {

    public static void main(String[] args) throws IOException {
        List<Float> vals = readCsv("F:\\GoogleDrive\\Work_Werum_Studium\\#EvolutionäreAlgorithmen\\sensordata-alg.csv");
        runSin(vals);


//        for (int i = 0; i < 9460; i++) {
//            System.out.println(calculateSinusoidalWave(5.0704834f, 50.01866f, 94.05648f, 12.187587f, (float) i));
//        }

        // [-13.384741, -12.677753, -47.57627, 18.060198] @ 9460 Rows
        // [1.8704834, 50.01866, 94.05648, 9.187587]
    }

    private static void runSin(List<Float> values) {
        float[] min = {-1000, -1000, -1000, -1000};
        float[] max = {1000, 1000, 1000, 1000};

        Function<Individual, Float> evalSinFunc = ind -> {
            float[] arr = ind.getGenome().array();

            float fitness = 0.0f;

            int size = values.size();

            for (int i = 110; i < size - 60; i++) {

                float sinVal = calculateSinusoidalWave(arr[0], arr[1], arr[2], arr[3], (float) i);
                float actVal = values.get(i);

                fitness += Math.abs(sinVal - actVal);
            }

            return fitness;
        };

        Comparator comparator = new MinimizeFunctionComparator(evalSinFunc);

        var algoDe = new DeAlg(
                comparator,
                min, max,
                new ComparatorIndividual(20_000.0f),
                0.0f,
                0.0f,
                500);

        algoDe.run();
    }

    private static List<Float> readCsv(String fileName) throws IOException {
        List<Float> result = new ArrayList<>();

        FileReader reader = new FileReader(fileName);
        CSVParser parser = new CSVParser(reader, CSVFormat.newFormat(';'));

        boolean b = true;

        for (CSVRecord record : parser) {

            // TODO make it clean!
            if (b) {
                b = false;
                continue;
            }

            String value = record.get(4);
            value = value.replace(",", ".");
            float floatValue = Float.parseFloat(value);
            result.add(floatValue);
        }

        parser.close();
        reader.close();

        return result;
    }

    private static float calculateSinusoidalWave(float amplitude, float frequency, float phase, float verticalShift, float time) {
        return (float) (amplitude * Math.sin(2 * Math.PI * frequency * time + phase) + verticalShift);
    }

}
