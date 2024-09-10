package cz.cuni.mff.java.hw.drawing;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::createAndShowGUI);
    }

    private static final PaintSettings settings = new PaintSettings();

    // Layout panels for settings
    private static final JPanel leftMenu = new JPanel();
    private static final JPanel colorPanel = new JPanel();
    private static final JPanel shapePanel = new JPanel();
    private static final JPanel penPanel = new JPanel();

    // Buttons and ButtonGroups
    private static final JButton selectMainColorButton = new JButton("Select main color...");
    private static final JButton selectBackgroundColorButton = new JButton("Select background color...");
    private static final ButtonGroup toolSelectionGroup = new ButtonGroup();
    // TODO: make button subclass for pen and shapes

    private static void  createAndShowGUI() {
        JFrame f = new JFrame("Paint");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLayout(new BorderLayout(1, 1));

        leftMenu.setLayout(new BoxLayout(leftMenu, BoxLayout.Y_AXIS));
        f.add(leftMenu, BorderLayout.WEST);

        // Drawing panel
        DrawPanel dp = new DrawPanel(settings);
        f.add(dp, BorderLayout.CENTER);

        // Color selection
        colorPanel.setLayout(new BoxLayout(colorPanel, BoxLayout.Y_AXIS));
        colorPanel.setBorder(BorderFactory.createTitledBorder("Color Selection"));

        selectMainColorButton.addActionListener(_ -> {
            var color = JColorChooser.showDialog(f, "Choose main color", settings.mainColor);
            if (color != null) {
                settings.mainColor = color;
                selectMainColorButton.setBackground(settings.mainColor);
            }
        });
        selectMainColorButton.setBackground(settings.mainColor);

        selectBackgroundColorButton.addActionListener(_ -> {
            var color = JColorChooser.showDialog(f, "Choose background color", settings.backgroundColor);
            if (color != null) {
                settings.backgroundColor = color;
                selectBackgroundColorButton.setBackground(settings.backgroundColor);
            }
        });
        selectBackgroundColorButton.setBackground(settings.backgroundColor);

        colorPanel.add(selectMainColorButton);
        colorPanel.add(selectBackgroundColorButton);
        leftMenu.add(colorPanel);

        // Shape selection
        shapePanel.setBorder(BorderFactory.createTitledBorder("Shape Selection"));
        // TODO
        leftMenu.add(shapePanel);

        // Pen selection
        penPanel.setBorder(BorderFactory.createTitledBorder("Pen Selection"));
        // TODO
        leftMenu.add(penPanel);

        f.pack();
        f.setSize(600, 300);
        f.setVisible(true);
    }
}

//Plan:
// add buttons and selectors
// add save, load and new
// add pen loading
// add preset pens and test extra pens
// add shape drawing
// docs