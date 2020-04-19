package DisplayMap;

import MainMap.DrawingProgram;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class DisplayPanel extends JPanel {

    public static final int scale = DrawingProgram.SCALE;

    private BufferedImage map;
    private boolean[][] blackPixels;

    public DisplayPanel(DisplayProgram parent, boolean[][] s) {
        blackPixels = s;
        setPreferredSize(new Dimension(DrawingProgram.SCREEN_WIDTH / scale, DrawingProgram.SCREEN_HEIGHT / scale));
        parent.add(this,BorderLayout.CENTER);
        loadImage();
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
            System.out.println("error");
        }
    }


    private void paintBlack(Graphics g) {
        g.setColor(Color.black);
        for (int w = 0; w < DrawingProgram.SCREEN_WIDTH / scale; w++) {
            for (int h = 0; h < DrawingProgram.SCREEN_HEIGHT / scale; h++) {
                if (!blackPixels[w][h]) {
                    g.fillRect(w * scale,h * scale,scale,scale);
                }
            }
        }
    }

}
