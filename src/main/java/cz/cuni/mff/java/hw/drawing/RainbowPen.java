package cz.cuni.mff.java.hw.drawing;

import java.awt.*;
import java.awt.image.BufferedImage;

public class RainbowPen extends Pen {
    private int lastX = 0;
    private int lastY = 0;
    private float hue = 0;

    public RainbowPen(PenSettings settings) {
        super(settings);
    }

    @Override
    public void mousePressed(int x, int y, BufferedImage image) {
        Graphics2D g = image.createGraphics();
        settings.setupGraphics2D(g);

        g.setColor(Color.getHSBColor(hue, 1, 1));

        g.drawLine(x, y, x, y);

        lastX = x;
        lastY = y;
    }

    @Override
    public void mouseDragged(int x, int y, BufferedImage image) {
        Graphics2D g = image.createGraphics();
        settings.setupGraphics2D(g);

        float len = (float) Math.sqrt(Math.pow(lastX - x, 2) + Math.pow(lastX - x, 2));
        hue += len / 100;
        g.setColor(Color.getHSBColor(hue, 1, 1));

        g.drawLine(lastX, lastY, x, y);

        lastX = x;
        lastY = y;
    }

    @Override
    public void mouseReleased(int x, int y, BufferedImage image) {
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
