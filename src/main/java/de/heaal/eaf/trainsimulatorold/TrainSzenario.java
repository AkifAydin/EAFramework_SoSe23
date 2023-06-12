package de.heaal.eaf.trainsimulatorold;

import io.jenetics.prog.op.EphemeralConst;
import io.jenetics.prog.op.Op;
import io.jenetics.prog.op.Var;
import io.jenetics.prog.regression.Error;
import io.jenetics.prog.regression.LossFunction;
import io.jenetics.prog.regression.Regression;
import io.jenetics.prog.regression.Sample;
import io.jenetics.util.ISeq;

import java.util.Random;

public class TrainSzenario {
    public static void main(String[] args) {

        // int maxSpeed, int powerLevel, int powerConsumption
        TrainVerySimple tvs = new TrainVerySimple(55, 5_000, 3);

        Op<Double> increaseSpeedOp = Op.of("increaseSpeed", 1, v -> tvs.increaseSpeedBy(v[0]));
        Op<Double> deCreaseSpeedOp = Op.of("deCreaseSpeed", 1, v -> tvs.deCreaseSpeedBy(v[0]));
        Op<Double> justDriveOp = Op.of("justDrive", 1, v -> tvs.justDrive(v[0]));
        ISeq<Op<Double>> OPERATIONS = ISeq.of(increaseSpeedOp, deCreaseSpeedOp, justDriveOp);

        Var<Double> incSpeedTerm = Var.of("IncSpeedTerm", 0);
        Var<Double> decSpeedTerm = Var.of("DecSpeedTerm", 1);
        Var<Double> justDriveTerm = Var.of("JustDriveTerm", 2);
        Op<Double> randEphConst = EphemeralConst.of(() -> new Random().nextDouble(90) + 10);
        ISeq<Op<Double>> TERMINALS = ISeq.of(incSpeedTerm, decSpeedTerm, justDriveTerm, randEphConst);

        Regression<Double> REGRESSION = Regression.of(
                Regression.codecOf(OPERATIONS, TERMINALS, 5),
                Error.of(LossFunction::mse),
                Sample.ofDouble(0.0, 0.0),
                Sample.ofDouble(0.0, 0.0)
        );

    }
}
