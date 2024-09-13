package cz.cuni.mff.java.hw.drawing;

import javax.swing.*;
import java.awt.image.BufferedImage;

public abstract class Pen {
    protected final PenSettings settings;

    public Pen(PenSettings settings) {
        this.settings = settings;
    }

    public void mouseDragged(int x, int y, BufferedImage image) {}
    public void mouseMoved(int x, int y, BufferedImage image) {}
    public void mouseExited(int x, int y, BufferedImage image) {}
    public void mouseEntered(int x, int y, BufferedImage image) {}
    public void mousePressed(int x, int y, BufferedImage image) {}
    public void mouseClicked(int x, int y, BufferedImage image) {}
    public void mouseReleased(int x, int y, BufferedImage image) {}

    public void reset() {}

    public Icon getPreferredIcon() {
        return null;
    }
}
