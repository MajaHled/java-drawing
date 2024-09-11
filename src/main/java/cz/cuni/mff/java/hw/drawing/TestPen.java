package cz.cuni.mff.java.hw.drawing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class TestPen extends Pen {

    private boolean started = false;
    private int lastX = 0;
    private int lastY = 0;

    public TestPen(PenSettings settings) {
        super(settings);
    }

    @Override
    public void mousePressed(MouseEvent e, BufferedImage image) {
        Graphics2D g = image.createGraphics();
        g.setColor(settings.mainColor);
        g.setStroke(new BasicStroke(settings.strokeWidth));

        if (!started) {
            g.drawLine(e.getX(), e.getY(), e.getX(), e.getY());
            started = true;
        } else {
            g.drawLine(lastX, lastY, e.getX(), e.getY());
        }
        lastX = e.getX();
        lastY = e.getY();
    }

    @Override
    public void mouseDragged(MouseEvent e, BufferedImage image) {
        Graphics2D g = image.createGraphics();
        g.setColor(settings.mainColor);
        g.setStroke(new BasicStroke(settings.strokeWidth));

        if (!started) {
            g.drawLine(e.getX(), e.getY(), e.getX(), e.getY());
            started = true;
        } else {
            g.drawLine(lastX, lastY, e.getX(), e.getY());
        }
        lastX = e.getX();
        lastY = e.getY();
    }

    @Override
    public void mouseReleased(MouseEvent e, BufferedImage image) {
        started = false;
    }

    @Override
    public void reset() {
        started = false;
        lastX = 0;
        lastY = 0;
    }
}
