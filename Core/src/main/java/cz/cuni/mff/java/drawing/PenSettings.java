package cz.cuni.mff.java.drawing;

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

    public void setupGraphics2D(Graphics2D g) {
        g.setColor(mainColor);
        g.setStroke(new BasicStroke(strokeWidth, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER));
    }
}