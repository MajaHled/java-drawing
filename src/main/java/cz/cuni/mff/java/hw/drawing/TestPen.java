package cz.cuni.mff.java.hw.drawing;

import java.awt.*;
import java.awt.event.MouseEvent;

public class TestPen implements Pen {

    private boolean started = false;
    private int lastX = 0;
    private int lastY = 0;
    private PenSettings settings;
    private Graphics2D g;

    public TestPen(PenSettings settings, Graphics2D graphics) {
        this.settings = settings;
        g = graphics;
    }

    public TestPen() { }

    private void checkReady() {
        if (settings == null) {
            throw new IllegalStateException("Settings have not been given, must call SetSettings method before use.");
        }
        if (g == null) {
            throw new IllegalStateException("Graphics object has not been given, must call SetGraphics method before use.");
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        checkReady();

        g.setColor(settings.mainColor);

        if (!started) {
            g.drawRect(e.getX(), e.getY(), 1, 1);
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

    }

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
    public void setGraphics(Graphics2D graphics) {
        g = graphics;
    }
}
