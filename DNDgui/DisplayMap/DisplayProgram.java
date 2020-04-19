package DisplayMap;

import javax.swing.*;
import java.awt.*;

public class DisplayProgram extends JFrame {

    public static final int ARRAY_WIDTH = 256;
    public static final int ARRAY_HEIGHT = 144;
    public static final int SCALE = Toolkit.getDefaultToolkit().getScreenSize().width / ARRAY_WIDTH;
    public static final int SCREEN_WIDTH = ARRAY_WIDTH * SCALE;//Toolkit.getDefaultToolkit().getScreenSize().width;
    public static final int SCREEN_HEIGHT = ARRAY_HEIGHT * SCALE;  //Toolkit.getDefaultToolkit().getScreenSize().height;
    public static final int INTERVAL = 40;



    private boolean[][] blackPixels;


    public DisplayProgram(boolean[][] pixels) {
        super("Draw");
        setLayout(new BorderLayout());
        setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        blackPixels = pixels;
        new DisplayPanel(this, blackPixels);
        setVisible(true);
        addTimer();
    }

    private void addTimer() {
        Timer timer = new Timer(INTERVAL, ae -> {
            repaint();
            validate();
        });
        timer.start();
    }
}
