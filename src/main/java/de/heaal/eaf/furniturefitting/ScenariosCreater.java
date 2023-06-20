package de.heaal.eaf.furniturefitting;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class ScenariosCreater extends JFrame {

    // java - get screen size using the Toolkit class
//    private static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private static final int WIDTH = 1920;
    private static final int HEIGHT = 1080;

    private int[] xPoints;
    private int[] yPoints;
    private int[] xPointsOBJ;
    private int[] yPointsOBJ;
    private BufferedImage goalImg;


    public ScenariosCreater() {
        setSize(WIDTH, HEIGHT);  //Size of the Frame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        createLShape();
    }

    private void createLShape() {
//        xPointsOBJ = new int[]{620, 670, 670, 820, 820, 770, 770, 620};
//        yPointsOBJ = new int[]{750, 750, 850, 850, 1000, 1000, 900, 900};
//        xPoints = new int[]{200,1720,1720,1100, 1100, 1690, 1690, 230, 230, 850, 850, 200, 200};
//        yPoints = new int[]{150, 150, 730, 730,  700,  700, 180, 180, 700, 700, 730, 730, 150};
        xPointsOBJ = new int[]{650, 700, 700, 850, 850, 800, 800, 650};
        yPointsOBJ = new int[]{750, 750, 850, 850, 1000, 1000, 900, 900};
        xPoints = new int[]{200, 930 , 1720, 1720, 860, 860, 930, 930, 960, 960, 1690, 1690, 960, 960, 930, 930, 230, 230, 660, 660, 200, 200};
        yPoints = new int[]{150, 150 ,  150,  730, 730, 700, 700, 500, 500, 700,  700,  180, 180, 300, 300, 180, 180, 700, 700, 730, 730, 150};

    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        Graphics2D g2d = (Graphics2D) g;

        // Set a thicker stroke
        Stroke stroke = new BasicStroke(3f); // Adjust the line thickness as needed
        g2d.setStroke(stroke);

        g2d.setColor(Color.BLACK);
        //g2d.drawPolygon(xPointsRaum, yPointsRaum, xPointsRaum.length);
        g2d.setColor(Color.gray);
        g2d.fillPolygon(xPoints, yPoints, xPoints.length);
        g2d.setColor(Color.pink);
        g2d.fillPolygon(xPointsOBJ, yPointsOBJ, xPointsOBJ.length);
        try {
            goalImg = ImageIO.read(new File("src\\main\\java\\de\\heaal\\eaf\\furniturefitting\\images\\green_flag.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //g2d.fillOval(960, 400, 10,10); //440 is the Center
        g2d.drawImage(goalImg, 1300, 400, null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ScenariosCreater::new);
    }
}
