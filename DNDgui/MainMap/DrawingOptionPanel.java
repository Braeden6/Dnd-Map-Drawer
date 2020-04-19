package MainMap;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DrawingOptionPanel extends JFrame {

    private DrawingProgram parent;
    private MainClient client;
    private DrawingPanel draw;

    public DrawingOptionPanel(DrawingProgram parent, MainClient client) {
        this.client = client;
        this.parent = parent;
        initializeBar();
    }

    public void getDrawingPanel(DrawingPanel draw) {
        this.draw = draw;
    }


    private void initializeBar() {
        SizeActionListener sizeActionListener = new SizeActionListener();
        OptionActionListener optionActionListener = new OptionActionListener();
        CharacterLocationActionListener colourActionListener = new CharacterLocationActionListener();
        JMenuBar mainMenuBar = new JMenuBar();
        mainMenuBar.setSize(DrawingProgram.SCREEN_WIDTH,20);
        JMenu option = new JMenu("Options");
        JMenu size = new JMenu("Size");
        JMenu location = new JMenu("Colour");
        JMenuItem erase = new JMenuItem("Erase");
        JMenuItem draw = new JMenuItem("Draw");
        JMenuItem main = new JMenuItem("Main");
        JMenuItem viewer = new JMenuItem("Viewer");
        JMenuItem small = new JMenuItem("Small");
        JMenuItem medium = new JMenuItem("Medium");
        JMenuItem large = new JMenuItem("Large");
        JMenuItem veryLarge = new JMenuItem("Very Large");
        JMenuItem purple = new JMenuItem("Purple");
        JMenuItem orange = new JMenuItem("Orange");
        JMenuItem red = new JMenuItem("Red");

        mainMenuBar.add(option);
        mainMenuBar.add(size);
        mainMenuBar.add(location);
        option.add(erase);
        option.add(draw);
        option.add(main);
        option.add(viewer);
        size.add(small);
        size.add(medium);
        size.add(large);
        size.add(veryLarge);
        location.add(purple);
        location.add(orange);
        location.add(red);

        purple.addActionListener(colourActionListener);
        orange.addActionListener(colourActionListener);
        red.addActionListener(colourActionListener);
        viewer.addActionListener(optionActionListener);
        main.addActionListener(optionActionListener);
        erase.addActionListener(optionActionListener);
        draw.addActionListener(optionActionListener);
        small.addActionListener(sizeActionListener);
        medium.addActionListener(sizeActionListener);
        large.addActionListener(sizeActionListener);
        veryLarge.addActionListener(sizeActionListener);

        parent.add(mainMenuBar);
    }

    // EFFECTS: action listener for enabling character location drawer
    // and to select the colour
    class CharacterLocationActionListener implements ActionListener {
        @Override
        public void actionPerformed( ActionEvent e) {
            String action = e.getActionCommand();
            switch (action) {
                case "Purple" :
                    System.out.println("purple");
                    break;
                case "Orange":
                    System.out.println("Orange");
                    break;
                case "Red":
                    System.out.println("Red");
                    break;
                default:
                    break;
            }
        }
    }

    // EFFECTS: action listener for the option menu
    // includes set to draw and erase
    class OptionActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String action = e.getActionCommand();
            switch (action) {
                case "Erase" :
                    parent.setDrawOrErase(true);
                    break;
                case "Draw" :
                    parent.setDrawOrErase(false);
                    break;
                case "Main":
                    client.setMainProgram(true);
                    draw.setMainProgram(true);
                    break;
                case "Viewer":
                    client.setMainProgram(false);
                    draw.setMainProgram(false);
                    break;
                default:
                    break;
            }
        }
    }

    // EFFECTS: action listener for setting the size of the drawing / erasing brush
    class SizeActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String action = e.getActionCommand();
            switch (action) {
                case "Small" :
                    parent.setSize(20 / DrawingProgram.SCALE);
                    break;
                case "Medium" :
                    parent.setSize(40 / DrawingProgram.SCALE);
                    break;
                case "Large" :
                    parent.setSize(140 / DrawingProgram.SCALE);
                    break;
                case "Very Large" :
                    parent.setSize(200 / DrawingProgram.SCALE);
                    break;
                default:
                    break;
            }
        }
    }
}

