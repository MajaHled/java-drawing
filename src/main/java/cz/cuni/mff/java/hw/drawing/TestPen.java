package cz.cuni.mff.java.hw.drawing;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class TestPen implements Pen {

    private boolean started = false;
    private int lastX = 0;
    private int lastY = 0;
    private PenSettings settings;
    private BufferedImage image;

    public TestPen(PenSettings settings, BufferedImage image) {
        this.settings = settings;
        this.image = image;
    }

    public TestPen() { }

    private void checkReady() {
        if (settings == null) {
            throw new IllegalStateException("Settings have not been given, must call SetSettings method before use.");
        }
        if (image == null) {
            throw new IllegalStateException("Image has not been given, must call SetImage method before use.");
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        checkReady();

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

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    } // TODO draw also here

    @Override
    public void mouseReleased(MouseEvent e) {
        started = false;
    }

    @Override
    public void reset() {
        started = false;
        lastX = 0;
        lastY = 0;
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
