package cz.cuni.mff.java.hw.drawing;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

public abstract class ShapePen extends Pen {
    private boolean started = false;
    private int startX = 0;
    private int startY = 0;
    private WritableRaster origRaster;

    public ShapePen(PenSettings settings) {
        super(settings);
    }

    protected abstract Shape getShape(int x1, int y1, int x2, int y2);

    @Override
    public final void mouseDragged(MouseEvent e, BufferedImage image) {
        if (started) {
            image.setData(origRaster);
            Graphics2D g = image.createGraphics();
            g.setColor(settings.mainColor);
            g.setStroke(new BasicStroke(settings.strokeWidth));

            g.draw(getShape(startX, startY, e.getX(), e.getY()));
        }
    }

    @Override
    public final void mousePressed(MouseEvent e, BufferedImage image) {
        started = true;
        startX = e.getX();
        startY = e.getY();
        origRaster = image.copyData(null);
    }

    @Override
    public final void mouseReleased(MouseEvent e, BufferedImage image) {
        started = false;
        startX = 0;
        startY = 0;
        origRaster = null;
    }

    @Override
    public final void reset() {
        started = false;
        startX = 0;
        startY = 0;
        origRaster = null;
    }
}
