package cz.cuni.mff.java.drawing;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::createAndShowGUI);
    }

    private static final JFrame f = new JFrame("Untitled");

    // Settings classes
    private static final DrawPanelSettings panelSettings = new DrawPanelSettings();
    private static final PenSettings penSettings = new PenSettings();

    // Draw panel
    private static final DrawPanel dp = new DrawPanel(panelSettings,
            PictureLoader.NewImage(250, 250, panelSettings.backgroundColor));
    private static final JScrollPane scrollPaneDP = new JScrollPane(dp);

    // Layout panels for settings
    private static final JPanel leftMenu = new JPanel();
    private static final JPanel colorPanel = new JPanel();
    private static final JPanel strokePanel = new JPanel();
    private static final JPanel shapePanel = new JPanel();
    private static final JPanel penPanel = new JPanel();

    // Buttons and ButtonGroups
    private static final JButton selectMainColorButton = new JButton("Main color..");
    private static final JButton selectBackgroundColorButton = new JButton("Back color..");
    private static final JSpinner strokeWidthSpinner = new JSpinner(new SpinnerNumberModel(1, 1, null, 1));

    private static final ButtonGroup toolSelectionGroup = new ButtonGroup();
    private static final ArrayList<PenButton> penButtons = new ArrayList<>();
    private static final ArrayList<PenButton> permanentPenButtons = new ArrayList<>();
    private static final ArrayList<PenButton> shapePenButtons = new ArrayList<>();
    private static final ArrayList<PenButton> permanentShapePenButtons = new ArrayList<>();

    private static final JButton refreshButton = new JButton("Refresh");

    // File open and save options
    private static final JFileChooser fileDialog = new JFileChooser();
    private static File saveFile;

    // Canvas size options window
    private static final JPanel canvasSizeOptions = new JPanel();
    private static JFormattedTextField xField;
    private static JFormattedTextField yField;

    // Menu bar
    private static final JMenuBar menuBar = new JMenuBar();
    private static final JMenu fileMenu = new JMenu("File...");
    private static final JMenuItem newItem = new JMenuItem("New...");
    private static final JMenuItem openItem = new JMenuItem("Open...");
    private static final JMenuItem saveItem = new JMenuItem("Save...");
    private static final JMenuItem saveAsItem = new JMenuItem("Save as...");

    // Plugin setup
    private static final File PLUGINS_DIR = new File("Plugins");
    private static final PluginLoader shapeLoader = new PluginLoader(ShapePen.class, shapePenButtons, permanentShapePenButtons, PLUGINS_DIR, penSettings, panelSettings);
    private static final PluginLoader penLoader = new PluginLoader(Pen.class, penButtons, permanentPenButtons, PLUGINS_DIR, penSettings, panelSettings);

    private static void setupColorSelect(GridBagConstraints gbc) {
        colorPanel.setLayout(new BoxLayout(colorPanel, BoxLayout.Y_AXIS));
        colorPanel.setBorder(BorderFactory.createTitledBorder("Color Selection"));

        selectMainColorButton.addActionListener(_ -> {
            var color = JColorChooser.showDialog(f, "Choose main color", penSettings.mainColor);
            if (color != null) {
                penSettings.mainColor = color;
                selectMainColorButton.setBackground(color);
            }
        });
        selectMainColorButton.setBackground(penSettings.mainColor);

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
        leftMenu.add(colorPanel, gbc);
    }

    private static void setupStrokeSelect(GridBagConstraints gbc) {
        strokePanel.setBorder(BorderFactory.createTitledBorder("Width Selection"));

        // Setting spinner size and edit settings
        var editor = (JSpinner.DefaultEditor) strokeWidthSpinner.getEditor();
        editor.setPreferredSize(new Dimension(73, editor.getPreferredSize().height));
        editor.getTextField().setEditable(false);

        strokeWidthSpinner.addChangeListener(_ -> penSettings.strokeWidth = (int) strokeWidthSpinner.getValue());

        strokePanel.add(strokeWidthSpinner);
        leftMenu.add(strokePanel, gbc);
    }

    private static void setupFileMenu() {
        menuBar.add(fileMenu);
        fileMenu.add(newItem);
        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.add(saveAsItem);
        f.setJMenuBar(menuBar);

        fileDialog.setFileFilter(PictureLoader.GetFileExtensionFilter());

        // Size picker for New... menu
        // Setup format of text fields
        NumberFormatter formatter = new NumberFormatter(NumberFormat.getInstance());
        formatter.setValueClass(Integer.class);
        formatter.setMinimum(0);
        formatter.setMaximum(Integer.MAX_VALUE);
        formatter.setAllowsInvalid(false);

        xField = new JFormattedTextField(formatter);
        xField.setColumns(5);
        xField.setText("250");
        yField = new JFormattedTextField(formatter);
        yField.setColumns(5);
        yField.setText("250");

        // Add textboxes
        canvasSizeOptions.add(new JLabel("Width:"));
        canvasSizeOptions.add(xField);
        canvasSizeOptions.add(Box.createHorizontalStrut(15)); // a spacer
        canvasSizeOptions.add(new JLabel("Height:"));
        canvasSizeOptions.add(yField);

        newItem.addActionListener(_ -> {
            int result = JOptionPane.showConfirmDialog(null, canvasSizeOptions,
                    "Please enter desired width and height", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                int x = 250, y = 250;
                if (!xField.getText().isEmpty())
                    x = Integer.parseInt(xField.getText());
                if (!yField.getText().isEmpty())
                    y = Integer.parseInt(yField.getText());

                dp.setImage(PictureLoader.NewImage(x, y, panelSettings.backgroundColor));
                saveFile = null;
                f.setTitle("Untitled");
                panelSettings.currentPen.reset();
            }
        });

        openItem.addActionListener(_ -> {
            if (fileDialog.showOpenDialog(f) == JFileChooser.APPROVE_OPTION) {
                File chosenFile = fileDialog.getSelectedFile();
                if (chosenFile != null) {
                    var img = PictureLoader.LoadImage(chosenFile);
                    if (img != null) {
                        dp.setImage(img);
                        saveFile = chosenFile;
                        f.setTitle(saveFile.getName());
                        panelSettings.currentPen.reset();
                    }
                }
            }
        });

        saveItem.addActionListener(_ -> {
            boolean saved = false;
            if (saveFile != null) {
                int ret_code = PictureLoader.SaveImage(dp.getImage(), saveFile);
                if (ret_code == 0) {
                    saved = true;
                    f.setTitle(saveFile.getName());
                }
            }
            if (!saved) {
                if (fileDialog.showOpenDialog(f) == JFileChooser.APPROVE_OPTION) {
                    File chosenFile = fileDialog.getSelectedFile();
                    if (chosenFile != null) {
                        if (PictureLoader.SaveImage(dp.getImage(), chosenFile) == 0) {
                            saveFile = chosenFile;
                            f.setTitle(saveFile.getName());
                            saved = true;
                        }
                    }
                }
            }

            if (!saved)
                JOptionPane.showMessageDialog(f,
                        "Could not save to file.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
        });

        saveAsItem.addActionListener(_ -> {
            boolean saved = false;
            if (fileDialog.showSaveDialog(f) == JFileChooser.APPROVE_OPTION) {
                File chosenFile = fileDialog.getSelectedFile();
                if (chosenFile != null) {
                    if (PictureLoader.SaveImage(dp.getImage(), chosenFile) == 0) {
                        saveFile = chosenFile;
                        f.setTitle(saveFile.getName());
                        saved = true;
                    }
                }
            }
            if (!saved)
                JOptionPane.showMessageDialog(f,
                        "Could not save to file.",
                        "Error",
                        JOptionPane.WARNING_MESSAGE);
        });
    }

    private static class ToolButtonSetup {
        private static void redisplayButtons(JPanel buttonPanel, List<PenButton> buttons) {
            // Empty panel and remove from tool group
            for (Component c : buttonPanel.getComponents()) {
                buttonPanel.remove(c);
                toolSelectionGroup.remove((PenButton) c);
            }

            // Adjust panel layout
            buttonPanel.setLayout(new GridLayout(Math.ceilDiv(buttons.size(), 2), 2, 2, 2));

            for (var b : buttons) {
                b.setPreferredSize(new Dimension(b.getIconSize() + 10, b.getIconSize() + 10));

                // Add to tool group and panel
                toolSelectionGroup.add(b);
                buttonPanel.add(b);
            }

            buttonPanel.revalidate();
            buttonPanel.repaint();
        }

        private static void setupToolButtonPanel(JPanel buttonPanel, List<PenButton> buttons, String borderMessage, GridBagConstraints gbc) {
            buttonPanel.setBorder(BorderFactory.createTitledBorder(borderMessage));

            redisplayButtons(buttonPanel, buttons);

            leftMenu.add(buttonPanel, gbc);
        }

        public static void setupShapeButtons(GridBagConstraints gbc) {
            // Create permanent (non-plugin) shape buttons
            permanentShapePenButtons.add(new PenButton(new RectanglePen(penSettings)));
            permanentShapePenButtons.add(new PenButton(new CirclePen(penSettings)));
            permanentShapePenButtons.add(new PenButton(new LinePen(penSettings)));

            // Setup button action
            for (var b : permanentShapePenButtons) {
                b.addActionListener(_ -> {
                    panelSettings.currentPen.reset();
                    panelSettings.currentPen = b.pen;
                    b.pen.reset();
                });
            }

            // Put on panel
            shapePenButtons.clear();
            shapePenButtons.addAll(permanentShapePenButtons);
            setupToolButtonPanel(shapePanel, shapePenButtons, "Shape Selection", gbc);
        }

        public static void setupPenButtons(GridBagConstraints gbc) {
            // Create permanent (non-plugin) pen buttons
            permanentPenButtons.add(new PenButton(new BasicPen(penSettings)));
            permanentPenButtons.add(new PenButton(new DoublePen(penSettings)));

            // Setup button action
            for (var b : permanentPenButtons) {
                b.addActionListener(_ -> {
                    panelSettings.currentPen.reset();
                    panelSettings.currentPen = b.pen;
                    b.pen.reset();
                });
            }

            // Put on panel
            penButtons.clear();
            penButtons.addAll(permanentPenButtons);
            setupToolButtonPanel(penPanel, penButtons, "Pen Selection", gbc);
        }

        public static void redisplayAllButtons() {
            redisplayButtons(penPanel, penButtons);
            redisplayButtons(shapePanel, shapePenButtons);
        }

        public static void refreshAllPlugins(boolean alerts) {
            if (PLUGINS_DIR.exists()) {
                try {
                    // Reload all plugins in the plugins directory
                    penLoader.refreshPlugins();
                    shapeLoader.refreshPlugins();
                    redisplayAllButtons();
                } catch (FileNotFoundException e) {
                    if (alerts)
                        JOptionPane.showMessageDialog(f,
                                e.getMessage(),
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                }
            } else {
                if (alerts)
                    JOptionPane.showMessageDialog(f,
                            "Plugins directory not found.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private static void createAndShowGUI() {
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Prepare layouts
        f.setLayout(new BorderLayout(1, 1));
        dp.setScrollPane(scrollPaneDP);
        f.add(scrollPaneDP, BorderLayout.CENTER);

        leftMenu.setLayout(new GridBagLayout());

        // Setup of controls
        var gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        setupColorSelect(gbc);
        gbc.gridy = 1;
        setupStrokeSelect(gbc);

        refreshButton.addActionListener(_ -> {
            ToolButtonSetup.refreshAllPlugins(true);
            penButtons.getFirst().doClick();
        });
        gbc.gridy = 2;
        leftMenu.add(refreshButton, gbc);

        gbc.gridy = 3;
        ToolButtonSetup.setupShapeButtons(gbc);
        gbc.gridy = 4;
        ToolButtonSetup.setupPenButtons(gbc);
        ToolButtonSetup.refreshAllPlugins(false);

        // Set starting pen
        penButtons.getFirst().setSelected(true);
        panelSettings.currentPen = penButtons.getFirst().pen;

        setupFileMenu();

        f.add(new JScrollPane(leftMenu), BorderLayout.WEST);

        f.pack();
        f.setSize(600, 500);
        f.setVisible(true);
    }
}

//Plan:
// deal with icon resizes
// retest
// docs