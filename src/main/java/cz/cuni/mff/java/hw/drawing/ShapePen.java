package cz.cuni.mff.java.hw.drawing;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

public abstract class ShapePen implements Pen {
    private boolean started = false;
    private int startX = 0;
    private int startY = 0;
    private PenSettings settings;
    private BufferedImage image;
    private WritableRaster origRaster;
    //TODO deal with unset image and settings, maybe think about subclass instead of interface

    public ShapePen(PenSettings settings, BufferedImage image) {
        this.settings = settings;
        this.image = image;
    }

    public ShapePen() { }

    protected abstract Shape getShape(int x1, int y1, int x2, int y2);

    @Override
    public void mouseDragged(MouseEvent e) {
        if (started) {
            image.setData(origRaster);
            Graphics2D g = image.createGraphics();
            g.setColor(settings.mainColor);
            g.setStroke(new BasicStroke(settings.strokeWidth));

            g.draw(getShape(startX, startY, e.getX(), e.getY())); // TODO selection
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        started = true;
        startX = e.getX();
        startY = e.getY();
        origRaster = image.copyData(null);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        started = false;
        startX = 0;
        startY = 0;
        origRaster = null;
    }

    @Override
    public void reset() {
        started = false;
        startX = 0;
        startY = 0;
        origRaster = null;
    }

    @Override
    public void setSettings(PenSettings settings) {
        this.settings = settings;
    }

    @Override
    public void setImage(BufferedImage image) {
        this.image = image;
    }
}
