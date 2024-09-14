package cz.cuni.mff.java.drawing;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

public abstract class ShapePen extends Pen {
    private int startX = 0;
    private int startY = 0;
    private WritableRaster origRaster;

    protected boolean fill = false;

    public ShapePen(PenSettings settings) {
        super(settings);
    }
    public ShapePen() {
        super();
    }

    protected abstract Shape getShape(int x1, int y1, int x2, int y2);

    @Override
    public final void mouseDragged(int x, int y, BufferedImage image) {
        if (origRaster != null)
            image.setData(origRaster);

        Graphics2D g = image.createGraphics();
        settings.setupGraphics2D(g);

        if (fill) {
            g.setColor(settings.backgroundColor);
            g.fill(getShape(startX, startY, x, y));
            g.setColor(settings.mainColor);
        }

        g.draw(getShape(startX, startY, x, y));
    }

    @Override
    public final void mouseMoved(int x, int y, BufferedImage image) {}

    @Override
    public final void mouseExited(int x, int y, BufferedImage image) {}

    @Override
    public final void mouseEntered(int x, int y, BufferedImage image) {}

    @Override
    public final void mousePressed(int x, int y, BufferedImage image) {
        startX = x;
        startY = y;
        origRaster = image.copyData(null);
    }

    @Override
    public final void mouseClicked(int x, int y, BufferedImage image) {}

    @Override
    public final void mouseReleased(int x, int y, BufferedImage image) {
        startX = 0;
        startY = 0;
        origRaster = null;
    }

    @Override
    public final void reset() {
        startX = 0;
        startY = 0;
        origRaster = null;
    }
}
