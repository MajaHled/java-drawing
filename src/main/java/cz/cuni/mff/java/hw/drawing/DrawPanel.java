package cz.cuni.mff.java.hw.drawing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

public class DrawPanel extends JPanel {
    private BufferedImage img;
    private DrawPanelSettings settings;

    public DrawPanel(DrawPanelSettings settings, BufferedImage image) {
        this.settings = settings;
        this.img = image;

        addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                settings.currentPen.mouseDragged(e);
                repaint();
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                settings.currentPen.mouseMoved(e);
                repaint();
            }
        });

        addMouseListener(new MouseAdapter() {

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                settings.currentPen.mouseExited(e);
                repaint();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                settings.currentPen.mouseEntered(e);
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                settings.currentPen.mouseReleased(e);
                repaint();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                settings.currentPen.mousePressed(e);
                repaint();
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                settings.currentPen.mouseClicked(e);
                repaint();
            }
        });
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(img, 0, 0, null);
    }

    public void setImage(BufferedImage image) {
        this.img = image;
        if (settings.currentPen != null)
            settings.currentPen.setImage(img);
        repaint();
    }
    public BufferedImage getImage() {
        return img;
    }
}
