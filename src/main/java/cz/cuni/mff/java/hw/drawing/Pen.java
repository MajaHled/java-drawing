package cz.cuni.mff.java.hw.drawing;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public abstract class Pen {
    protected final PenSettings settings;

    public Pen(PenSettings settings) {
        this.settings = settings;
    }

    public void mouseDragged(MouseEvent e, BufferedImage image) {}
    public void mouseMoved(MouseEvent e, BufferedImage image) {}
    public void mouseExited(MouseEvent e, BufferedImage image) {}
    public void mouseEntered(MouseEvent e, BufferedImage image) {}
    public void mousePressed(MouseEvent e, BufferedImage image) {}
    public void mouseClicked(MouseEvent e, BufferedImage image) {}
    public void mouseReleased(MouseEvent e, BufferedImage image) {}

    public void reset() {}

    public Icon getPreferredIcon() {
        return null;
    }
}
