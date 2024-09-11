package cz.cuni.mff.java.hw.drawing;

import javax.sound.sampled.Line;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class RainbowPen extends Pen {
    private int lastX = 0;
    private int lastY = 0;
    private float hue = 0;

    public RainbowPen(PenSettings settings) {
        super(settings);
    }

    @Override
    public void mousePressed(MouseEvent e, BufferedImage image) {
        Graphics2D g = image.createGraphics();
        settings.setupGraphics2D(g);

        g.setColor(Color.getHSBColor(hue, 1, 1));

        g.drawLine(e.getX(), e.getY(), e.getX(), e.getY());

        lastX = e.getX();
        lastY = e.getY();
    }

    @Override
    public void mouseDragged(MouseEvent e, BufferedImage image) {
        Graphics2D g = image.createGraphics();
        settings.setupGraphics2D(g);

        float len = (float) Math.sqrt(Math.pow(lastX - e.getX(), 2) + Math.pow(lastX - e.getX(), 2));
        hue += len / 100;
        g.setColor(Color.getHSBColor(hue, 1, 1));

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
        hue = 0;
    }
}
