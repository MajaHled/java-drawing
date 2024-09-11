package cz.cuni.mff.java.hw.drawing;

import java.awt.*;

public class DrawPanelSettings {
    public Color mainColor, backgroundColor; //TODO do we need main color???
    public DrawShape shape;
    public Pen currentPen;

    public enum DrawShape { RECTANGLE, OVAL, TRIANGLE, STAR }; //TODO custom

    public DrawPanelSettings() {
        mainColor = Color.BLACK;
        backgroundColor = Color.WHITE;
        shape = DrawShape.RECTANGLE;
        currentPen = new TestPen(); //TODO temp
    }

    public DrawPanelSettings(Color mainColor, Color backgroundColor, DrawShape shape, Pen pen) {
        this.mainColor = mainColor;
        this.backgroundColor = backgroundColor;
        this.shape = shape;
        this.currentPen = pen;
    }
}
