package cz.cuni.mff.java.hw.drawing;

import java.awt.*;

public class PenSettings {
    public Color mainColor, backgroundColor;
    public int strokeWidth;

    public PenSettings() {
        mainColor = Color.BLACK;
        backgroundColor = Color.WHITE;
        strokeWidth = 1;
    }

    public PenSettings(Color mainColor, Color backgroundColor, int strokeWidth) {
        this.mainColor = mainColor;
        this.backgroundColor = backgroundColor;
        this.strokeWidth = strokeWidth;
    }
}
