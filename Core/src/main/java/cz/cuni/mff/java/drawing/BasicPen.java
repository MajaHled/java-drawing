package cz.cuni.mff.java.drawing;

import java.awt.*;
import java.awt.image.BufferedImage;

public class BasicPen extends Pen {
    private int lastX = 0;
    private int lastY = 0;

    public BasicPen(PenSettings settings) {
        super(settings);
    }

    public BasicPen() {
        super();
    }

    @Override
    public void mousePressed(int x, int y, BufferedImage image) {
        Graphics2D g = image.createGraphics();
        settings.setupGraphics2D(g);

        g.drawLine(x, y, x, y);

        lastX = x;
        lastY = y;
    }

    @Override
    public void mouseDragged(int x, int y, BufferedImage image) {
        Graphics2D g = image.createGraphics();
        settings.setupGraphics2D(g);

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
    }
}
