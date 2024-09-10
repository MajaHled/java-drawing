package cz.cuni.mff.java.hw.drawing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

public class DrawPanel extends JPanel {
    private BufferedImage img;
    private PaintSettings settings;

    public DrawPanel(PaintSettings settings) {
        img = new BufferedImage(250, 250, BufferedImage.TYPE_3BYTE_BGR);
        var g = img.createGraphics();
        this.settings = settings;
        // TODO temp
        settings.CurrentPen = new TestPen(settings, img.createGraphics());

        g.setColor(settings.backgroundColor);
        g.fillRect(0, 0, img.getWidth(), img.getHeight());

        addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                settings.CurrentPen.mouseDragged(e);
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
                settings.CurrentPen.mouseReleased(e);
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
