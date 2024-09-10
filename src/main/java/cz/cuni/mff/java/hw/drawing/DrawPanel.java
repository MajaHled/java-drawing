package cz.cuni.mff.java.hw.drawing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

public class DrawPanel extends JPanel {
    private Pen CurrentPen;
    private BufferedImage img;
    private PaintSettings Settings;

    public DrawPanel(PaintSettings settings) {
        CurrentPen = new TestPen();
        img = new BufferedImage(250, 250, BufferedImage.TYPE_3BYTE_BGR);
        var g = img.createGraphics();
        Settings = settings;

        g.setColor(Color.white);
        g.fillRect(0, 0, img.getWidth(), img.getHeight());

        addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                CurrentPen.mouseDragged(e, img.createGraphics(), settings);
                repaint();
            }

            @Override
            public void mouseMoved(MouseEvent e) {

            }
        });

        addMouseListener(new MouseAdapter() {

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                CurrentPen.mouseReleased(e, img.createGraphics(), settings);
                repaint();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
            }
        });

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(img, 0, 0, null);
    }
}
