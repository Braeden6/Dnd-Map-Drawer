package MainMap;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static java.lang.Math.*;

public class DrawingProgram extends JFrame {

    public static final int ARRAY_WIDTH = 256;
    public static final int ARRAY_HEIGHT = 144;
    public static final int SCALE = Toolkit.getDefaultToolkit().getScreenSize().width / ARRAY_WIDTH;
    public static final int SCREEN_WIDTH = ARRAY_WIDTH * SCALE;//Toolkit.getDefaultToolkit().getScreenSize().width;
    public static final int SCREEN_HEIGHT = ARRAY_HEIGHT * SCALE;  //Toolkit.getDefaultToolkit().getScreenSize().height;
    public static final int INTERVAL = 50;



    private boolean[][] blackPixels;
    protected Point previousPoint;
    private int size = 40 / SCALE;
    private boolean drawOrErase = true;
    private MainClient client;


    public DrawingProgram(boolean[][] pixels, MainClient client) {
        super("Draw");
        setLayout(new BorderLayout());
        setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        blackPixels = pixels;
        this.client = client;
        DrawingOptionPanel option = new DrawingOptionPanel(this, client);
        DrawingPanel draw =  new DrawingPanel(this, blackPixels);
        option.getDrawingPanel(draw);
        addActionListeners();
        setVisible(true);
        addTimer();
    }

    //setter
    public void setSize(int size) {
        this.size = size;
    }

    //setter
    public void setDrawOrErase(boolean drawOrErase) {
        this.drawOrErase = drawOrErase;
    }

    // MODIFIES: DrawingPanel
    // EFFECTS: adds timer to program that request a repaint of graphics
    private void addTimer() {
        Timer timer = new Timer(INTERVAL, ae -> repaint());
        timer.start();
    }

    // MODIFIES: this
    // EFFECTS: add action Listeners and request focus from program
    private void addActionListeners() {
        addMouseListener(new DrawingMouseListener());
        addMouseMotionListener(new DrawingMouseListener());
        requestFocus();
    }

    // MODIFIES: this
    // EFFECTS: draws amount of circles between the two points given
    private void eraseArea(Point p, Point c) {
        double amount = 20;
        double xSlope =  abs(p.x - c.x) / amount;
        double ySlope = abs(p.y - c.y) / amount;
        for (int i = 0; i < amount; i++) {
            if (p.x < c.x && p.y < c.y) {
                paintCircle((int) (p.x + i * xSlope), (int) (p.y + i * ySlope));
            } else if (p.x < c.x && p.y > c.y) {
                paintCircle((int) (p.x + i * xSlope), (int) (p.y - i * ySlope));
            } else if (p.x > c.x && p.y < c.y) {
                paintCircle((int) (p.x - i * xSlope), (int) (p.y + i * ySlope));
            } else { // p.x => c.x && p.y => c.y
                paintCircle((int) (p.x - i * xSlope), (int) (p.y - i * ySlope));
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: draws a circle at location x/y with radius of size
    private void paintCircle(int x, int y) {
        y -= 2;
        for (int w = -size; w < size * 2; w++) {
            for (int h = -size; h < size * 2; h++)
                if (getDifference(x, x + w, y, y + h) < size && outOfBounds(x + w, y + h)) {
                    blackPixels[x + w][y + h] = drawOrErase;
                }
        }
    }


    // EFFECTS: return true if the w and h are within the bounds of the array
    private boolean outOfBounds(int w, int h) {
        return w >= 0 && h >= 0 && w < ARRAY_WIDTH && h < ARRAY_HEIGHT;
    }

    // EFFECTS: returns the distance between the two points
    private double getDifference(int x1, int x2, int y1, int y2) {
        return sqrt(pow(x1 - x2, 2) + pow(y1 - y2, 2));
    }

    private class DrawingMouseListener extends MouseAdapter {

        // EFFECTS:
        public void mouseReleased(MouseEvent e) {
            client.signalSendInfo();
        }

        // EFFECTS: Forward mouse pressed event to the active tool
        public void mousePressed(MouseEvent e) {
            previousPoint = new Point(e.getX() / SCALE, e.getY() / SCALE);
        }

        // EFFECTS:Forward mouse dragged event to the active tool
        public void mouseDragged(MouseEvent e) {
            Point p = new Point(e.getX() / SCALE, e.getY() / SCALE);
            eraseArea(previousPoint, p);
            previousPoint = p;
        }

    }

}
