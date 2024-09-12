package cz.cuni.mff.java.hw.drawing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

public class DrawPanel extends JPanel {
    private BufferedImage img;
    private final DrawPanelSettings s;

    public DrawPanel(DrawPanelSettings settings, BufferedImage image) {
        this.s = settings;
        this.img = image;

        addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (s.currentPen != null)
                    s.currentPen.mouseDragged(e, img);
                repaint();
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                if (s.currentPen != null)
                    s.currentPen.mouseMoved(e, img);
                repaint();
            }
        });

        addMouseListener(new MouseAdapter() {

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                if (s.currentPen != null)
                    s.currentPen.mouseExited(e, img);
                repaint();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                if (s.currentPen != null)
                    s.currentPen.mouseEntered(e, img);
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                if (s.currentPen != null)
                    s.currentPen.mouseReleased(e, img);
                repaint();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                if (s.currentPen != null)
                    s.currentPen.mousePressed(e, img);
                repaint();
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (s.currentPen != null)
                    s.currentPen.mouseClicked(e, img);
                repaint();
            }
        });
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(img, 0, 0, null);
    }

    public BufferedImage getImage() {
        return img;
    }

    public void setImage(BufferedImage image) {
        this.img = image;
        repaint();
    }

    public DrawPanelSettings getSettings() {
        return s;
    }
}
