package cz.cuni.mff.java.hw.drawing;

import java.awt.*;
import java.awt.event.MouseEvent;

public interface Pen {
    // This is gonna have responses to mouse listener events from draw panel
    void mouseDragged(MouseEvent e);
    void mouseMoved(MouseEvent e);
    void mouseExited(MouseEvent e);
    void mouseEntered(MouseEvent e);
    void mousePressed(MouseEvent e);
    void mouseClicked(MouseEvent e);
    void mouseReleased(MouseEvent e);

    void reset();
    void setSettings(PenSettings settings);
    void setGraphics(Graphics2D graphics);
}
