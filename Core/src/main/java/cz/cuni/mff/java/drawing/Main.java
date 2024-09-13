package cz.cuni.mff.java.drawing;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ServiceLoader;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::createAndShowGUI);
    }

    private static final File PLUGINS_DIR = new File("Plugins");

    private static final JFrame f = new JFrame("Untitled*");

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
    private static ArrayList<PenButton> penButtons = new ArrayList<>();
    private static final ArrayList<PenButton> permanentPenButtons = new ArrayList<>();
    private static final ArrayList<PenButton> shapePenButtons = new ArrayList<>();

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
        var editor = (JSpinner.DefaultEditor)strokeWidthSpinner.getEditor();
        editor.setPreferredSize(new Dimension(73, editor.getPreferredSize().height));
        editor.getTextField().setEditable(false);

        strokeWidthSpinner.addChangeListener(_ -> penSettings.strokeWidth = (int) strokeWidthSpinner.getValue());

        strokePanel.add(strokeWidthSpinner);
        leftMenu.add(strokePanel, gbc);
    }

    private static void displayButtons(JPanel buttonPanel, List<PenButton> buttons) {
        for(Component c : buttonPanel.getComponents()) {
            buttonPanel.remove(c);
        }

        // Set up button actions and add to tool group
        for (var b : buttons) {
            b.setPreferredSize(new Dimension(b.getIconSize()+10, b.getIconSize()+10));
            b.addActionListener (_ -> {
                panelSettings.currentPen.reset();
                panelSettings.currentPen = b.pen;
                b.pen.reset();
            });
            toolSelectionGroup.add(b);
            buttonPanel.add(b);
        }

        buttonPanel.revalidate();
        buttonPanel.repaint();
    }

    private static void setupPenButtonPanel(JPanel buttonPanel, List<PenButton> buttons, String borderMessage, GridBagConstraints gbc) {
        buttonPanel.setLayout(new GridLayout(Math.ceilDiv(buttons.size(),2), 2, 2, 2));
        buttonPanel.setBorder(BorderFactory.createTitledBorder(borderMessage));

        displayButtons(buttonPanel, buttons);

        leftMenu.add(buttonPanel, gbc);
    }

    private static void setupShapeButtons(GridBagConstraints gbc) {
        // Create shape buttons
        shapePenButtons.add(new PenButton(new RectanglePen(penSettings)));
        shapePenButtons.add(new PenButton(new CirclePen(penSettings)));
        shapePenButtons.add(new PenButton(new LinePen(penSettings)));

        setupPenButtonPanel(shapePanel, shapePenButtons, "Shape Selection", gbc);
    }

    private static void setupPenButtons(GridBagConstraints gbc) {
        // Create pen buttons
        permanentPenButtons.add(new PenButton(new TestPen(penSettings)));
        //permanentPenButtons.add(new PenButton(new RainbowPen(penSettings)));

        refreshPlugins();
        setupPenButtonPanel(penPanel, penButtons, "Pen Selection", gbc);
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
                f.setTitle("Untitled*");
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
                        JOptionPane.WARNING_MESSAGE);
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

    private static void refreshPlugins() {
        try {
            // Reset buttons to default state
            penButtons = new ArrayList<>(permanentPenButtons);

            // Get URLs of all files in PLUGINS_DIR
            ArrayList<URL> urls = new ArrayList<>();
            for (File file : Objects.requireNonNull(PLUGINS_DIR.listFiles())) {
                urls.add(file.toURI().toURL());
            }

            // Get a classloader for all the loaded URLs
            URL[] array = new URL[urls.size()];
            urls.toArray(array);
            URLClassLoader loader = new URLClassLoader(array);

            // Use the classloader in ServiceLoader to load all appropriate jars
            ServiceLoader<Pen> sl = ServiceLoader.load(Pen.class, loader);

            for (Pen plugin : sl) {
                plugin.setSettings(penSettings);
                penButtons.add(new PenButton(plugin));
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void  createAndShowGUI() {
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
            if (PLUGINS_DIR.exists() && PLUGINS_DIR.isDirectory()) {
                refreshPlugins();
                displayButtons(penPanel, penButtons);
            } else {
                //TODO deal
            }
        });
        gbc.gridy = 2;
        leftMenu.add(refreshButton, gbc);

        gbc.gridy = 3;
        setupShapeButtons(gbc);
        gbc.gridy = 4;
        setupPenButtons(gbc);

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
// add pen loading
// make example plugins
// Saving better and better size dialog
// fix layouts
// docs