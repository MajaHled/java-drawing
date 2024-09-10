package cz.cuni.mff.java.hw.drawing;

import java.awt.*;
import java.awt.event.MouseEvent;

public interface Pen {
    // This is gonna have responses to mouse listener events from draw panel
    void mouseDragged(MouseEvent e, Graphics2D g, PaintSettings s);
    void mouseReleased(MouseEvent e, Graphics2D g, PaintSettings s);

    void Reset();
}
