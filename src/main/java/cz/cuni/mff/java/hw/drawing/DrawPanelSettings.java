package cz.cuni.mff.java.hw.drawing;

import java.awt.*;

public class DrawPanelSettings {
    public Color mainColor, backgroundColor; //TODO do we need main color???
    public Pen currentPen; // TODO this may be null, what do?

    public DrawPanelSettings() {
        mainColor = Color.BLACK;
        backgroundColor = Color.WHITE;
    }

    public DrawPanelSettings(Color mainColor, Color backgroundColor, Pen pen) {
        this.mainColor = mainColor;
        this.backgroundColor = backgroundColor;
        this.currentPen = pen;
    }
}
