package cz.cuni.mff.java.hw.drawing;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::createAndShowGUI);
    }

    private static final JFrame f = new JFrame("Paint");

    // Settings classes
    private static final DrawPanelSettings panelSettings = new DrawPanelSettings();
    private static final PenSettings penSettings = new PenSettings();

    // Draw panel
    private static final DrawPanel dp = new DrawPanel(panelSettings,
            PictureLoader.NewImage(250, 250, panelSettings.backgroundColor));

    // Layout panels for settings
    private static final JPanel leftMenu = new JPanel();
    private static final JPanel colorPanel = new JPanel();
    private static final JPanel strokePanel = new JPanel();
    private static final JPanel shapePanel = new JPanel();
    private static final JPanel penPanel = new JPanel();

    // Buttons and ButtonGroups
    private static final JButton selectMainColorButton = new JButton("Select main color...");
    private static final JButton selectBackgroundColorButton = new JButton("Select background color...");
    private static final JSpinner strokeWidthSpinner = new JSpinner(new SpinnerNumberModel(1, 1, null, 1));

    private static final ButtonGroup toolSelectionGroup = new ButtonGroup();
    private static final ArrayList<PenButton> penButtons = new ArrayList<>();
    private static final ArrayList<PenButton> shapePenButtons = new ArrayList<>();

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
    // TODO add keyboard shortcuts

    private static void setupColorSelect() {
        colorPanel.setLayout(new BoxLayout(colorPanel, BoxLayout.Y_AXIS));
        colorPanel.setBorder(BorderFactory.createTitledBorder("Color Selection"));

        selectMainColorButton.addActionListener(_ -> {
            var color = JColorChooser.showDialog(f, "Choose main color", panelSettings.mainColor);
            if (color != null) {
                panelSettings.mainColor = color;
                penSettings.mainColor = color;
                selectMainColorButton.setBackground(panelSettings.mainColor);
            }
        });
        selectMainColorButton.setBackground(panelSettings.mainColor);

        selectBackgroundColorButton.addActionListener(_ -> {
            var color = JColorChooser.showDialog(f, "Choose background color", panelSettings.backgroundColor);
            if (color != null) {
                panelSettings.backgroundColor = color;
                penSettings.backgroundColor = color;
                selectBackgroundColorButton.setBackground(panelSettings.backgroundColor);
            }
        });
        selectBackgroundColorButton.setBackground(panelSettings.backgroundColor);

        colorPanel.add(selectMainColorButton);
        colorPanel.add(selectBackgroundColorButton);
        leftMenu.add(colorPanel);
    }

    private static void setupStrokeSelect() {
        strokePanel.setBorder(BorderFactory.createTitledBorder("Width Selection"));

        // Setting spinner size and edit settings
        var editor = (JSpinner.DefaultEditor)strokeWidthSpinner.getEditor();
        editor.setPreferredSize(new Dimension(30, editor.getPreferredSize().height));
        editor.getTextField().setEditable(false); //TODO possibly just do input validation

        strokeWidthSpinner.addChangeListener(_ -> penSettings.strokeWidth = (int) strokeWidthSpinner.getValue());

        strokePanel.add(strokeWidthSpinner);
        leftMenu.add(strokePanel);
    }

    private static void setupShapeButtons() {
        // Create shape buttons
        shapePenButtons.add(new PenButton(new RectanglePen(penSettings)));
        shapePenButtons.add(new PenButton(new CirclePen(penSettings)));
        shapePenButtons.add(new PenButton(new LinePen(penSettings)));

        shapePanel.setBorder(BorderFactory.createTitledBorder("Shape Selection"));

        // Set up button actions and add to tool group
        for (var b : shapePenButtons) {
            b.addActionListener (_ -> {
                panelSettings.currentPen.reset();
                panelSettings.currentPen = b.pen;
                b.pen.reset();
            });
            toolSelectionGroup.add(b);
            shapePanel.add(b);
        }

        leftMenu.add(shapePanel);
    }

    private static void setupPenButtons() {
        // Create pen buttons
        penButtons.add(new PenButton(new TestPen(penSettings)));
        penButtons.add(new PenButton(new RainbowPen(penSettings)));
        // TODO: layout max two buttons in a row

        penPanel.setBorder(BorderFactory.createTitledBorder("Pen Selection"));

        // Set up button actions and add to tool group
        for (var b : penButtons) {
            b.addActionListener (_ -> {
                panelSettings.currentPen.reset();
                panelSettings.currentPen = b.pen;
                b.pen.reset();
            });
            toolSelectionGroup.add(b);
            penPanel.add(b);
        }

        leftMenu.add(penPanel);
    }

    private static void setupFileMenu() {
        menuBar.add(fileMenu);
        fileMenu.add(newItem);
        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.add(saveAsItem);
        f.setJMenuBar(menuBar);

        newItem.addActionListener(_ -> {
            dp.setImage(PictureLoader.NewImage(250, 250, panelSettings.backgroundColor)); // TODO dialog to choose
            saveFile = null;
        });

        openItem.addActionListener(_ -> {
            if (fileDialog.showOpenDialog(f) == JFileChooser.APPROVE_OPTION) {
                File chosenFile = fileDialog.getSelectedFile();
                if (chosenFile != null) {
                    var img = PictureLoader.LoadImage(chosenFile);
                    if (img != null) {
                        dp.setImage(img);
                        saveFile = chosenFile;
                    }
                }
            }
        });

        saveItem.addActionListener(_ -> {
            if (saveFile != null) {
                PictureLoader.SaveImage(dp.getImage(), saveFile); // TODO could be error here
            } else {
                if (fileDialog.showOpenDialog(f) == JFileChooser.APPROVE_OPTION) {
                    File chosenFile = fileDialog.getSelectedFile();
                    if (chosenFile != null) {
                        if (PictureLoader.SaveImage(dp.getImage(), chosenFile) == 0)
                            saveFile = chosenFile;
                    }
                }
            }
        });

        saveAsItem.addActionListener(_ -> {
            if (fileDialog.showOpenDialog(f) == JFileChooser.APPROVE_OPTION) {
                File chosenFile = fileDialog.getSelectedFile();
                if (chosenFile != null) {
                    if (PictureLoader.SaveImage(dp.getImage(), chosenFile) == 0)
                        saveFile = chosenFile;
                }
            }
        });
    }

    private static void  createAndShowGUI() {
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Prepare layouts
        f.setLayout(new BorderLayout(1, 1));
        leftMenu.setLayout(new BoxLayout(leftMenu, BoxLayout.Y_AXIS));
        f.add(leftMenu, BorderLayout.WEST);
        f.add(dp, BorderLayout.CENTER);

        // Setup of controls
        setupColorSelect();
        setupStrokeSelect();
        setupShapeButtons();
        setupPenButtons();

        // Set starting pen
        penButtons.getFirst().setSelected(true);
        panelSettings.currentPen = penButtons.getFirst().pen;

        setupFileMenu();

        f.pack();
        f.setSize(600, 300);
        f.setVisible(true);
    }
}
// after break: add icons to pens

//Plan:
// add save, load, new -> add save state to title and ask to override
// deal with file extensions
// add pen loading
// make example plugins
// deal with resize better
// docs
// crlf