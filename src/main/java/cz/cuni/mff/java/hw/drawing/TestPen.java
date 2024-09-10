package cz.cuni.mff.java.hw.drawing;

import java.awt.*;
import java.awt.event.MouseEvent;

public class TestPen implements Pen {

    private boolean started = false;
    private int lastX = 0;
    private int lastY = 0;

    @Override
    public void mouseDragged(MouseEvent e, Graphics2D g, PaintSettings s) {
        g.setColor(s.mainColor);

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
    public void mouseReleased(MouseEvent e, Graphics2D g, PaintSettings s) {
        started = false;
    }

    @Override
    public void Reset() {
        started = false;
        lastX = 0;
        lastY = 0;
    }
}
