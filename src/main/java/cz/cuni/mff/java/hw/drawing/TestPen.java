package cz.cuni.mff.java.hw.drawing;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class TestPen extends Pen {
    private int lastX = 0;
    private int lastY = 0;

    public TestPen(PenSettings settings) {
        super(settings);
    }

    @Override
    public void mousePressed(MouseEvent e, BufferedImage image) {
        Graphics2D g = image.createGraphics();
        settings.setupGraphics2D(g);

        g.drawLine(e.getX(), e.getY(), e.getX(), e.getY());

        lastX = e.getX();
        lastY = e.getY();
    }

    @Override
    public void mouseDragged(MouseEvent e, BufferedImage image) {
        Graphics2D g = image.createGraphics();
        settings.setupGraphics2D(g);

        g.drawLine(lastX, lastY, e.getX(), e.getY());

        lastX = e.getX();
        lastY = e.getY();
    }

    @Override
    public void mouseReleased(MouseEvent e, BufferedImage image) {
        lastX = 0;
        lastY = 0;
    }

    @Override
    public void reset() {
        lastX = 0;
        lastY = 0;
    }
}
