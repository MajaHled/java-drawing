package cz.cuni.mff.java.hw.drawing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::createAndShowGUI);
    }

    private static PaintSettings settings = new PaintSettings();

    private static void  createAndShowGUI() {
        JFrame f = new JFrame("Paint");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container pane = f.getContentPane();
        pane.setLayout(new BorderLayout(1, 1));

        DrawPanel dp = new DrawPanel(settings);
        pane.add(dp, BorderLayout.CENTER);

        JButton selectMainColor = new JButton("Select main color...");
        JButton selectBackgroundColor = new JButton("Select background color...");
        selectMainColor.addActionListener(_ -> settings.mainColor = JColorChooser.showDialog(f, "Choose color", settings.mainColor));
        selectBackgroundColor.addActionListener(_ -> settings.backgroundColor = JColorChooser.showDialog(f, "Choose color", settings.mainColor));



        pane.add(selectMainColor, BorderLayout.WEST);
        pane.add(selectBackgroundColor, BorderLayout.WEST);

        f.pack();
        f.setSize(300, 300);
        f.setVisible(true);
    }
}

//TODO: have a settings object with stroke size, color etc, which may be ignored or used by pen and is set in the frame
//Plan:
// add buttons and selectors
// add save and load
// add pen loading
// add preset pens and test extra pens
// add shape drawing
// docs
// add undo???