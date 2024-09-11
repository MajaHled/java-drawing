package cz.cuni.mff.java.hw.drawing;

import java.awt.*;

public class DrawPanelSettings {
    public Color mainColor, backgroundColor; //TODO do we need main color???
    public Pen currentPen;

    public DrawPanelSettings() {
        mainColor = Color.BLACK;
        backgroundColor = Color.WHITE;
        currentPen = new TestPen(); //TODO temp
    }

    public DrawPanelSettings(Color mainColor, Color backgroundColor, Pen pen) {
        this.mainColor = mainColor;
        this.backgroundColor = backgroundColor;
        this.currentPen = pen;
    }
}
