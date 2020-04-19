package MainMap;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class DrawingPanel extends JPanel {

    public static final int scale = DrawingProgram.SCALE;

    private BufferedImage map;
    private boolean[][] blackPixels;
    private boolean mainProgram;

    public DrawingPanel(DrawingProgram parent, boolean[][] s) {
        mainProgram = false;
        blackPixels = s;
        setPreferredSize(new Dimension(DrawingProgram.SCREEN_WIDTH / scale, DrawingProgram.SCREEN_HEIGHT / scale));
        parent.add(this,BorderLayout.CENTER);
        loadImage();
    }

    public void setMainProgram(boolean main) {
        mainProgram = main;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(map,0,0, DrawingProgram.SCREEN_WIDTH, DrawingProgram.SCREEN_HEIGHT, null);
        paintBlack(g);
    }

    // MODIFIES: this
    // EFFECTS: load images
    private void loadImage() {
        try {
            map = ImageIO.read(new File("map.jpg"));
        } catch (Exception e) {
            try {
                map = ImageIO.read(new File("map.png"));
            } catch (Exception ex) {
                System.out.println("Image not loaded");
            }
        }
    }

    private void setOverlayColour(Graphics g) {
        if (mainProgram) {
            g.setColor(new Color(0, 0, 0, 127));
        } else {
            g.setColor(Color.black);
        }
    }


    private void paintBlack(Graphics g) {
        setOverlayColour(g);
        for (int w = 0; w < DrawingProgram.SCREEN_WIDTH / scale; w++) {
            for (int h = 0; h < DrawingProgram.SCREEN_HEIGHT / scale; h++) {
                if (!blackPixels[w][h]) {
                    g.fillRect(w * scale,h * scale,scale,scale);
                }
            }
        }
    }

}
