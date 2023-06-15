package de.heaal.eaf.furniturefitting;

import javax.swing.*;

public class QuickTesting {
    public static void main(String[] args) throws InterruptedException {
        ScenarioVisualizer sv = new ScenarioVisualizer();

        sv.addRoomObject(ScenarioObjectGenerator.INSTANCE.getRoom());

        MovableObject mo = ScenarioObjectGenerator.INSTANCE.getNewMovableObject();

//        MovableObject mo = new MovableObject(
//                new int[] {0, 10, 0},
//                new int[] {0, 0, 10},
//                new Point(0, 0));
        sv.setMovableObject(mo);

        JFrame frame = new JFrame();
        frame.setSize(1500, 900);
        frame.getContentPane().add(sv);
        frame.setVisible(true);

        Thread.sleep(1000);
        mo.moveToByDelta(400, 400);
        frame.repaint();

        String s = mo.getCenterPoint().toString();
        System.out.println(s);

        boolean b = CollisionUtil.polygonsIntersect(mo, ScenarioObjectGenerator.INSTANCE.getRoom());
        System.out.println(b);
    }
}
