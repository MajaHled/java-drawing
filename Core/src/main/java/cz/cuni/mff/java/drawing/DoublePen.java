package cz.cuni.mff.java.drawing;

import java.awt.*;
import java.awt.image.BufferedImage;

public class DoublePen extends Pen {
    private int lastX = 0;
    private int lastY = 0;

    public DoublePen(PenSettings settings) {
        this.settings = settings;
    }

    public DoublePen() { }

    @Override
    public void mousePressed(int x, int y, BufferedImage image) {
        Graphics2D g = image.createGraphics();
        settings.setupGraphics2D(g);

        g.drawLine(x, y - settings.strokeWidth, x, y - settings.strokeWidth);
        g.drawLine(x, y + settings.strokeWidth, x, y + settings.strokeWidth);

        lastX = x;
        lastY = y;
    }

    @Override
    public void mouseDragged(int x, int y, BufferedImage image) {
        Graphics2D g = image.createGraphics();
        settings.setupGraphics2D(g);

        g.drawLine(lastX, lastY - settings.strokeWidth, x, y - settings.strokeWidth);
        g.drawLine(lastX, lastY + settings.strokeWidth, x, y + settings.strokeWidth);

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
    }
}
