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
    private JScrollPane scrollPane;
    
    private int imgStartX = 0, imgStartY = 0;
    
    public DrawPanel(DrawPanelSettings settings, BufferedImage image) {
        this.s = settings;
        this.setImage(image);

        addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (s.currentPen != null)
                    s.currentPen.mouseDragged(transformX(e.getX()), transformY(e.getY()),  img);
                repaint();
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                if (s.currentPen != null)
                    s.currentPen.mouseMoved(transformX(e.getX()), transformY(e.getY()),  img);
                repaint();
            }
        });

        addMouseListener(new MouseAdapter() {

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                if (s.currentPen != null)
                    s.currentPen.mouseExited(transformX(e.getX()), transformY(e.getY()),  img);
                repaint();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                if (s.currentPen != null)
                    s.currentPen.mouseEntered(transformX(e.getX()), transformY(e.getY()),  img);
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                if (s.currentPen != null)
                    s.currentPen.mouseReleased(transformX(e.getX()), transformY(e.getY()),  img);
                repaint();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                if (s.currentPen != null)
                    s.currentPen.mousePressed(transformX(e.getX()), transformY(e.getY()),  img);
                repaint();
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (s.currentPen != null)
                    s.currentPen.mouseClicked(transformX(e.getX()), transformY(e.getY()),  img);
                repaint();
            }
        });
    }
    
    private int transformX(int x) {
        return x - imgStartX;
    }

    private int transformY(int y) {
        return y - imgStartY;
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // If the image is smaller than the panel area, center
        imgStartX = Math.max(0, (this.getWidth() - img.getWidth()) / 2);
        imgStartY = Math.max(0, (this.getHeight() - img.getHeight()) / 2);
        g.drawImage(img, imgStartX, imgStartY, null);
    }

    public void setScrollPane(JScrollPane scrollPane) {
        this.scrollPane = scrollPane;
        scrollPane.setPreferredSize(new Dimension(img.getWidth(), img.getHeight()));
    }

    public BufferedImage getImage() {
        return img;
    }

    public void setImage(BufferedImage image) {
        this.img = image;
        this.setPreferredSize(new Dimension(img.getWidth(), img.getHeight()));
        if (scrollPane != null)
            scrollPane.setPreferredSize(new Dimension(img.getWidth(), img.getHeight()));
        repaint();
    }

    public DrawPanelSettings getSettings() {
        return s;
    }
}
