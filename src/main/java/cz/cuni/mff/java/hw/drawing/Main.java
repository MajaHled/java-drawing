package cz.cuni.mff.java.hw.drawing;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::createAndShowGUI);
    }

    private static final PaintSettings settings = new PaintSettings();

    // Draw panel
    private static final DrawPanel dp = new DrawPanel(settings);

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

    // File open and save options
    private static final JFileChooser fileDialog = new JFileChooser();
    private static File saveFile;

    // Menu bar
    private static final JMenuBar menuBar = new JMenuBar();
    private static final JMenu fileMenu = new JMenu("File...");
    private static final JMenuItem newItem = new JMenuItem("New...");
    private static final JMenuItem openItem = new JMenuItem("Open...");
    private static final JMenuItem saveItem = new JMenuItem("Save...");
    private static final JMenuItem saveAsItem = new JMenuItem("Save as...");

    private static void  createAndShowGUI() {
        JFrame f = new JFrame("Paint");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLayout(new BorderLayout(1, 1));

        leftMenu.setLayout(new BoxLayout(leftMenu, BoxLayout.Y_AXIS));
        f.add(leftMenu, BorderLayout.WEST);

        // Drawing panel
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

        // Saving menu
        menuBar.add(fileMenu);
        fileMenu.add(newItem);
        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.add(saveAsItem);
        f.setJMenuBar(menuBar);

        newItem.addActionListener(_ -> {
            dp.NewImage(250, 250); // TODO dialog to choose
            saveFile = null;
        });

        openItem.addActionListener(_ -> {
            if (fileDialog.showOpenDialog(f) == JFileChooser.APPROVE_OPTION) {
                File chosenFile = fileDialog.getSelectedFile();
                if (chosenFile != null) {
                    if (dp.LoadImage(chosenFile) == 0)
                        saveFile = chosenFile;
                }
            }
        });

        saveItem.addActionListener(_ -> {
            if (saveFile != null) {
                dp.SaveImage(saveFile);
            } else {
                if (fileDialog.showOpenDialog(f) == JFileChooser.APPROVE_OPTION) {
                    File chosenFile = fileDialog.getSelectedFile();
                    if (chosenFile != null) {
                        if (dp.SaveImage(chosenFile) == 0)
                            saveFile = chosenFile;
                    }
                }
            }
        });

        saveAsItem.addActionListener(_ -> {
            if (fileDialog.showOpenDialog(f) == JFileChooser.APPROVE_OPTION) {
                File chosenFile = fileDialog.getSelectedFile();
                if (chosenFile != null) {
                    if (dp.SaveImage(chosenFile) == 0)
                        saveFile = chosenFile;
                }
            }
        });

        f.pack();
        f.setSize(600, 300);
        f.setVisible(true);
    }
}

//Plan:
// add buttons and selectors (with stroke width too)
// add save, load, new -> add save state to title and ask to override
// add pen loading
// add preset pens and test extra pens
// the pens should optionally have the ability to return own icon, but may also be generated by dragging
// add shape drawing
// docs