package cz.cuni.mff.java.hw.drawing;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class DrawPanel extends JPanel {
    private BufferedImage img;
    private PaintSettings settings;

    public DrawPanel(PaintSettings settings) {
        this.settings = settings;

        NewImage(250, 250);
        // TODO temp
        settings.CurrentPen = new TestPen(settings, img.createGraphics());

        // TODO add links of events to pen
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

    public int LoadImage(File imgFile) {
        if (imgFile.exists()) {
            try {
                img = ImageIO.read(imgFile); // TODO: other formats
                settings.CurrentPen.SetGraphics(img.createGraphics());
                this.repaint();
                return 0;
            } catch (IOException e) {
                return 1;
            }
        }
        return 1;
    }
    public int SaveImage(File imgFile) {
        try {
            ImageIO.write(img, "png", imgFile); // TODO: other formats
            return 0;
        } catch (IOException e) {
            return 1;
        }
    }
    public void NewImage(int width, int height) {
        img = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        var g = img.createGraphics();
        g.setColor(settings.backgroundColor);
        g.fillRect(0, 0, img.getWidth(), img.getHeight());

        if (settings.CurrentPen != null) {
            settings.CurrentPen.SetGraphics(g);
        }

        this.repaint();
    }
}
