package de.heaal.eaf.trainsimulator;

import io.jenetics.prog.op.Op;

/**
 *
 * @author chris
 */
public class Zug {

    public static abstract class Operation implements Op<Double> {

        protected Zug zug;

        public void setZug(Zug zug) {
            this.zug = zug;
        }

        @Override
        public int arity() {
            return 1;
        }

        @Override
        public String toString() {
            return name();
        }
    }

    public static class GetSpeed extends Operation {

        @Override
        public String name() {
            return "GET_SPEED";
        }

        @Override
        public int arity() {
            return 0;
        }

        @Override
        public Double apply(Double[] t) {
            return zug.getGeschwindigkeit();
        }

    }

    public static class GetDistance extends Operation {

        @Override
        public String name() {
            return "GET_DIST";
        }

        @Override
        public int arity() {
            return 0;
        }

        @Override
        public Double apply(Double[] t) {
            return zug.getEntfernung();
        }

    }

    public static class IfElse extends Operation {

        @Override
        public String name() {
            return "IF";
        }

        @Override
        public int arity() {
            return 3;
        }

        @Override
        public Double apply(Double[] t) {
            if (t[0] > 0) { // true
                return t[1];
            } else {
                return t[2];
            }
        }

    }

    public static class SetSpeed extends Operation {

        @Override
        public String name() {
            return "SET_SPEED";
        }

        @Override
        public Double apply(Double[] v) {
            zug.setGeschwindigkeit(v[0]);
            return v[0];
//            return 0.0;
        }

    }

    public static class CombineOps extends Operation {

        @Override
        public String name() {
            return "COMBINE";
        }

        @Override
        public int arity() {
            return 2;
        }

        @Override
        public Double apply(Double[] doubles) {
            return doubles[0] + doubles[1];
        }
    }

    private static final double MAX_SPEED = 17.0;
    private static final double MIN_SPEED = -1.0;

    private double entfernung;
    private double geschwindigkeit;
    private double energie;
    private double elapsedTime;

    public void setGeschwindigkeit(double geschwindigkeit) {
        if (geschwindigkeit > MAX_SPEED) {
            this.geschwindigkeit = MAX_SPEED;
        } else if (geschwindigkeit < MIN_SPEED) {
            this.geschwindigkeit = MIN_SPEED;
        } else {
            this.geschwindigkeit = geschwindigkeit;
        }
    }

    public double getEnergie() {
        return this.energie;
    }

    public double getElapsedTime() {
        return this.elapsedTime;
    }

    public double getGeschwindigkeit() {
        return geschwindigkeit;
    }

    public double getEntfernung() {
        return entfernung;
    }

    /**
     * Ein Zeitschritt.
     */
    public void tick() {
        // Physiker*innen bitte wegsehen...
        this.entfernung += geschwindigkeit;
        this.energie += geschwindigkeit * geschwindigkeit;
        elapsedTime++;
    }
}
