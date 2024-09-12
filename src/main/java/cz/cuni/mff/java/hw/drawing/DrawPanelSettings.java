package cz.cuni.mff.java.hw.drawing;

import java.awt.*;

public class DrawPanelSettings {
    public Color backgroundColor;
    public Pen currentPen;

    public DrawPanelSettings() {
        backgroundColor = Color.WHITE;
    }

    public DrawPanelSettings(Color backgroundColor, Pen pen) {
        this.backgroundColor = backgroundColor;
        this.currentPen = pen;
    }
}
