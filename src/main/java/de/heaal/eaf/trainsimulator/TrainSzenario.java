package de.heaal.eaf.trainsimulator;

import io.jenetics.prog.op.EphemeralConst;
import io.jenetics.prog.op.Op;
import io.jenetics.util.ISeq;

import java.util.Random;

public class TrainSzenario {
    public static void main(String[] args) {

        // int maxSpeed, int powerLevel, int powerConsumption
        TrainVerySimple tvs = new TrainVerySimple(120, 5_000, 3);

        Op<Integer> increaseSpeedOp = Op.of("increaseSpeed", 1, v -> tvs.increaseSpeedBy(v[0]));
        Op<Integer> deCreaseSpeedOp = Op.of("deCreaseSpeed", 1, v -> tvs.deCreaseSpeedBy(v[0]));
        Op<Integer> justDriveOp = Op.of("ac", 1, v -> tvs.justDrive(v[0]));

        Op<Integer> distance = EphemeralConst.of(() -> new Random().nextInt(90) + 10);

        ISeq<Op<Integer>> OPERATIONS = ISeq.of(increaseSpeedOp, deCreaseSpeedOp, justDriveOp);
        ISeq<Op<Integer>> TERMINALS = ISeq.of(distance);


    }
}
