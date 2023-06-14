package de.heaal.eaf.furniturefitting;

import io.jenetics.prog.op.Op;

import java.awt.*;
import java.util.List;

public abstract class MoveableObjectOperation implements Op<Double> {

    protected MovableObject movableObject;
    protected List<Polygon> room;
    protected Point destination; // TODO Really needed?

    public void setMovableObject(MovableObject movableObject) {
        this.movableObject = movableObject;
    }

    public void setRoom(List<Polygon> room) {
        this.room = room;
    }

    @Override
    public int arity() {
        return 1;
    }

    @Override
    public String toString() {
        return name();
    }

    public abstract class MoveToByAngleAndDist extends MoveableObjectOperation {
        @Override
        public String name() {
            return "MOVE_TO_BY_ANGLE";
        }

        @Override
        public int arity() {
            return 2;
        }

        @Override
        public Double apply(Double[] doubles) {
            movableObject.moveToByAngleAndDist(doubles[0], doubles[1]);
            return 0.0;
        }
    }

    public abstract class MoveToByDelta extends MoveableObjectOperation {
        @Override
        public String name() {
            return "MOVE_TO_BY_DELTA";
        }

        @Override
        public int arity() {
            return 2;
        }

        @Override
        public Double apply(Double[] doubles) {
            movableObject.moveToByDelta(doubles[0], doubles[1]);
            return 0.0;
        }
    }

    public abstract class Turn extends MoveableObjectOperation {
        @Override
        public String name() {
            return "TURN";
        }

        @Override
        public int arity() {
            return 3;
        }

        @Override
        public Double apply(Double[] doubles) {
            movableObject.turn(doubles[0], doubles[1], doubles[2]);
            return 0.0;
        }
    }

    public abstract class CombineTwoActions extends MoveableObjectOperation {
        @Override
        public String name() {
            return "COMB_TWO";
        }

        @Override
        public int arity() {
            return 2;
        }

        @Override
        public Double apply(Double[] doubles) {
            return 0.0;
        }
    }

    public abstract class CombineTHREEActions extends MoveableObjectOperation {
        @Override
        public String name() {
            return "COMB_THREE";
        }

        @Override
        public int arity() {
            return 3;
        }

        @Override
        public Double apply(Double[] doubles) {
            return 0.0;
        }
    }
}
